package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class SelectRecipientDataCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<SelectRecipientData>", "<LIST_ID>1</LIST_ID>", "<EMAIL>test@github.com</EMAIL>",
			"<RETURN_CONTACT_LISTS>FALSE</RETURN_CONTACT_LISTS>", "</SelectRecipientData>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		SelectRecipientDataCommand command = new SelectRecipientDataCommand();
		SelectRecipientDataOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		SelectRecipientDataCommand command = new SelectRecipientDataCommand();
		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
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
	public void testBuildXmlHonorsRecipientId() {
		// get XML from command
		SelectRecipientDataCommand command = new SelectRecipientDataCommand();
		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		options.setRecipientId(2L);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EMAIL>test@github.com</EMAIL>",
				"<RECIPIENT_ID>2</RECIPIENT_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsEncodedRecipientId() {
		// get XML from command
		SelectRecipientDataCommand command = new SelectRecipientDataCommand();
		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		options.setEncodedRecipientId("123-456-677");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EMAIL>test@github.com</EMAIL>",
				"<ENCODED_RECIPIENT_ID>123-456-677</ENCODED_RECIPIENT_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsVisitorKey() {
		// get XML from command
		SelectRecipientDataCommand command = new SelectRecipientDataCommand();
		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		options.setVisitorKey("Visitor Key");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EMAIL>test@github.com</EMAIL>",
				"<VISITOR_KEY>Visitor Key</VISITOR_KEY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsKeyColumns() {
		// get XML from command
		SelectRecipientDataCommand command = new SelectRecipientDataCommand();
		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		Map<String, String> keyColumns = new HashMap<String, String>();
		keyColumns.put("Email", "test2@github.com");
		options.setKeyColumns(keyColumns);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EMAIL>test@github.com</EMAIL>",
				"<COLUMN><NAME>Email</NAME><VALUE>test2@github.com</VALUE></COLUMN>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresUniqueKey() {
		// get XML from command
		SelectRecipientDataCommand command = new SelectRecipientDataCommand();
		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);

		command.buildXmlRequest(options);
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		SelectRecipientDataCommand command = context.getBean(SelectRecipientDataCommand.class);

		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		options.setEmail("test@github.com");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		Long recipientId = 7821525927L;
		LocalDateTime lastModified = LocalDateTime.of(2018, 8, 25, 1, 2);
		String organizationId = "6d230b87-14af455a8ca-4097dfa4559ed783d26bfeed2dffc922";
		String value1 = "1";
		int createdFrom = 1;
		LocalDateTime resumeSendDate = LocalDateTime.of(2019, 1, 1, 14, 23);
		LocalDateTime optedOut = LocalDateTime.of(2018, 5, 1, 23, 33);
		String value2 = "123";
		int emailType = 1;
		String crmLeadSource = "crm Lead Source";
		String name1 = "CustID";
		String email = "test@github.com";
		LocalDateTime optedIn = LocalDateTime.of(2018, 3, 28, 10, 20);
		String name2 = "Phone";
		Long contactListId = 3L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Email>" + email + "</Email><RecipientId>" + recipientId
				+ "</RecipientId><EmailType>" + emailType + "</EmailType><LastModified>"
				+ lastModified.format(formatter) + "</LastModified><CreatedFrom>" + createdFrom
				+ "</CreatedFrom><OptedIn>" + optedIn.format(formatter) + "</OptedIn><OptedOut>"
				+ optedOut.format(formatter) + "</OptedOut><ResumeSendDate>" + resumeSendDate.format(formatter)
				+ "</ResumeSendDate><ORGANIZATION_ID>" + organizationId + "</ORGANIZATION_ID><CRMLeadSource>"
				+ crmLeadSource + "</CRMLeadSource><COLUMNS><COLUMN><NAME>" + name1 + "</NAME><VALUE>" + value1
				+ "</VALUE></COLUMN><COLUMN><NAME>" + name2 + "</NAME><VALUE>" + value2
				+ "</VALUE></COLUMN></COLUMNS><CONTACT_LISTS><CONTACT_LIST_ID>" + contactListId
				+ "</CONTACT_LIST_ID></CONTACT_LISTS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<SelectRecipientDataResponse> responseContainer = command.readResponse(resultNode, options);
		SelectRecipientDataResponse response = responseContainer.getResposne();

		assertEquals(response.getColumns().size(), 2);
		assertTrue(response.getColumns().containsKey(name1));
		assertTrue(response.getColumns().containsKey(name2));
		assertEquals(response.getColumns().get(name1), value1);
		assertEquals(response.getColumns().get(name2), value2);

		assertEquals(response.getContactLists().size(), 1);
		assertEquals(response.getContactLists().get(0), contactListId);

		assertEquals(response.getCreatedFrom(), createdFrom);
		assertEquals(response.getCrmLeadSource(), crmLeadSource);
		assertEquals(response.getEmail(), email);
		assertEquals(response.getEmailType(), emailType);
		assertEquals(response.getLastModified(), lastModified);
		assertEquals(response.getOptedIn(), optedIn);
		assertEquals(response.getOptedOut(), optedOut);
		assertEquals(response.getOrganiztionId(), organizationId);
		assertEquals(response.getRecipientId(), recipientId);
		assertEquals(response.getResumeSendDate(), resumeSendDate);
	}

	@Test
	public void testReadResponseHonorsDateFormat()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		SelectRecipientDataCommand command = context.getBean(SelectRecipientDataCommand.class);

		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		options.setEmail("test@github.com");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		Long recipientId = 7821525927L;
		LocalDateTime lastModified = LocalDateTime.of(2018, 8, 25, 1, 2);
		String organizationId = "6d230b87-14af455a8ca-4097dfa4559ed783d26bfeed2dffc922";
		String value1 = "1";
		int createdFrom = 1;
		LocalDateTime resumeSendDate = LocalDateTime.of(2019, 1, 1, 12, 23);
		LocalDateTime optedOut = LocalDateTime.of(2018, 5, 1, 23, 33);
		String value2 = "123";
		int emailType = 1;
		String crmLeadSource = "crm Lead Source";
		String name1 = "CustID";
		String email = "test@github.com";
		LocalDateTime optedIn = LocalDateTime.of(2018, 3, 28, 10, 20);
		String name2 = "Phone";
		Long contactListId = 3L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Email>" + email + "</Email><RecipientId>" + recipientId
				+ "</RecipientId><EmailType>" + emailType + "</EmailType><LastModified>"
				+ lastModified.format(formatter) + "</LastModified><CreatedFrom>" + createdFrom
				+ "</CreatedFrom><OptedIn>" + optedIn.format(formatter) + "</OptedIn><OptedOut>"
				+ optedOut.format(formatter) + "</OptedOut><ResumeSendDate>" + resumeSendDate.format(formatter)
				+ "</ResumeSendDate><ORGANIZATION_ID>" + organizationId + "</ORGANIZATION_ID><CRMLeadSource>"
				+ crmLeadSource + "</CRMLeadSource><COLUMNS><COLUMN><NAME>" + name1 + "</NAME><VALUE>" + value1
				+ "</VALUE></COLUMN><COLUMN><NAME>" + name2 + "</NAME><VALUE>" + value2
				+ "</VALUE></COLUMN></COLUMNS><CONTACT_LISTS><CONTACT_LIST_ID>" + contactListId
				+ "</CONTACT_LIST_ID></CONTACT_LISTS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<SelectRecipientDataResponse> responseContainer = command.readResponse(resultNode, options);
		SelectRecipientDataResponse response = responseContainer.getResposne();

		assertEquals(response.getColumns().size(), 2);
		assertTrue(response.getColumns().containsKey(name1));
		assertTrue(response.getColumns().containsKey(name2));
		assertEquals(response.getColumns().get(name1), value1);
		assertEquals(response.getColumns().get(name2), value2);

		assertEquals(response.getContactLists().size(), 1);
		assertEquals(response.getContactLists().get(0), contactListId);

		assertEquals(response.getCreatedFrom(), createdFrom);
		assertEquals(response.getCrmLeadSource(), crmLeadSource);
		assertEquals(response.getEmail(), email);
		assertEquals(response.getEmailType(), emailType);
		assertEquals(response.getLastModified(), lastModified);
		assertEquals(response.getOptedIn(), optedIn);
		assertEquals(response.getOptedOut(), optedOut);
		assertEquals(response.getOrganiztionId(), organizationId);
		assertEquals(response.getRecipientId(), recipientId);
		assertEquals(response.getResumeSendDate(), resumeSendDate);
	}

	@Test
	public void testReadResponseHonorsBlankDateFields()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		SelectRecipientDataCommand command = context.getBean(SelectRecipientDataCommand.class);

		SelectRecipientDataOptions options = new SelectRecipientDataOptions(1L);
		options.setEmail("test@github.com");

		Long recipientId = 7821525927L;
		String organizationId = "6d230b87-14af455a8ca-4097dfa4559ed783d26bfeed2dffc922";
		String value1 = "1";
		int createdFrom = 1;
		String value2 = "123";
		int emailType = 1;
		String crmLeadSource = "crm Lead Source";
		String name1 = "CustID";
		String email = "test@github.com";
		String name2 = "Phone";
		Long contactListId = 3L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Email>" + email + "</Email><RecipientId>" + recipientId
				+ "</RecipientId><EmailType>" + emailType + "</EmailType><LastModified></LastModified><CreatedFrom>"
				+ createdFrom
				+ "</CreatedFrom><OptedIn></OptedIn><OptedOut></OptedOut><ResumeSendDate></ResumeSendDate><ORGANIZATION_ID>"
				+ organizationId + "</ORGANIZATION_ID><CRMLeadSource>" + crmLeadSource
				+ "</CRMLeadSource><COLUMNS><COLUMN><NAME>" + name1 + "</NAME><VALUE>" + value1
				+ "</VALUE></COLUMN><COLUMN><NAME>" + name2 + "</NAME><VALUE>" + value2
				+ "</VALUE></COLUMN></COLUMNS><CONTACT_LISTS><CONTACT_LIST_ID>" + contactListId
				+ "</CONTACT_LIST_ID></CONTACT_LISTS></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<SelectRecipientDataResponse> responseContainer = command.readResponse(resultNode, options);
		SelectRecipientDataResponse response = responseContainer.getResposne();

		assertEquals(response.getColumns().size(), 2);
		assertTrue(response.getColumns().containsKey(name1));
		assertTrue(response.getColumns().containsKey(name2));
		assertEquals(response.getColumns().get(name1), value1);
		assertEquals(response.getColumns().get(name2), value2);

		assertEquals(response.getContactLists().size(), 1);
		assertEquals(response.getContactLists().get(0), contactListId);

		assertEquals(response.getCreatedFrom(), createdFrom);
		assertEquals(response.getCrmLeadSource(), crmLeadSource);
		assertEquals(response.getEmail(), email);
		assertEquals(response.getEmailType(), emailType);
		assertNull(response.getLastModified());
		assertNull(response.getOptedIn());
		assertNull(response.getOptedOut());
		assertEquals(response.getOrganiztionId(), organizationId);
		assertEquals(response.getRecipientId(), recipientId);
		assertNull(response.getResumeSendDate());
	}
}
