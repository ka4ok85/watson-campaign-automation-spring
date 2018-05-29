package com.github.ka4ok85.wca.command;

import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@Service
@Scope("prototype")
public class ExportTableCommand extends AbstractCommand<ExportTableResponse, ExportTableOptions> {

	private static final String apiMethodName = "ExportTable";
	private static final Logger log = LoggerFactory.getLogger(ExportTableCommand.class);

	@Autowired
	private ExportTableResponse exportTableResponse;

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

	@Override
	public ResponseContainer<ExportTableResponse> readResponse(Node resultNode, ExportTableOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		String description;
		String remoteFileName;
		String listName;
		Long listId;
		Long numProcessed;
		String filePath;
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);
			Node filePathNode = (Node) xpath.evaluate("FILE_PATH", resultNode, XPathConstants.NODE);

			final Long jobId = Long.parseLong(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);

			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (jobResponse.isComplete()) {
				description = jobResponse.getJobDescription();
				listName = jobResponse.getParameters().get("LIST_NAME");
				remoteFileName = jobResponse.getParameters().get("FILENAME");
				listId = Long.parseLong(jobResponse.getParameters().get("LIST_ID"));
				numProcessed = Long.parseLong(jobResponse.getParameters().get("NUM_PROCESSED"));

				filePath = filePathNode.getTextContent();
				log.debug("Generated Export File {} on SFTP", filePath);
				if (options.getLocalAbsoluteFilePath() != null) {
					sftp.download(filePath, options.getLocalAbsoluteFilePath());
				}
			} else {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is "
						+ jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
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
