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
import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class RemoveRecipientCommandTest {

	@Autowired
	ApplicationContext context;
	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<RemoveRecipient>",
			"<LIST_ID>1</LIST_ID>", "<EMAIL>test@github.com</EMAIL>", "</RemoveRecipient>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		RemoveRecipientCommand command = new RemoveRecipientCommand();
		RemoveRecipientOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		RemoveRecipientCommand command = new RemoveRecipientCommand();
		RemoveRecipientOptions options = new RemoveRecipientOptions(1L);
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

	@Test(expected = RuntimeException.class)
	public void testBuildXmlHonorsRequiresEmail() {
		// get XML from command
		RemoveRecipientCommand command = new RemoveRecipientCommand();
		RemoveRecipientOptions options = new RemoveRecipientOptions(1L);

		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlHonorsColumns() {
		// get XML from command
		RemoveRecipientCommand command = new RemoveRecipientCommand();
		RemoveRecipientOptions options = new RemoveRecipientOptions(1L);
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
		RemoveRecipientCommand command = new RemoveRecipientCommand();
		RemoveRecipientOptions options = new RemoveRecipientOptions(1L);
		options.setEmail("test@github.com");

		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(
						"<Envelope><Body><RESULT><SUCCESS>TRUE</SUCCESS></RESULT></Body></Envelope>".getBytes()))
				.getDocumentElement();

		ResponseContainer<RemoveRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		RemoveRecipientResponse response = responseContainer.getResposne();
		assertEquals(response, null);
	}
}
