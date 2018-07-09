package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.github.ka4ok85.wca.constants.CreatedFrom;
import com.github.ka4ok85.wca.options.AddRecipientOptions;
import com.github.ka4ok85.wca.response.AddRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class AddRecipientCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<AddRecipient>",
			"<LIST_ID>1</LIST_ID>", "<CREATED_FROM>1</CREATED_FROM>", "<UPDATE_IF_FOUND>TRUE</UPDATE_IF_FOUND>",
			"<ALLOW_HTML>TRUE</ALLOW_HTML>", "<SEND_AUTOREPLY>FALSE</SEND_AUTOREPLY>", "<SYNC_FIELDS>", "<SYNC_FIELD>",
			"<NAME>", "<![CDATA[Email]]>", "</NAME>", "<VALUE>", "<![CDATA[test2@github.com]]>", "</VALUE>",
			"</SYNC_FIELD>", "</SYNC_FIELDS>", "<COLUMN>", "<NAME>", "<![CDATA[Email]]>", "</NAME>", "<VALUE>",
			"<![CDATA[test1@github.com]]>", "</VALUE>", "</COLUMN>", "<COLUMN>", "<NAME>", "<![CDATA[customerID]]>",
			"</NAME>", "<VALUE>", "<![CDATA[123]]>", "</VALUE>", "</COLUMN>", "<CONTACT_LISTS>",
			"<CONTACT_LIST_ID>23</CONTACT_LIST_ID>", "</CONTACT_LISTS>", "</AddRecipient>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

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
	public void testBuildXmlHonorsEmptySyncFields() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlStringPart = String.join(System.getProperty("line.separator"),
				"<SEND_AUTOREPLY>FALSE</SEND_AUTOREPLY>", "<SYNC_FIELDS>", "<SYNC_FIELD>", "<NAME>",
				"<![CDATA[Email]]>", "</NAME>", "<VALUE>", "<![CDATA[test2@github.com]]>", "</VALUE>", "</SYNC_FIELD>",
				"</SYNC_FIELDS>");

		String controlString = defaultRequest.replace(controlStringPart, "<SEND_AUTOREPLY>FALSE</SEND_AUTOREPLY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsEmptyColumns() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlStringPart = String.join(System.getProperty("line.separator"), "</SYNC_FIELDS>", "<COLUMN>",
				"<NAME>", "<![CDATA[Email]]>", "</NAME>", "<VALUE>", "<![CDATA[test1@github.com]]>", "</VALUE>",
				"</COLUMN>", "<COLUMN>", "<NAME>", "<![CDATA[customerID]]>", "</NAME>", "<VALUE>", "<![CDATA[123]]>",
				"</VALUE>", "</COLUMN>");

		String controlString = defaultRequest.replace(controlStringPart, "</SYNC_FIELDS>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsEmptyContactLists() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlStringPart = String.join(System.getProperty("line.separator"), "</COLUMN>", "<CONTACT_LISTS>",
				"<CONTACT_LIST_ID>23</CONTACT_LIST_ID>", "</CONTACT_LISTS>");

		String controlString = defaultRequest.replace(controlStringPart, "</COLUMN>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsAllowHtml() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		options.setAllowHtml(false);

		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<ALLOW_HTML>TRUE</ALLOW_HTML>",
				"<ALLOW_HTML>FALSE</ALLOW_HTML>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsCreatedFrom() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		CreatedFrom createdFrom = CreatedFrom.OPTED_IN;
		options.setCreatedFrom(createdFrom);

		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<CREATED_FROM>1</CREATED_FROM>",
				"<CREATED_FROM>" + createdFrom.value().toString() + "</CREATED_FROM>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsSendAutoReply() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		options.setSendAutoReply(true);

		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<SEND_AUTOREPLY>FALSE</SEND_AUTOREPLY>",
				"<SEND_AUTOREPLY>TRUE</SEND_AUTOREPLY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsUpdateIfFound() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		options.setUpdateIfFound(false);

		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<UPDATE_IF_FOUND>TRUE</UPDATE_IF_FOUND>",
				"<UPDATE_IF_FOUND>FALSE</UPDATE_IF_FOUND>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsVisitorKey() {
		// get XML from command
		AddRecipientCommand command = new AddRecipientCommand();
		AddRecipientOptions options = new AddRecipientOptions(1L);
		String visitorKey = "123";
		options.setVisitorKey(visitorKey);

		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test1@github.com");
		keyColumns.put("customerID", "123");
		options.setColumns(keyColumns);

		List<Long> contactLists = new ArrayList<Long>();
		contactLists.add(23L);
		options.setContactLists(contactLists);

		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</SEND_AUTOREPLY>",
				"</SEND_AUTOREPLY><VISITOR_KEY>" + visitorKey + "</VISITOR_KEY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		AddRecipientCommand command = context.getBean(AddRecipientCommand.class);
		AddRecipientOptions options = new AddRecipientOptions(1L);

		Long recipientId = 7821525927L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><RecipientId>" + recipientId.toString()
				+ "</RecipientId><VISITOR_ASSOCIATION>something</VISITOR_ASSOCIATION><ORGANIZATION_ID>6d230b87-14af455a8ca-4097dfa4559ed783ss6bfeed2dffc966</ORGANIZATION_ID></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<AddRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		AddRecipientResponse response = responseContainer.getResposne();

		assertEquals(response.getRecipientId(), recipientId);
		assertFalse(response.isVisitorAssociation());
	}

	@Test
	public void testReadResponseHonorsVisitorAssociation()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		AddRecipientCommand command = context.getBean(AddRecipientCommand.class);
		AddRecipientOptions options = new AddRecipientOptions(1L);

		Long recipientId = 7821525927L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><RecipientId>" + recipientId.toString()
				+ "</RecipientId><VISITOR_ASSOCIATION>TRUE</VISITOR_ASSOCIATION><ORGANIZATION_ID>6d230b87-14af455a8ca-4097dfa4559ed783ss6bfeed2dffc966</ORGANIZATION_ID></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<AddRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		AddRecipientResponse response = responseContainer.getResposne();

		assertEquals(response.getRecipientId(), recipientId);
		assertTrue(response.isVisitorAssociation());
	}

	@Test
	public void testReadResponseHonorsMissingVisitorAssociationNode()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		AddRecipientCommand command = context.getBean(AddRecipientCommand.class);
		AddRecipientOptions options = new AddRecipientOptions(1L);

		Long recipientId = 7821525927L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><RecipientId>" + recipientId.toString()
				+ "</RecipientId><ORGANIZATION_ID>6d230b87-14af455a8ca-4097dfa4559ed783ss6bfeed2dffc966</ORGANIZATION_ID></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<AddRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		AddRecipientResponse response = responseContainer.getResposne();

		assertEquals(response.getRecipientId(), recipientId);
		assertFalse(response.isVisitorAssociation());
	}
}
