package com.github.ka4ok85.wca.command;

import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.sftp.SFTP;
import com.github.ka4ok85.wca.utils.DateTimeRange;

public class ExportListCommand extends AbstractCommand<ExportListResponse, ExportListOptions> {

	private static final String apiMethodName = "ExportList";
	private static final Logger log = LoggerFactory.getLogger(ExportListCommand.class);

	public ExportListCommand(OAuthClient oAuthClient, SFTP sftp) {
		super(oAuthClient, sftp);
	}


	@Override
	public ResponseContainer<ExportListResponse> executeCommand(ExportListOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
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

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);
		
    	XPathFactory factory = XPathFactory.newInstance();
    	XPath xpath = factory.newXPath();
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);
			Node filePathNode = (Node) xpath.evaluate("FILE_PATH", resultNode, XPathConstants.NODE);

			int jobId = Integer.parseInt(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);
			
			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (jobResponse.isComplete()) {
				String filePath = filePathNode.getTextContent();
				log.debug("Generated Export File {} on SFTP", filePath);
				if (options.getLocalAbsoluteFilePath() != null) {
					sftp.download(filePath, options.getLocalAbsoluteFilePath());
				}
			} else {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is " + jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ExportListResponse exportListResponse = new ExportListResponse();
		ResponseContainer<ExportListResponse> response = new ResponseContainer<ExportListResponse>(exportListResponse);

		return response;
	}
}
