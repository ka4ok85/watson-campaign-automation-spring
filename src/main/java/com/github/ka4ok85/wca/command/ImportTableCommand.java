package com.github.ka4ok85.wca.command;

import java.io.File;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.ImportTableOptions;
import com.github.ka4ok85.wca.response.ImportTableResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA ImportTable API.</strong> It builds
 * XML request for ImportTable API using
 * {@link com.github.ka4ok85.wca.options.ImportTableOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.ImportTableResponse}.
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
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class ImportTableCommand extends AbstractJobCommand<ImportTableResponse, ImportTableOptions> {

	private static final String apiMethodName = "ImportTable";

	@Autowired
	private ImportTableResponse importTableResponse;

	/**
	 * Builds XML request for ImportTable API using
	 * {@link com.github.ka4ok85.wca.options.ImportTableOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(ImportTableOptions options) {
		Objects.requireNonNull(options, "ImportTableOptions must not be null");

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
	 * Reads ImportTable API response into
	 * {@link com.github.ka4ok85.wca.response.ImportTableResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for ImportTable API
	 * @param options
	 *            - settings for API call
	 * @return POJO ImportTable Response
	 */
	@Override
	public ResponseContainer<ImportTableResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, ImportTableOptions options) {
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

		importTableResponse.setBadAddressesNumber(badAddressesNumber);
		importTableResponse.setBadRecordsNumber(badRecordsNumber);
		importTableResponse.setCalculateListJobId(calculateListJobId);
		importTableResponse.setDescription(jobResponse.getJobDescription());
		importTableResponse.setJobId(jobResponse.getJobId());
		importTableResponse.setDuplicatesNumber(duplicatesNumber);
		importTableResponse.setErrorFileName(errorFileName);
		importTableResponse.setListId(listId);
		importTableResponse.setListName(listName);
		importTableResponse.setListProgramLists(listProgramLists);
		importTableResponse.setNotAllowedNumber(notAllowedNumber);
		importTableResponse.setParentFolderName(parentFolderName);
		importTableResponse.setResultsFileName(resultsFileName);
		importTableResponse.setSmsConsent(smsConsent);
		importTableResponse.setSqlAddedNumber(sqlAddedNumber);
		importTableResponse.setSqlUpdatedNumber(sqlUpdatedNumber);
		importTableResponse.setSyncFieldsFromMappingFile(syncFieldsFromMappingFile);
		importTableResponse.setTotalRowsNumber(totalRowsNumber);
		importTableResponse.setTotalValidNumber(totalValidNumber);

		ResponseContainer<ImportTableResponse> response = new ResponseContainer<ImportTableResponse>(
				importTableResponse);

		return response;
	}

}