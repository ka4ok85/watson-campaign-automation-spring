package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import com.github.ka4ok85.wca.options.OptOutRecipientOptions;
import com.github.ka4ok85.wca.response.OptOutRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class OptOutRecipientCommandTest {

	@Autowired
	ApplicationContext context;
	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<OptOutRecipient>",
			"<LIST_ID>1</LIST_ID>", "<EMAIL>test@github.com</EMAIL>", "</OptOutRecipient>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		OptOutRecipientCommand command = new OptOutRecipientCommand();
		OptOutRecipientOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		OptOutRecipientCommand command = new OptOutRecipientCommand();
		OptOutRecipientOptions options = new OptOutRecipientOptions(1L);
		options.setEmail("test@github.com");

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
	public void testBuildXmlHonorsNonEmail() {
		// get XML from command
		OptOutRecipientCommand command = new OptOutRecipientCommand();
		OptOutRecipientOptions options = new OptOutRecipientOptions(1L);
		String jobId = "test-job";
		options.setJobId(jobId);
		Long mailingId = 2L;
		options.setMailingId(mailingId);
		String recipientId = "test-recipient-id";
		options.setRecipientId(recipientId);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EMAIL>test@github.com</EMAIL>", "<RECIPIENT_ID>" + recipientId
				+ "</RECIPIENT_ID><JOB_ID>" + jobId + "</JOB_ID><MAILING_ID>" + mailingId + "</MAILING_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlHonorsRequiresEmailOrRecipientId() {
		// get XML from command
		OptOutRecipientCommand command = new OptOutRecipientCommand();
		OptOutRecipientOptions options = new OptOutRecipientOptions(1L);

		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlHonorsColumns() {
		// get XML from command
		OptOutRecipientCommand command = new OptOutRecipientCommand();
		OptOutRecipientOptions options = new OptOutRecipientOptions(1L);
		options.setEmail("test@github.com");

		Map<String, String> columns = new HashMap<String, String>();
		columns.put("Email", "test1@github.com");
		columns.put("customerID", "123");
		options.setColumns(columns);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String columnsString = "";
		for (Entry<String, String> entry : columns.entrySet()) {
			columnsString = columnsString + "<COLUMN><NAME>" + entry.getKey() + "</NAME><VALUE>" + entry.getValue()
					+ "</VALUE></COLUMN>";
		}

		String controlString = defaultRequest.replace("<EMAIL>test@github.com</EMAIL>",
				"<EMAIL>test@github.com</EMAIL>" + columnsString);
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		OptOutRecipientCommand command = new OptOutRecipientCommand();
		OptOutRecipientOptions options = new OptOutRecipientOptions(1L);
		options.setEmail("test@github.com");

		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(
						"<Envelope><Body><RESULT><SUCCESS>TRUE</SUCCESS></RESULT></Body></Envelope>".getBytes()))
				.getDocumentElement();

		ResponseContainer<OptOutRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		OptOutRecipientResponse response = responseContainer.getResposne();
		assertEquals(response, null);
	}
}
