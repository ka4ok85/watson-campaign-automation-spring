package com.github.ka4ok85.wca.command;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.options.ImportListOptions;
import com.github.ka4ok85.wca.response.ImportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })

public class ImportListCommandTest {

	@Autowired
	ApplicationContext context;

	@Test
	public void testDummy() {
		assertTrue(true);
	}
	// TODO: add mocks
	/*
	@Test
	public void testReadResponse() {
		ImportListCommand command = context.getBean(ImportListCommand.class);

		ImportListOptions options = new ImportListOptions(
				"src\\main\\java\\com\\github\\ka4ok85\\wca\\command\\ImportListCommand.java",
				"src\\test\\java\\com\\github\\ka4ok85\\wca\\command\\ImportListCommandTest.java");

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
	*/
}
