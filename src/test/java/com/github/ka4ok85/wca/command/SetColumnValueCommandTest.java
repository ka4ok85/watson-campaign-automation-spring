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
import com.github.ka4ok85.wca.constants.ColumnValueAction;
import com.github.ka4ok85.wca.constants.ListExportType;
import com.github.ka4ok85.wca.options.SetColumnValueOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SetColumnValueResponse;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })

public class SetColumnValueCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<SetColumnValue>",
			"<LIST_ID>1</LIST_ID>", "<COLUMN_NAME>test column</COLUMN_NAME>", "<ACTION>0</ACTION>", "</SetColumnValue>",
			"</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		SetColumnValueCommand command = new SetColumnValueCommand();
		SetColumnValueOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		SetColumnValueCommand command = new SetColumnValueCommand();
		SetColumnValueOptions options = new SetColumnValueOptions(1L, "test column", ColumnValueAction.RESET);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest;
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlDoesNotAcceptColumnValueWithWrongAction() {
		// get XML from command
		SetColumnValueCommand command = new SetColumnValueCommand();
		SetColumnValueOptions options = new SetColumnValueOptions(1L, "test column", ColumnValueAction.RESET);
		options.setColumnValue("test value");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EXPORT_TYPE>ALL</EXPORT_TYPE>",
				"<EXPORT_TYPE>" + ListExportType.UNDELIVERABLE.value() + "</EXPORT_TYPE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsColumnValue() {
		// get XML from command
		SetColumnValueCommand command = new SetColumnValueCommand();
		SetColumnValueOptions options = new SetColumnValueOptions(1L, "test column", ColumnValueAction.UPDATE);
		String value = "test value";
		options.setColumnValue(value);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<ACTION>0</ACTION>",
				"<ACTION>1</ACTION><COLUMN_VALUE>" + value + "</COLUMN_VALUE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse() {
		SetColumnValueCommand command = context.getBean(SetColumnValueCommand.class);
		SetColumnValueOptions options = new SetColumnValueOptions(1L, "test column", ColumnValueAction.RESET);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		Long jobId = 10L;
		jobPollingContainer.setJobId(jobId);

		String jobDescription = "String Job Description";
		String listName = "Test List";
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("LIST_NAME", listName);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<SetColumnValueResponse> responseContainer = command.readResponse(jobPollingContainer,
				jobResponse, options);
		SetColumnValueResponse response = responseContainer.getResposne();

		assertEquals(response.getDescription(), jobDescription);
		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getListName(), listName);
	}
}
