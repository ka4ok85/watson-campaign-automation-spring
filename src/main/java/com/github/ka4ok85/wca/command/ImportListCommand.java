package com.github.ka4ok85.wca.command;

import java.io.File;
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

import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.ImportListOptions;
import com.github.ka4ok85.wca.response.ImportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class ImportListCommand extends AbstractCommand<ImportListResponse, ImportListOptions> {

	private static final String apiMethodName = "ImportList";
	private static final Logger log = LoggerFactory.getLogger(ImportListCommand.class);

	@Autowired
	private ImportListResponse importListResponse;
	
	@Override
	public ResponseContainer<ImportListResponse> executeCommand(ImportListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "ImportListOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		File file = new File(options.getMapFile());
		sftp.upload(options.getMapFile(), file.getName());
		Element mapFile = doc.createElement("MAP_FILE");
		mapFile.setTextContent(file.getName());
		addChildNode(mapFile, currentNode);

		file = new File(options.getSourceFile());
		sftp.upload(options.getSourceFile(), file.getName());
		Element sourceFile = doc.createElement("SOURCE_FILE");
		sourceFile.setTextContent(file.getName());
		addChildNode(sourceFile, currentNode);

		Element fileEncoding = doc.createElement("FILE_ENCODING");
		fileEncoding.setTextContent(options.getFileEncoding().value());
		addChildNode(fileEncoding, currentNode);
		
		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		String description;
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);
			Node filePathNode = (Node) xpath.evaluate("FILE_PATH", resultNode, XPathConstants.NODE);

			final Long jobId = Long.parseLong(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);

			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (jobResponse.isComplete()) {
				filePath = filePathNode.getTextContent();
				description = jobResponse.getJobDescription();
				listName = jobResponse.getParameters().get("LIST_NAME");
				keepInStoredFiles = Boolean.valueOf(jobResponse.getParameters().get("KEEP_IN_STORED_FILES"));
				keepInFtpDownloadDirectory = Boolean
						.valueOf(jobResponse.getParameters().get("KEEP_IN_FTP_DOWNLOAD_DIRECTORY"));
				fileEncodingValue = FileEncoding.getFileEncoding(jobResponse.getParameters().get("FILE_ENCODING"));

				log.debug("Generated Export File {} on SFTP", filePath);
				//if (options.getLocalAbsoluteFilePath() != null) {
					//sftp.download(filePath, options.getLocalAbsoluteFilePath());
				//}
			} else {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is "
						+ jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}
		
		Long badAddressesNumber;
		Long badRecordsNumber;
		importListResponse.setBadAddressesNumber(badAddressesNumber);

		importListResponse.setBadRecordsNumber(badRecordsNumber);
		Long calculateListJobId;
		importListResponse.setCalculateListJobId(calculateListJobId);
		importListResponse.setDescription(description);
		Long duplicatesNumber;
		importListResponse.setDuplicatesNumber(duplicatesNumber);
		String errorFileName;
		importListResponse.setErrorFileName(errorFileName);
		Long listId;
		importListResponse.setListId(listId);
		String listName;
		importListResponse.setListName(listName);
		String listProgramLists;
		importListResponse.setListProgramLists(listProgramLists);
		Long notAllowedNumber;
		importListResponse.setNotAllowedNumber(notAllowedNumber);
		String parentFolderName;
		importListResponse.setParentFolderName(parentFolderName);
		String resultsFileName;
		importListResponse.setResultsFileName(resultsFileName);
		boolean smsConsent;
		importListResponse.setSmsConsent(smsConsent);
		Long sqlAddedNumber;
		importListResponse.setSqlAddedNumber(sqlAddedNumber);
		Long sqlUpdatedNumber;
		importListResponse.setSqlUpdatedNumber(sqlUpdatedNumber);
		String syncFieldsFromMappingFile;
		importListResponse.setSyncFieldsFromMappingFile(syncFieldsFromMappingFile);
		Long totalRowsNumber;
		importListResponse.setTotalRowsNumber(totalRowsNumber);
		Long totalValidNumber;
		importListResponse.setTotalValidNumber(totalValidNumber);
		
		ResponseContainer<ImportListResponse> response = new ResponseContainer<ImportListResponse>(importListResponse);

		return response;
	}
}
