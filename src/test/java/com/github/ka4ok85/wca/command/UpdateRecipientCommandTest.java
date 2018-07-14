package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
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
import com.github.ka4ok85.wca.options.UpdateRecipientOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.UpdateRecipientResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class UpdateRecipientCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<UpdateRecipient>",
			"<LIST_ID>1</LIST_ID>", "<OLD_EMAIL>test@github.com</OLD_EMAIL>", "<ALLOW_HTML>TRUE</ALLOW_HTML>",
			"<SEND_AUTOREPLY>FALSE</SEND_AUTOREPLY>", "</UpdateRecipient>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setOldEmail("test@github.com");

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
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setRecipientId(2L);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<OLD_EMAIL>test@github.com</OLD_EMAIL>",
				"<RECIPIENT_ID>2</RECIPIENT_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsEncodedRecipientId() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setEncodedRecipientId("123-456-677");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<OLD_EMAIL>test@github.com</OLD_EMAIL>",
				"<ENCODED_RECIPIENT_ID>123-456-677</ENCODED_RECIPIENT_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsSyncFields() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		Map<String, String> syncFields = new HashMap<String, String>();
		syncFields.put("Email", "test2@github.com");
		options.setSyncFields(syncFields);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<OLD_EMAIL>test@github.com</OLD_EMAIL>",
				"<SYNC_FIELDS><SYNC_FIELD><NAME>Email</NAME><VALUE>test2@github.com</VALUE></SYNC_FIELD></SYNC_FIELDS>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresUniqueKey() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);

		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlHonorsVisitorKey() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setOldEmail("test@github.com");
		options.setVisitorKey("Visitor Key");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</SEND_AUTOREPLY>",
				"</SEND_AUTOREPLY><VISITOR_KEY>Visitor Key</VISITOR_KEY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsColumns() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setOldEmail("test@github.com");
		Map<String, String> columns = new HashMap<String, String>();
		columns.put("Email", "test1@github.com");
		columns.put("customerID", "123");
		options.setColumns(columns);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</SEND_AUTOREPLY>",
				"</SEND_AUTOREPLY><COLUMN><NAME>Email</NAME><VALUE>test1@github.com</VALUE></COLUMN><COLUMN><NAME>customerID</NAME><VALUE>123</VALUE></COLUMN>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsSnoozeDaysToSnooze() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setOldEmail("test@github.com");
		options.setSnoozed(true);
		options.setSnoozeDaysToSnooze(4);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</SEND_AUTOREPLY>",
				"</SEND_AUTOREPLY><SNOOZE_SETTINGS><SNOOZED>TRUE</SNOOZED><DAYS_TO_SNOOZE>4</DAYS_TO_SNOOZE></SNOOZE_SETTINGS>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsSnoozeResumeSendDate() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setOldEmail("test@github.com");
		options.setSnoozed(true);
		LocalDate snoozeResumeSendDate = LocalDate.of(2118, 05, 06);
		options.setSnoozeResumeSendDate(snoozeResumeSendDate);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</SEND_AUTOREPLY>",
				"</SEND_AUTOREPLY><SNOOZE_SETTINGS><SNOOZED>TRUE</SNOOZED><RESUME_SEND_DATE>05/6/2118</RESUME_SEND_DATE></SNOOZE_SETTINGS>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresSnoozeDetails() {
		// get XML from command
		UpdateRecipientCommand command = new UpdateRecipientCommand();
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);
		options.setOldEmail("test@github.com");
		options.setSnoozed(true);

		command.buildXmlRequest(options);
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		UpdateRecipientCommand command = context.getBean(UpdateRecipientCommand.class);
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);

		Long recipientId = 7821525927L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><RecipientId>" + recipientId.toString()
				+ "</RecipientId><VISITOR_ASSOCIATION>something</VISITOR_ASSOCIATION></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<UpdateRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		UpdateRecipientResponse response = responseContainer.getResposne();

		assertEquals(response.getRecipientId(), recipientId);
		assertFalse(response.isVisitorAssociation());
	}

	@Test
	public void testReadResponseHonorsVisitorAssociation()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		UpdateRecipientCommand command = context.getBean(UpdateRecipientCommand.class);
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);

		Long recipientId = 7821525927L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><RecipientId>" + recipientId.toString()
				+ "</RecipientId><VISITOR_ASSOCIATION>TRUE</VISITOR_ASSOCIATION></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<UpdateRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		UpdateRecipientResponse response = responseContainer.getResposne();

		assertEquals(response.getRecipientId(), recipientId);
		assertTrue(response.isVisitorAssociation());
	}

	@Test
	public void testReadResponseHonorsMissingVisitorAssociationNode()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		UpdateRecipientCommand command = context.getBean(UpdateRecipientCommand.class);
		UpdateRecipientOptions options = new UpdateRecipientOptions(1L);

		Long recipientId = 7821525927L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><RecipientId>" + recipientId.toString()
				+ "</RecipientId><ORGANIZATION_ID>6d230b87-14af455a8ca-4097dfa4559ed783ss6bfeed2dffc966</ORGANIZATION_ID></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<UpdateRecipientResponse> responseContainer = command.readResponse(resultNode, options);
		UpdateRecipientResponse response = responseContainer.getResposne();

		assertEquals(response.getRecipientId(), recipientId);
		assertFalse(response.isVisitorAssociation());
	}
}
