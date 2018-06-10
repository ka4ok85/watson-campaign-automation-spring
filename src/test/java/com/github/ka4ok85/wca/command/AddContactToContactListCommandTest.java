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
import com.github.ka4ok85.wca.options.AddContactToContactListOptions;
import com.github.ka4ok85.wca.response.AddContactToContactListResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class AddContactToContactListCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<AddContactToContactList>", "<CONTACT_LIST_ID>1</CONTACT_LIST_ID>", "<COLUMN>",
			"<NAME><![CDATA[email]]></NAME>", "<VALUE><![CDATA[test@github.com]]></VALUE>", "</COLUMN>",

			"</AddContactToContactList>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		AddContactToContactListCommand command = new AddContactToContactListCommand();
		AddContactToContactListOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		AddContactToContactListCommand command = new AddContactToContactListCommand();
		AddContactToContactListOptions options = new AddContactToContactListOptions(1L);
		Map<String, String> columns = new HashMap<String, String>();
		columns.put("email", "test@github.com");
		options.setColumns(columns);
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
	public void testBuildXmlAcceptsContactId() {
		// get XML from command
		AddContactToContactListCommand command = new AddContactToContactListCommand();
		AddContactToContactListOptions options = new AddContactToContactListOptions(1L);
		Long contactId = 321L;
		options.setContactId(contactId);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace(
				"<COLUMN>" + System.getProperty("line.separator") + "<NAME><![CDATA[email]]></NAME>"
						+ System.getProperty("line.separator") + "<VALUE><![CDATA[test@github.com]]></VALUE>"
						+ System.getProperty("line.separator") + "</COLUMN>",
				"<CONTACT_ID>" + contactId + "</CONTACT_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresContactIdOrColumns() {
		AddContactToContactListCommand command = new AddContactToContactListCommand();
		AddContactToContactListOptions options = new AddContactToContactListOptions(1L);

		command.buildXmlRequest(options);
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		AddContactToContactListCommand command = new AddContactToContactListCommand();
		AddContactToContactListOptions options = new AddContactToContactListOptions(1L);
		Long contactId = 321L;
		options.setContactId(contactId);

		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(
						"<Envelope><Body><RESULT><SUCCESS>TRUE</SUCCESS></RESULT></Body></Envelope>".getBytes()))
				.getDocumentElement();

		ResponseContainer<AddContactToContactListResponse> responseContainer = command.readResponse(resultNode,
				options);
		AddContactToContactListResponse response = responseContainer.getResposne();
		assertEquals(response, null);
	}
}