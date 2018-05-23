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
		Long badAddressesNumber;
		Long badRecordsNumber;
		Long calculateListJobId;
		Long duplicatesNumber;
		String errorFileName;
		Long listId;
		String listName;
		String listProgramLists;
		Long notAllowedNumber;
		String parentFolderName;
		String resultsFileName;
		boolean smsConsent;
		Long sqlAddedNumber;
		Long sqlUpdatedNumber;
		String syncFieldsFromMappingFile;
		Long totalRowsNumber;
		Long totalValidNumber;
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);

			final Long jobId = Long.parseLong(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);

			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (jobResponse.isComplete()) {
				description = jobResponse.getJobDescription();
				badAddressesNumber = Long.valueOf(jobResponse.getParameters().get("BAD_ADDRESSES"));
				badRecordsNumber = Long.valueOf(jobResponse.getParameters().get("BAD_RECORDS"));
				calculateListJobId = Long.valueOf(jobResponse.getParameters().get("CALC_LIST_JOB_ID"));
				duplicatesNumber = Long.valueOf(jobResponse.getParameters().get("DUPLICATES"));
				errorFileName = jobResponse.getParameters().get("ERROR_FILE_NAME");
				listId = Long.valueOf(jobResponse.getParameters().get("LIST_ID"));
				listName = jobResponse.getParameters().get("LIST_NAME");
				listProgramLists = jobResponse.getParameters().get("LIST_PROGRAM_LISTS");
				notAllowedNumber = Long.valueOf(jobResponse.getParameters().get("NOT_ALLOWED"));
				parentFolderName = jobResponse.getParameters().get("PARENT_FOLDER_NAME");
				resultsFileName = jobResponse.getParameters().get("RESULTS_FILE_NAME");
				smsConsent = Boolean.valueOf(jobResponse.getParameters().get("CONSENT"));
				sqlAddedNumber = Long.valueOf(jobResponse.getParameters().get("SQL_ADDED"));
				sqlUpdatedNumber = Long.valueOf(jobResponse.getParameters().get("SQL_UPDATED"));
				syncFieldsFromMappingFile = jobResponse.getParameters().get("SYNC_FIELDS_FROM_MAPPING_FILE");
				totalRowsNumber = Long.valueOf(jobResponse.getParameters().get("TOTAL_ROWS"));
				totalValidNumber = Long.valueOf(jobResponse.getParameters().get("TOTAL_VALID"));
			} else {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is "
						+ jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		importListResponse.setBadAddressesNumber(badAddressesNumber);
		importListResponse.setBadRecordsNumber(badRecordsNumber);
		importListResponse.setCalculateListJobId(calculateListJobId);
		importListResponse.setDescription(description);
		importListResponse.setDuplicatesNumber(duplicatesNumber);
		importListResponse.setErrorFileName(errorFileName);
		importListResponse.setListId(listId);
		importListResponse.setListName(listName);
		importListResponse.setListProgramLists(listProgramLists);
		importListResponse.setNotAllowedNumber(notAllowedNumber);
		importListResponse.setParentFolderName(parentFolderName);
		importListResponse.setResultsFileName(resultsFileName);
		importListResponse.setSmsConsent(smsConsent);
		importListResponse.setSqlAddedNumber(sqlAddedNumber);
		importListResponse.setSqlUpdatedNumber(sqlUpdatedNumber);
		importListResponse.setSyncFieldsFromMappingFile(syncFieldsFromMappingFile);
		importListResponse.setTotalRowsNumber(totalRowsNumber);
		importListResponse.setTotalValidNumber(totalValidNumber);

		ResponseContainer<ImportListResponse> response = new ResponseContainer<ImportListResponse>(importListResponse);

		return response;
	}
}
