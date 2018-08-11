package com.github.ka4ok85.wca.command;

import java.io.File;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.ImportListOptions;
import com.github.ka4ok85.wca.response.ImportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA ImportList API.</strong> It builds XML
 * request for ImportList API using
 * {@link com.github.ka4ok85.wca.options.ImportListOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.ImportListResponse}.
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
public class ImportListCommand extends AbstractJobCommand<ImportListResponse, ImportListOptions> {

	private static final String apiMethodName = "ImportList";

	@Autowired
	private ImportListResponse importListResponse;

	/**
	 * Builds XML request for ImportList API using
	 * {@link com.github.ka4ok85.wca.options.ImportListOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(ImportListOptions options) {
		Objects.requireNonNull(options, "ImportListOptions must not be null");

		setAllowRetry(false);

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
	}

	/**
	 * Reads ImportList API response into
	 * {@link com.github.ka4ok85.wca.response.ImportListResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for ImportList API
	 * @param options
	 *            - settings for API call
	 * @return POJO Import List Response
	 */
	@Override
	public ResponseContainer<ImportListResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, ImportListOptions options) {
		String description = jobResponse.getJobDescription();
		Long badAddressesNumber = Long.valueOf(jobResponse.getParameters().get("BAD_ADDRESSES"));
		Long badRecordsNumber = Long.valueOf(jobResponse.getParameters().get("BAD_RECORDS"));
		Long calculateListJobId = Long.valueOf(jobResponse.getParameters().get("CALC_LIST_JOB_ID"));
		Long duplicatesNumber = Long.valueOf(jobResponse.getParameters().get("DUPLICATES"));
		String errorFileName = jobResponse.getParameters().get("ERROR_FILE_NAME");
		Long listId = Long.valueOf(jobResponse.getParameters().get("LIST_ID"));
		String listName = jobResponse.getParameters().get("LIST_NAME");
		String listProgramLists = jobResponse.getParameters().get("LIST_PROGRAM_LISTS");
		Long notAllowedNumber = Long.valueOf(jobResponse.getParameters().get("NOT_ALLOWED"));
		String parentFolderName = jobResponse.getParameters().get("PARENT_FOLDER_NAME");
		String resultsFileName = jobResponse.getParameters().get("RESULTS_FILE_NAME");
		boolean smsConsent = Boolean.valueOf(jobResponse.getParameters().get("CONSENT"));
		Long sqlAddedNumber = Long.valueOf(jobResponse.getParameters().get("SQL_ADDED"));
		Long sqlUpdatedNumber = Long.valueOf(jobResponse.getParameters().get("SQL_UPDATED"));
		String syncFieldsFromMappingFile = jobResponse.getParameters().get("SYNC_FIELDS_FROM_MAPPING_FILE");
		Long totalRowsNumber = Long.valueOf(jobResponse.getParameters().get("TOTAL_ROWS"));
		Long totalValidNumber = Long.valueOf(jobResponse.getParameters().get("TOTAL_VALID"));

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
