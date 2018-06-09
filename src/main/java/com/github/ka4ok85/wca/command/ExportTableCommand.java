package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

/**
 * <strong>Class for interacting with WCA ExportTable API.</strong> It builds XML
 * request for ExportTable API using
 * {@link com.github.ka4ok85.wca.options.ExportTableOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.ExportTableResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * It relies on parent {@link com.github.ka4ok85.wca.command.AbstractJobCommand}
 * for polling mechanism.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class ExportTableCommand extends AbstractJobCommand<ExportTableResponse, ExportTableOptions> {

	private static final String apiMethodName = "ExportTable";
	private static final Logger log = LoggerFactory.getLogger(ExportTableCommand.class);

	@Autowired
	private ExportTableResponse exportTableResponse;

	/**
	 * Builds XML request for ExportTable API using
	 * {@link com.github.ka4ok85.wca.options.ExportTableOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(ExportTableOptions options) {
		Objects.requireNonNull(options, "ExportTableOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element tableID = doc.createElement("TABLE_ID");
		tableID.setTextContent(options.getTableId().toString());
		addChildNode(tableID, currentNode);

		Element exportFormat = doc.createElement("EXPORT_FORMAT");
		exportFormat.setTextContent(options.getExportFormat().value());
		addChildNode(exportFormat, currentNode);

		Element fileEncoding = doc.createElement("FILE_ENCODING");
		fileEncoding.setTextContent(options.getFileEncoding().value());
		addChildNode(fileEncoding, currentNode);

		DateTimeRange lastModifiedRange = options.getLastModifiedRange();
		if (lastModifiedRange != null) {
			addParameter(currentNode, "DATE_START", lastModifiedRange.getFormattedStartDateTime());
			addParameter(currentNode, "DATE_END", lastModifiedRange.getFormattedEndDateTime());
		}

		addBooleanParameter(methodElement, "ADD_TO_STORED_FILES", options.isAddToStoredFiles());
	}

	/**
	 * Reads ExportTable API response into
	 * {@link com.github.ka4ok85.wca.response.ExportTableResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for ExportTable API
	 * @param options
	 *            - settings for API call
	 * @return POJO Export Table Response
	 */
	@Override
	public ResponseContainer<ExportTableResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, ExportTableOptions options) {
		String description = jobResponse.getJobDescription();
		String listName = jobResponse.getParameters().get("LIST_NAME");
		String remoteFileName = jobResponse.getParameters().get("FILENAME");
		Long listId = Long.parseLong(jobResponse.getParameters().get("LIST_ID"));
		Long numProcessed = Long.parseLong(jobResponse.getParameters().get("NUM_PROCESSED"));
		String filePath = jobPollingContainer.getParameters().get("FILE_PATH");

		log.debug("Generated Export File {} on SFTP", filePath);
		if (options.getLocalAbsoluteFilePath() != null) {
			sftp.download(filePath, options.getLocalAbsoluteFilePath());
		}

		exportTableResponse.setDescription(description);
		exportTableResponse.setRemoteFilePath(filePath);
		exportTableResponse.setListId(listId);
		exportTableResponse.setListName(listName);
		exportTableResponse.setNumProcessed(numProcessed);
		exportTableResponse.setRemoteFileName(remoteFileName);
		ResponseContainer<ExportTableResponse> response = new ResponseContainer<ExportTableResponse>(
				exportTableResponse);

		return response;
	}

}
