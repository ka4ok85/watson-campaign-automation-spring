package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@Service
@Scope("prototype")
public class ExportListCommand extends AbstractJobCommand<ExportListResponse, ExportListOptions> {

	private static final String apiMethodName = "ExportList";
	private static final Logger log = LoggerFactory.getLogger(ExportListCommand.class);

	@Autowired
	private ExportListResponse exportListResponse;

	@Override
	public void buildXmlRequest(ExportListOptions options) {
		Objects.requireNonNull(options, "ExportListOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element exportType = doc.createElement("EXPORT_TYPE");
		exportType.setTextContent(options.getExportType().value());
		addChildNode(exportType, currentNode);

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
		addBooleanParameter(methodElement, "INCLUDE_LEAD_SOURCE", options.isIncludeLeadSource());
		addBooleanParameter(methodElement, "INCLUDE_LIST_ID_IN_FILE", options.isIncludeListId());
		addBooleanParameter(methodElement, "INCLUDE_RECIPIENT_ID", options.isIncludeRecipientId());

		if (options.getExportColumns() != null && options.getExportColumns().size() > 0) {
			Element exportColumns = doc.createElement("EXPORT_COLUMNS");
			addChildNode(exportColumns, currentNode);
			Element columnElement;
			for (String column : options.getExportColumns()) {
				columnElement = doc.createElement("COLUMN");
				columnElement.setTextContent(column);
				addChildNode(columnElement, exportColumns);
			}
		}
	}

	@Override
	public ResponseContainer<ExportListResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, ExportListOptions options) {
		String filePath;
		String description;
		String listName;
		Boolean keepInStoredFiles;
		Boolean keepInFtpDownloadDirectory;
		FileEncoding fileEncodingValue;
		filePath = jobPollingContainer.getParameters().get("FILE_PATH");
		description = jobResponse.getJobDescription();
		listName = jobResponse.getParameters().get("LIST_NAME");
		keepInStoredFiles = Boolean.valueOf(jobResponse.getParameters().get("KEEP_IN_STORED_FILES"));
		keepInFtpDownloadDirectory = Boolean.valueOf(jobResponse.getParameters().get("KEEP_IN_FTP_DOWNLOAD_DIRECTORY"));
		fileEncodingValue = FileEncoding.getFileEncoding(jobResponse.getParameters().get("FILE_ENCODING"));

		log.debug("Generated Export File {} on SFTP", filePath);
		if (options.getLocalAbsoluteFilePath() != null) {
			sftp.download(filePath, options.getLocalAbsoluteFilePath());
		}

		exportListResponse.setRemoteFileName(filePath);
		exportListResponse.setDescription(description);
		exportListResponse.setFileEncoding(fileEncodingValue);
		exportListResponse.setKeepInStoredFiles(keepInStoredFiles);
		exportListResponse.setListName(listName);
		exportListResponse.setKeepInFtpDownloadDirectory(keepInFtpDownloadDirectory);

		ResponseContainer<ExportListResponse> response = new ResponseContainer<ExportListResponse>(exportListResponse);

		return response;
	}

}
