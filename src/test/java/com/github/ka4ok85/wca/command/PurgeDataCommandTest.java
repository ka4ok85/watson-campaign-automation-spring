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
import com.github.ka4ok85.wca.options.PurgeDataOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.PurgeDataResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class PurgeDataCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<PurgeData>",
			"<TARGET_ID>1</TARGET_ID>", "<SOURCE_ID>2</SOURCE_ID>", "</PurgeData>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		PurgeDataCommand command = new PurgeDataCommand();
		PurgeDataOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		PurgeDataCommand command = new PurgeDataCommand();
		PurgeDataOptions options = new PurgeDataOptions(1L, 2L);
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
		PurgeDataCommand command = context.getBean(PurgeDataCommand.class);
		PurgeDataOptions options = new PurgeDataOptions(1L, 2L);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		Long jobId = 10L;
		jobPollingContainer.setJobId(10L);

		String jobDescription = "String Job Description";
		String purgedContactListId = "1";
		String purgedContactListName = "Purged Contact List";
		String purgedContactListSize = "300";
		String sourceListName = "Test Source List";
		String targetListName = "Test Target List";

		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("LIST_ID_C", purgedContactListId);
		jobParameters.put("LIST_NAME_C", purgedContactListName);
		jobParameters.put("LIST_SIZE", purgedContactListSize);
		jobParameters.put("LIST2_NAME", sourceListName);
		jobParameters.put("LIST1_NAME", targetListName);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<PurgeDataResponse> responseContainer = command.readResponse(jobPollingContainer, jobResponse,
				options);
		PurgeDataResponse response = responseContainer.getResposne();

		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getDescription(), jobDescription);
		assertEquals(response.getPurgedContactListId(), Long.valueOf(purgedContactListId));
		assertEquals(response.getPurgedContactListName(), purgedContactListName);
		assertEquals(response.getPurgedContactListSize(), Long.valueOf(purgedContactListSize));
		assertEquals(response.getSourceListName(), sourceListName);
		assertEquals(response.getTargetListName(), targetListName);
	}
}
