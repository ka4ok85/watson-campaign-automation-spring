package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.JobStatus;
import com.github.ka4ok85.wca.exceptions.InternalApiMismatchException;
import com.github.ka4ok85.wca.options.JobOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class WaitForJobCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<GetJobStatus>",
			"<JOB_ID>1</JOB_ID>", "</GetJobStatus>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		WaitForJobCommand command = new WaitForJobCommand();
		JobOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		WaitForJobCommand command = new WaitForJobCommand();
		JobOptions options = new JobOptions(1L);

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
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		WaitForJobCommand command = context.getBean(WaitForJobCommand.class);
		Long jobId = 6020497L;
		JobOptions options = new JobOptions(jobId);

		JobStatus jobStatus = JobStatus.COMPLETE;
		String jobDescription = "Exporting all contact source data, Preference Center";
		String name1 = "FILE_ENCODING";
		String value1 = "utf-8";
		String name2 = "EXPORT_FORMAT";
		String value2 = "0";

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><JOB_ID>" + jobId + "</JOB_ID><JOB_STATUS>" + jobStatus
				+ "</JOB_STATUS><JOB_DESCRIPTION>" + jobDescription + "</JOB_DESCRIPTION><PARAMETERS><PARAMETER><NAME>"
				+ name1 + "</NAME><VALUE>" + value1 + "</VALUE></PARAMETER><PARAMETER><NAME>" + name2 + "</NAME><VALUE>"
				+ value2 + "</VALUE></PARAMETER></PARAMETERS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<JobResponse> responseContainer = command.readResponse(resultNode, options);
		JobResponse response = responseContainer.getResposne();

		assertEquals(response.getJobDescription(), jobDescription);
		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getJobStatus(), jobStatus);

		assertEquals(response.getParameters().size(), 2);
		assertTrue(response.getParameters().containsKey(name1));
		assertEquals(response.getParameters().get(name1), value1);
		assertTrue(response.getParameters().containsKey(name2));
		assertEquals(response.getParameters().get(name2), value2);
	}

	@Test(expected = InternalApiMismatchException.class)
	public void testApiJobMismatch()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		WaitForJobCommand command = context.getBean(WaitForJobCommand.class);
		JobOptions options = new JobOptions(1L);

		JobStatus jobStatus = JobStatus.COMPLETE;
		String jobDescription = "Exporting all contact source data, Preference Center";
		String name1 = "FILE_ENCODING";
		String value1 = "utf-8";
		String name2 = "EXPORT_FORMAT";
		String value2 = "0";

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><JOB_ID>" + 2 + "</JOB_ID><JOB_STATUS>" + jobStatus
				+ "</JOB_STATUS><JOB_DESCRIPTION>" + jobDescription + "</JOB_DESCRIPTION><PARAMETERS><PARAMETER><NAME>"
				+ name1 + "</NAME><VALUE>" + value1 + "</VALUE></PARAMETER><PARAMETER><NAME>" + name2 + "</NAME><VALUE>"
				+ value2 + "</VALUE></PARAMETER></PARAMETERS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<JobResponse> responseContainer = command.readResponse(resultNode, options);
		System.out.println(responseContainer);
	}

}
