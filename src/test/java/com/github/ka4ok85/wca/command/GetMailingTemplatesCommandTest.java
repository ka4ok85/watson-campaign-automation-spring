package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.GetMailingTemplatesOptions;
import com.github.ka4ok85.wca.response.GetMailingTemplatesResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetMailingTemplatesCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<GetMailingTemplates>", "<VISIBILITY>1</VISIBILITY>", "</GetMailingTemplates>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();

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
	public void testBuildXmlHonorsVisibility() {
		// get XML from command
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();
		options.setVisibility(Visibility.PRIVATE);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<VISIBILITY>1</VISIBILITY>", "<VISIBILITY>0</VISIBILITY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsStartDate() {
		// get XML from command
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();
		LocalDateTime lastModifiedStartDate = LocalDateTime.of(2018, 1, 2, 13, 44, 23);
		options.setLastModifiedStartDate(lastModifiedStartDate);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String controlString = defaultRequest.replace("</VISIBILITY>", "</VISIBILITY><LAST_MODIFIED_START_DATE>"
				+ lastModifiedStartDate.format(formatter) + "</LAST_MODIFIED_START_DATE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsLastDate() {
		// get XML from command
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();
		LocalDateTime lastModifiedEndDate = LocalDateTime.of(2019, 2, 3, 14, 45, 24);
		options.setLastModifiedEndDate(lastModifiedEndDate);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String controlString = defaultRequest.replace("</VISIBILITY>", "</VISIBILITY><LAST_MODIFIED_END_DATE>"
				+ lastModifiedEndDate.format(formatter) + "</LAST_MODIFIED_END_DATE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsDateRange() {
		// get XML from command
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();
		LocalDateTime lastModifiedStartDate = LocalDateTime.of(2018, 2, 3, 14, 45, 24);
		LocalDateTime lastModifiedEndDate = LocalDateTime.of(2019, 2, 3, 14, 45, 24);
		options.setLastModifiedStartDate(lastModifiedStartDate);
		options.setLastModifiedEndDate(lastModifiedEndDate);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String controlString = defaultRequest.replace("</VISIBILITY>",
				"</VISIBILITY><LAST_MODIFIED_START_DATE>" + lastModifiedStartDate.format(formatter)
						+ "</LAST_MODIFIED_START_DATE><LAST_MODIFIED_END_DATE>" + lastModifiedEndDate.format(formatter)
						+ "</LAST_MODIFIED_END_DATE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsCrmEnabled() {
		// get XML from command
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();
		options.setCrmEnabled(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</VISIBILITY>",
				"</VISIBILITY><IS_CRM_ENABLED>TRUE</IS_CRM_ENABLED>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresValidDateRange() {
		// get XML from command
		GetMailingTemplatesCommand command = new GetMailingTemplatesCommand();
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();
		LocalDateTime lastModifiedStartDate = LocalDateTime.of(2019, 2, 3, 14, 45, 24);
		LocalDateTime lastModifiedEndDate = LocalDateTime.of(2018, 2, 3, 14, 45, 24);
		options.setLastModifiedStartDate(lastModifiedStartDate);
		options.setLastModifiedEndDate(lastModifiedEndDate);

		command.buildXmlRequest(options);
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetMailingTemplatesCommand command = context.getBean(GetMailingTemplatesCommand.class);
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		String userId = "6d230b87-14af455a8d5-4097dfa4559ed783d26bfeed2dffc966";
		String mailingName = "Pref Center Mailing";
		LocalDateTime lastModified = LocalDateTime.of(2017, 2, 3, 15, 34);
		String subject = "Pref center";
		Visibility visibility = Visibility.SHARED;
		Long mailingId = 343652L;
		Boolean flaggedForBackup = true;
		Boolean allowCrmBlock = true;

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><MAILING_TEMPLATE><MAILING_ID>" + mailingId
				+ "</MAILING_ID><MAILING_NAME>" + mailingName + "</MAILING_NAME><SUBJECT>" + subject
				+ "</SUBJECT><LAST_MODIFIED>" + lastModified.format(formatter) + "</LAST_MODIFIED><VISIBILITY>"
				+ visibility + "</VISIBILITY><USER_ID>" + userId + "</USER_ID><FLAGGED_FOR_BACKUP>" + flaggedForBackup
				+ "</FLAGGED_FOR_BACKUP><ALLOW_CRM_BLOCK>" + allowCrmBlock
				+ "</ALLOW_CRM_BLOCK></MAILING_TEMPLATE></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetMailingTemplatesResponse> responseContainer = command.readResponse(resultNode, options);
		GetMailingTemplatesResponse response = responseContainer.getResposne();

		assertEquals(response.getMailingTempaltes().size(), 1);

		assertEquals(response.getMailingTempaltes().get(0).getLastModified(), lastModified);
		assertEquals(response.getMailingTempaltes().get(0).getMailingId(), mailingId);
		assertEquals(response.getMailingTempaltes().get(0).getMailingName(), mailingName);
		assertEquals(response.getMailingTempaltes().get(0).getSubject(), subject);
		assertEquals(response.getMailingTempaltes().get(0).getUserId(), userId);
		assertEquals(response.getMailingTempaltes().get(0).getVisibility(), visibility);
		assertEquals(response.getMailingTempaltes().get(0).isAllowCrmBlock(), allowCrmBlock);
		assertEquals(response.getMailingTempaltes().get(0).isFlaggedForBackup(), flaggedForBackup);
	}

	@Test
	public void testReadResponseHonorsDateFormat()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetMailingTemplatesCommand command = context.getBean(GetMailingTemplatesCommand.class);
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		String userId = "6d230b87-14af455a8d5-4097dfa4559ed783d26bfeed2dffc966";
		String mailingName = "Pref Center Mailing";
		LocalDateTime lastModified = LocalDateTime.of(2017, 2, 3, 12, 34);
		String subject = "Pref center";
		Visibility visibility = Visibility.SHARED;
		Long mailingId = 343652L;
		Boolean flaggedForBackup = true;
		Boolean allowCrmBlock = true;

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><MAILING_TEMPLATE><MAILING_ID>" + mailingId
				+ "</MAILING_ID><MAILING_NAME>" + mailingName + "</MAILING_NAME><SUBJECT>" + subject
				+ "</SUBJECT><LAST_MODIFIED>" + lastModified.format(formatter) + "</LAST_MODIFIED><VISIBILITY>"
				+ visibility + "</VISIBILITY><USER_ID>" + userId + "</USER_ID><FLAGGED_FOR_BACKUP>" + flaggedForBackup
				+ "</FLAGGED_FOR_BACKUP><ALLOW_CRM_BLOCK>" + allowCrmBlock
				+ "</ALLOW_CRM_BLOCK></MAILING_TEMPLATE></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetMailingTemplatesResponse> responseContainer = command.readResponse(resultNode, options);
		GetMailingTemplatesResponse response = responseContainer.getResposne();

		assertEquals(response.getMailingTempaltes().size(), 1);

		assertEquals(response.getMailingTempaltes().get(0).getLastModified(), lastModified);
		assertEquals(response.getMailingTempaltes().get(0).getMailingId(), mailingId);
		assertEquals(response.getMailingTempaltes().get(0).getMailingName(), mailingName);
		assertEquals(response.getMailingTempaltes().get(0).getSubject(), subject);
		assertEquals(response.getMailingTempaltes().get(0).getUserId(), userId);
		assertEquals(response.getMailingTempaltes().get(0).getVisibility(), visibility);
		assertEquals(response.getMailingTempaltes().get(0).isAllowCrmBlock(), allowCrmBlock);
		assertEquals(response.getMailingTempaltes().get(0).isFlaggedForBackup(), flaggedForBackup);
	}

	@Test
	public void testReadResponseHonorsBlankNodes()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetMailingTemplatesCommand command = context.getBean(GetMailingTemplatesCommand.class);
		GetMailingTemplatesOptions options = new GetMailingTemplatesOptions();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		String userId = "6d230b87-14af455a8d5-4097dfa4559ed783d26bfeed2dffc966";
		String mailingName = "Pref Center Mailing";
		LocalDateTime lastModified = LocalDateTime.of(2017, 2, 3, 15, 34);
		String subject = "Pref center";
		Visibility visibility = Visibility.SHARED;
		Long mailingId = 343652L;
		Boolean flaggedForBackup = true;

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><MAILING_TEMPLATE><MAILING_ID>" + mailingId
				+ "</MAILING_ID><MAILING_NAME>" + mailingName + "</MAILING_NAME><SUBJECT>" + subject
				+ "</SUBJECT><LAST_MODIFIED>" + lastModified.format(formatter) + "</LAST_MODIFIED><VISIBILITY>"
				+ visibility + "</VISIBILITY><USER_ID>" + userId + "</USER_ID><FLAGGED_FOR_BACKUP>" + flaggedForBackup
				+ "</FLAGGED_FOR_BACKUP></MAILING_TEMPLATE></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetMailingTemplatesResponse> responseContainer = command.readResponse(resultNode, options);
		GetMailingTemplatesResponse response = responseContainer.getResposne();

		assertEquals(response.getMailingTempaltes().size(), 1);

		assertEquals(response.getMailingTempaltes().get(0).getLastModified(), lastModified);
		assertEquals(response.getMailingTempaltes().get(0).getMailingId(), mailingId);
		assertEquals(response.getMailingTempaltes().get(0).getMailingName(), mailingName);
		assertEquals(response.getMailingTempaltes().get(0).getSubject(), subject);
		assertEquals(response.getMailingTempaltes().get(0).getUserId(), userId);
		assertEquals(response.getMailingTempaltes().get(0).getVisibility(), visibility);
		assertEquals(response.getMailingTempaltes().get(0).isFlaggedForBackup(), flaggedForBackup);
	}
}
