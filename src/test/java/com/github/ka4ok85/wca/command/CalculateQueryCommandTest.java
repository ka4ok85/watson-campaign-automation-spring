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
import com.github.ka4ok85.wca.options.CalculateQueryOptions;
import com.github.ka4ok85.wca.response.CalculateQueryResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class CalculateQueryCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<CalculateQuery>",
			"<QUERY_ID>1</QUERY_ID>", "</CalculateQuery>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		CalculateQueryCommand command = new CalculateQueryCommand();
		CalculateQueryOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		CalculateQueryCommand command = new CalculateQueryCommand();
		CalculateQueryOptions options = new CalculateQueryOptions(1L);
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
	public void testReadResponse() {
		CalculateQueryCommand command = context.getBean(CalculateQueryCommand.class);
		CalculateQueryOptions options = new CalculateQueryOptions(1L);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		Long jobId = 10L;
		jobPollingContainer.setJobId(10L);

		String jobDescription = "String Job Description";
		String listName = "Test List";
		String listSize = "10004";
		String parentListId = "345";
		String parentListName = "Test Parent List";
		String queryExpressionId = "300";
		String parentListType = "Test Type";

		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("LIST_NAME", listName);
		jobParameters.put("LIST_SIZE", listSize);
		jobParameters.put("PARENT_LIST_ID", parentListId);
		jobParameters.put("PARENT_LIST_NAME", parentListName);
		jobParameters.put("QUERY_EXPRESSION_ID", queryExpressionId);
		jobParameters.put("PARENT_LIST_TYPE", parentListType);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<CalculateQueryResponse> responseContainer = command.readResponse(jobPollingContainer,
				jobResponse, options);
		CalculateQueryResponse response = responseContainer.getResposne();

		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getDescription(), jobDescription);

		assertEquals(response.getListName(), listName);
		assertEquals(response.getListSize(), Long.valueOf(listSize));
		assertEquals(response.getParentListId(), Long.valueOf(parentListId));
		assertEquals(response.getParentListName(), parentListName);
		assertEquals(response.getParentListType(), parentListType);
		assertEquals(response.getQueryExpressionId(), Long.valueOf(queryExpressionId));

	}
}
