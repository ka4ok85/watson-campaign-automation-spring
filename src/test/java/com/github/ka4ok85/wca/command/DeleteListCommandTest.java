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
import com.github.ka4ok85.wca.options.DeleteListOptions;
import com.github.ka4ok85.wca.response.DeleteListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class DeleteListCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<DeleteList>",
			"<LIST_ID>1</LIST_ID>", "<KEEP_DETAILS>TRUE</KEEP_DETAILS>", "<RECURSIVE>FALSE</RECURSIVE>",
			"</DeleteList>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		DeleteListCommand command = new DeleteListCommand();
		DeleteListOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		DeleteListCommand command = new DeleteListCommand();
		DeleteListOptions options = new DeleteListOptions(1L);
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
	public void testBuildXmlHonorsKeepListDetails() {
		// get XML from command
		DeleteListCommand command = new DeleteListCommand();
		DeleteListOptions options = new DeleteListOptions(1L);
		options.setKeepListDetails(false);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<KEEP_DETAILS>TRUE</KEEP_DETAILS>",
				"<KEEP_DETAILS>FALSE</KEEP_DETAILS>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsIsRecursive() {
		// get XML from command
		DeleteListCommand command = new DeleteListCommand();
		DeleteListOptions options = new DeleteListOptions(1L);
		options.setRecursive(true);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<RECURSIVE>FALSE</RECURSIVE>", "<RECURSIVE>TRUE</RECURSIVE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlAlternativeConstructor() {
		// get XML from command
		DeleteListCommand command = new DeleteListCommand();
		String listName = "list 1";
		Visibility visibility = Visibility.PRIVATE;
		DeleteListOptions options = new DeleteListOptions(listName, visibility);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<LIST_ID>1</LIST_ID>", "<LIST_NAME>" + listName
				+ "</LIST_NAME><LIST_VISIBILITY>" + visibility.value().toString() + "</LIST_VISIBILITY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse() {
		DeleteListCommand command = context.getBean(DeleteListCommand.class);

		DeleteListOptions options = new DeleteListOptions(1L);
		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		Long jobId = 10L;
		jobPollingContainer.setJobId(jobId);

		String jobDescription = "String Job Description";
		String listId = "123";
		String listName = "Test List 2";
		String numberRemoved = "23";
		String ignoreDependentMailings = "FALSE";
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("SOURCE_LIST_ID", listId);
		jobParameters.put("LIST_NAME", listName);
		jobParameters.put("NUM_REMOVED", numberRemoved);
		jobParameters.put("IGNORE_DEPENDENT_MAILINGS", ignoreDependentMailings);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<DeleteListResponse> responseContainer = command.readResponse(jobPollingContainer, jobResponse,
				options);
		DeleteListResponse response = responseContainer.getResposne();

		assertEquals(response.getDescription(), jobDescription);
		assertEquals(response.getListName(), listName);
		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getListId(), Long.valueOf(listId));
		assertEquals(response.getNumberRemoved(), Long.valueOf(numberRemoved));
		assertEquals(response.isIgnoreDependentMailings(), Boolean.getBoolean(ignoreDependentMailings));
	}
}
