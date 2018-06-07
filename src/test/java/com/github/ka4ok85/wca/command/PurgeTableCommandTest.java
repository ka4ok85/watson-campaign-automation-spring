package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.github.ka4ok85.wca.options.PurgeTableOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.PurgeTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class PurgeTableCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<PurgeTable>",
			"<TABLE_ID>1</TABLE_ID>", "</PurgeTable>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		PurgeTableCommand command = new PurgeTableCommand();
		PurgeTableOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		PurgeTableCommand command = new PurgeTableCommand();

		PurgeTableOptions options = new PurgeTableOptions();
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
	public void testBuildXmlAcceptsNames() {
		// get XML from command
		PurgeTableCommand command = new PurgeTableCommand();

		PurgeTableOptions options = new PurgeTableOptions();
		String tableName = "test table 1";
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

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresTableIdOrTableName() {
		PurgeTableCommand command = new PurgeTableCommand();

		PurgeTableOptions options = new PurgeTableOptions();
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlHonorsTableVisibility() {
		// get XML from command
		PurgeTableCommand command = new PurgeTableCommand();

		PurgeTableOptions options = new PurgeTableOptions();
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
	public void testBuildXmlHonorsDeleteBefore() {
		// get XML from command
		PurgeTableCommand command = new PurgeTableCommand();

		PurgeTableOptions options = new PurgeTableOptions();
		options.setTableId(1L);
		LocalDateTime deleteBefore = LocalDateTime.now();
		options.setDeleteBefore(deleteBefore);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String controlString = defaultRequest.replace("</TABLE_ID>",
				"</TABLE_ID><DELETE_BEFORE>" + deleteBefore.format(formatter) + "</DELETE_BEFORE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse() {
		PurgeTableCommand command = context.getBean(PurgeTableCommand.class);

		PurgeTableOptions options = new PurgeTableOptions();
		options.setTableId(1L);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		Long jobId = 10L;
		jobPollingContainer.setJobId(10L);

		String jobDescription = "String Job Description";
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);

		ResponseContainer<PurgeTableResponse> responseContainer = command.readResponse(jobPollingContainer, jobResponse,
				options);
		PurgeTableResponse response = responseContainer.getResposne();

		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getDescription(), jobDescription);
	}
}
