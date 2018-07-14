package com.github.ka4ok85.wca.command;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.options.ImportListOptions;
import com.github.ka4ok85.wca.response.ImportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.sftp.SFTP;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })

public class ImportListCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<ImportList>",
			"<MAP_FILE>testmap.xml</MAP_FILE>", "<SOURCE_FILE>testsource.csv</SOURCE_FILE>",
			"<FILE_ENCODING>iso-8859-1</FILE_ENCODING>", "</ImportList>", "</Body>", "</Envelope>");

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		ImportListCommand command = new ImportListCommand();
		SFTP sftp = mock(SFTP.class);
		command.setSftp(sftp);
		ImportListOptions options = mock(ImportListOptions.class);

		when(options.getMapFile()).thenReturn("testmap.xml");
		when(options.getSourceFile()).thenReturn("testsource.csv");
		when(options.getFileEncoding()).thenReturn(FileEncoding.ISO_8859_1);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest;
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
		verify(options, times(2)).getMapFile();
		verify(options, times(2)).getSourceFile();
		verify(options, times(1)).getFileEncoding();

		verify(sftp, times(1)).upload(options.getMapFile(), options.getMapFile());
		verify(sftp, times(1)).upload(options.getSourceFile(), options.getSourceFile());
	}

	@Test
	public void testReadResponse() {
		ImportListCommand command = context.getBean(ImportListCommand.class);
		ImportListOptions options = mock(ImportListOptions.class);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		jobPollingContainer.setJobId(10L);

		String jobDescription = "String Job Description";
		String listName = "Test List";
		String badAddressesNumber = "1";
		String badRecordsNumber = "2";
		String calculateListJobId = "3";
		String duplicatesNumber = "4";
		String errorFileName = "error.res";
		String listId = "5";
		String listProgramLists = "lists";
		String notAllowedNumber = "6";
		String parentFolderName = "parent folder";
		String resultsFileName = "result.res";
		String sqlAddedNumber = "7";
		String sqlUpdatedNumber = "8";
		String syncFieldsFromMappingFile = "test";
		String totalRowsNumber = "9";
		String totalValidNumber = "10";

		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("LIST_NAME", listName);
		jobParameters.put("BAD_ADDRESSES", badAddressesNumber);
		jobParameters.put("BAD_RECORDS", badRecordsNumber);
		jobParameters.put("CALC_LIST_JOB_ID", calculateListJobId);
		jobParameters.put("DUPLICATES", duplicatesNumber);
		jobParameters.put("ERROR_FILE_NAME", errorFileName);
		jobParameters.put("LIST_ID", listId);
		jobParameters.put("NOT_ALLOWED", notAllowedNumber);
		jobParameters.put("PARENT_FOLDER_NAME", parentFolderName);
		jobParameters.put("RESULTS_FILE_NAME", resultsFileName);
		jobParameters.put("SQL_ADDED", sqlAddedNumber);
		jobParameters.put("SQL_UPDATED", sqlUpdatedNumber);
		jobParameters.put("SYNC_FIELDS_FROM_MAPPING_FILE", syncFieldsFromMappingFile);
		jobParameters.put("TOTAL_ROWS", totalRowsNumber);
		jobParameters.put("TOTAL_VALID", totalValidNumber);
		jobParameters.put("LIST_PROGRAM_LISTS", listProgramLists);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<ImportListResponse> responseContainer = command.readResponse(jobPollingContainer, jobResponse,
				options);
		ImportListResponse response = responseContainer.getResposne();

		assertEquals(response.getDescription(), jobDescription);
		assertEquals(response.getBadAddressesNumber(), Long.valueOf(badAddressesNumber));
		assertEquals(response.getBadRecordsNumber(), Long.valueOf(badRecordsNumber));
		assertEquals(response.getCalculateListJobId(), Long.valueOf(calculateListJobId));
		assertEquals(response.getDuplicatesNumber(), Long.valueOf(duplicatesNumber));
		assertEquals(response.getErrorFileName(), errorFileName);
		assertEquals(response.getListId(), Long.valueOf(listId));
		assertEquals(response.getListName(), listName);
		assertEquals(response.getNotAllowedNumber(), Long.valueOf(notAllowedNumber));
		assertEquals(response.getParentFolderName(), parentFolderName);
		assertEquals(response.getResultsFileName(), resultsFileName);
		assertEquals(response.getSqlAddedNumber(), Long.valueOf(sqlAddedNumber));
		assertEquals(response.getSqlUpdatedNumber(), Long.valueOf(sqlUpdatedNumber));
		assertEquals(response.getSyncFieldsFromMappingFile(), syncFieldsFromMappingFile);
		assertEquals(response.getTotalRowsNumber(), Long.valueOf(totalRowsNumber));
		assertEquals(response.getTotalValidNumber(), Long.valueOf(totalValidNumber));
		assertEquals(response.isSmsConsent(), Boolean.valueOf(totalValidNumber));
		assertEquals(response.getListProgramLists(), listProgramLists);
	}
}
