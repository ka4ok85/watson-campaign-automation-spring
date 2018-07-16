package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.github.ka4ok85.wca.options.DoubleOptInRecipientOptions;
import com.github.ka4ok85.wca.response.DoubleOptInRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class DoubleOptInRecipientCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<DoubleOptInRecipient>", "<LIST_ID>1</LIST_ID>", "<SEND_AUTOREPLY>FALSE</SEND_AUTOREPLY>",
			"<ALLOW_HTML>TRUE</ALLOW_HTML>", "<COLUMN>", "<NAME>", "<![CDATA[customerID]]>", "</NAME>", "<VALUE>",
			"<![CDATA[123]]>", "</VALUE>", "</COLUMN>", "<COLUMN>", "<NAME>", "<![CDATA[EMAIL]]>", "</NAME>", "<VALUE>",
			"<![CDATA[test1@github.com]]>", "</VALUE>", "</COLUMN>", "</DoubleOptInRecipient>", "</Body>",
			"</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		DoubleOptInRecipientCommand command = new DoubleOptInRecipientCommand();
		DoubleOptInRecipientOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		DoubleOptInRecipientCommand command = new DoubleOptInRecipientCommand();
		DoubleOptInRecipientOptions options = new DoubleOptInRecipientOptions(1L);
		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("EMAIL", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

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
	public void testBuildXmlRequiresColumns() {
		// get XML from command
		DoubleOptInRecipientCommand command = new DoubleOptInRecipientCommand();
		DoubleOptInRecipientOptions options = new DoubleOptInRecipientOptions(1L);

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
	public void testBuildXmlRequiresEMAILColumn() {
		// get XML from command
		DoubleOptInRecipientCommand command = new DoubleOptInRecipientCommand();
		DoubleOptInRecipientOptions options = new DoubleOptInRecipientOptions(1L);
		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

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
		DoubleOptInRecipientCommand command = context.getBean(DoubleOptInRecipientCommand.class);
		DoubleOptInRecipientOptions options = new DoubleOptInRecipientOptions(1L);

		Long recipientId = 7821525927L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><RecipientId>" + recipientId.toString()
				+ "</RecipientId></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<DoubleOptInRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		DoubleOptInRecipientResponse response = responseContainer.getResposne();

		assertEquals(response.getRecipientId(), recipientId);
	}

}
