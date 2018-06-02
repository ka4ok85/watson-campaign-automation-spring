package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

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
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.DeleteTableOptions;
import com.github.ka4ok85.wca.response.DeleteTableResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class DeleteTableCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<DeleteTable>",
			"<TABLE_ID>1</TABLE_ID>", "</DeleteTable>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		DeleteTableCommand command = new DeleteTableCommand();
		DeleteTableOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresTableIdorTableName() {
		DeleteTableCommand command = new DeleteTableCommand();
		DeleteTableOptions options = new DeleteTableOptions();
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		DeleteTableCommand command = new DeleteTableCommand();
		DeleteTableOptions options = new DeleteTableOptions();
		options.setTableId(1L);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest;
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsVisibility() {
		// get XML from command
		DeleteTableCommand command = new DeleteTableCommand();
		DeleteTableOptions options = new DeleteTableOptions();
		options.setTableId(1L);
		Visibility tableVisibility = Visibility.SHARED;
		options.setTableVisibility(tableVisibility);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</TABLE_ID>",
				"</TABLE_ID><TABLE_VISIBILITY>" + tableVisibility.value().toString() + "</TABLE_VISIBILITY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsTableName() {
		// get XML from command
		DeleteTableCommand command = new DeleteTableCommand();
		DeleteTableOptions options = new DeleteTableOptions();
		String tableName = "table 1";
		options.setTableName(tableName);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<TABLE_ID>1</TABLE_ID>",
				"<TABLE_NAME>" + tableName + "</TABLE_NAME>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse() {
		DeleteTableCommand command = context.getBean(DeleteTableCommand.class);

		DeleteTableOptions options = new DeleteTableOptions();
		options.setTableId(1L);
		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		Long jobId = 10L;
		jobPollingContainer.setJobId(jobId);

		String jobDescription = "String Job Description";
		String listId = "123";
		String listName = "Test List 2";
		String numberRemoved = "23";
		String ignoreDependentMailings = "TRUE";
		String isKeepListDetails = "TRUE";
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("SOURCE_LIST_ID", listId);
		jobParameters.put("LIST_NAME", listName);
		jobParameters.put("NUM_REMOVED", numberRemoved);
		jobParameters.put("IGNORE_DEPENDENT_MAILINGS", ignoreDependentMailings);
		jobParameters.put("IS_KEEP_LIST_DETAILS", isKeepListDetails);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<DeleteTableResponse> responseContainer = command.readResponse(jobPollingContainer,
				jobResponse, options);
		DeleteTableResponse response = responseContainer.getResposne();

		assertEquals(response.getDescription(), jobDescription);
		assertEquals(response.getListName(), listName);
		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getListId(), Long.valueOf(listId));
		assertEquals(response.getNumberRemoved(), Long.valueOf(numberRemoved));
		assertEquals(response.isIgnoreDependentMailings(), Boolean.valueOf(ignoreDependentMailings));
		assertEquals(response.isKeepListDetails(), Boolean.valueOf(isKeepListDetails));
	}

}
