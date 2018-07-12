package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.GetSentMailingsForListOptions;
import com.github.ka4ok85.wca.response.GetSentMailingsForListResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@RunWith(value = Parameterized.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetSentMailingsForListCommandTest {

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	private String xmlNodeName;
	private String methodName;

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<GetSentMailingsForList>", "<LIST_ID>1</LIST_ID>", "<DATE_START>05/01/2017 00:00:00</DATE_START>",
			"<DATE_END>05/01/2018 00:00:00</DATE_END>", "</GetSentMailingsForList>", "</Body>", "</Envelope>");

	public GetSentMailingsForListCommandTest(String xmlNodeName, String methodName) {
		this.xmlNodeName = xmlNodeName;
		this.methodName = methodName;
	}

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		GetSentMailingsForListCommand command = new GetSentMailingsForListCommand();
		GetSentMailingsForListOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		GetSentMailingsForListCommand command = new GetSentMailingsForListCommand();
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);

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
	public void testBuildXmlHonorsIncludeChildren() {
		// get XML from command
		GetSentMailingsForListCommand command = new GetSentMailingsForListCommand();
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);
		options.setIncludeChildren(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</DATE_END>",
				"</DATE_END><INCLUDE_CHILDREN>TRUE</INCLUDE_CHILDREN>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsVisibilityShared() {
		// get XML from command
		GetSentMailingsForListCommand command = new GetSentMailingsForListCommand();
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);
		options.setVisibility(Visibility.SHARED);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</DATE_END>", "</DATE_END><SHARED>TRUE</SHARED>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsVisibilityPrivate() {
		// get XML from command
		GetSentMailingsForListCommand command = new GetSentMailingsForListCommand();
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);
		options.setVisibility(Visibility.PRIVATE);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</DATE_END>", "</DATE_END><PRIVATE>TRUE</PRIVATE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Parameterized.Parameters(name = "{index}: isValid({0})={1}")
	public static Iterable<Object[]> data() {
		return Arrays
				.asList(new Object[][] { { "<MAILING_COUNT_ONLY>TRUE</MAILING_COUNT_ONLY>", "setMailingCountOnly" },
						{ "<AUTOMATED>TRUE</AUTOMATED>", "setAutomated" },
						{ "<CAMPAIGN_ACTIVE>TRUE</CAMPAIGN_ACTIVE>", "setCampaignActive" },
						{ "<CAMPAIGN_CANCELLED>TRUE</CAMPAIGN_CANCELLED>", "setCampaignCancelled" },
						{ "<CAMPAIGN_COMPLETED>TRUE</CAMPAIGN_COMPLETED>", "setCampaignCompleted" },
						{ "<CAMPAIGN_SCRAPE_TEMPLATE>TRUE</CAMPAIGN_SCRAPE_TEMPLATE>", "setCampaignScrapeTemplate" },
						{ "<EXCLUDE_TEST_MAILINGS>TRUE</EXCLUDE_TEST_MAILINGS>", "setExcludeTestMailings" },
						{ "<EXCLUDE_ZERO_SENT>TRUE</EXCLUDE_ZERO_SENT>", "setExcludeZeroSent" },
						{ "<INCLUDE_TAGS>TRUE</INCLUDE_TAGS>", "setIncludeTags" },
						{ "<OPTIN_CONFIRMATION>TRUE</OPTIN_CONFIRMATION>", "setOptinConfirmation" },
						{ "<PROFILE_CONFIRMATION>TRUE</PROFILE_CONFIRMATION>", "setProfileConfirmation" },
						{ "<SCHEDULED>TRUE</SCHEDULED>", "setScheduled" }, { "<SENT>TRUE</SENT>", "setSend" },
						{ "<SENDING>TRUE</SENDING>", "setSending" }

		});
	}

	@Test
	public void testIsValidBooleanParameters() {
		GetSentMailingsForListCommand command = new GetSentMailingsForListCommand();
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);
		java.lang.reflect.Method method;
		try {
			method = options.getClass().getMethod(methodName, boolean.class);
			method.invoke(options, true);

			command.buildXmlRequest(options);
			String testString = command.getXML();
			Source test = Input.fromString(testString).build();

			// get control XML
			String controlString = defaultRequest.replace("</DATE_END>", "</DATE_END>" + xmlNodeName);
			Source control = Input.fromString(controlString).build();

			Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
			Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetSentMailingsForListCommand command = context.getBean(GetSentMailingsForListCommand.class);
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);

		Long mailingId = 1L;
		Long reportId = 2L;
		String scheduledTS = "2018-04-03 20:00:54.0";
		String mailingName = "Test MailingName";
		String listName = "Test ListName";
		Long listId = 3L;
		String userName = "Test UserName";
		String sentTS = "2018-04-03 20:00:57.0";
		Long numSent = 4L;
		String subject = "Test Subject";
		String visibility = "Private";
		Long parentTemplateId = 5L;
		Long parentListId = 6L;
		String tag1 = "tag 1";
		String tag2 = "tag 2";
		String tag3 = "tag 3";

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Mailing><MailingId>" + mailingId + "</MailingId><ReportId>"
				+ reportId + "</ReportId><ScheduledTS>" + scheduledTS + "</ScheduledTS><MailingName>" + mailingName
				+ "</MailingName><ListName>" + listName + "</ListName><ListId>" + listId + "</ListId><UserName>"
				+ userName + "</UserName><SentTS>" + sentTS + "</SentTS><NumSent>" + numSent + "</NumSent><Subject>"
				+ subject + "</Subject><Visibility>" + visibility + "</Visibility><ParentTemplateId>" + parentTemplateId
				+ "</ParentTemplateId><ParentListId>" + parentListId + "</ParentListId><Tags><Tag>" + tag1
				+ "</Tag><Tag>" + tag2 + "</Tag><Tag>" + tag3 + "</Tag></Tags></Mailing></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetSentMailingsForListResponse> responseContainer = command.readResponse(resultNode, options);
		GetSentMailingsForListResponse response = responseContainer.getResposne();

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
		assertEquals(response.getSentMailings().size(), 1);
		assertEquals(response.getSentMailings().get(0).getListId(), listId);
		assertEquals(response.getSentMailings().get(0).getListName(), listName);
		assertEquals(response.getSentMailings().get(0).getMailingId(), mailingId);
		assertEquals(response.getSentMailings().get(0).getMailingName(), mailingName);
		assertEquals(response.getSentMailings().get(0).getNumSent(), numSent);
		assertEquals(response.getSentMailings().get(0).getParentListId(), parentListId);
		assertEquals(response.getSentMailings().get(0).getParentTemplateId(), parentTemplateId);
		assertEquals(response.getSentMailings().get(0).getReportId(), reportId);
		assertEquals(response.getSentMailings().get(0).getScheduledDateTime(),
				LocalDateTime.parse(scheduledTS, formatter));
		assertEquals(response.getSentMailings().get(0).getSentDateTime(), LocalDateTime.parse(sentTS, formatter));
		assertEquals(response.getSentMailings().get(0).getSubject(), subject);
		assertEquals(response.getSentMailings().get(0).getUserName(), userName);
		assertEquals(response.getSentMailings().get(0).getVisibility(), Visibility.getVisibilityByAlias(visibility));
		assertEquals(response.getSentMailings().get(0).getTags().size(), 3);
		assertEquals(response.getSentMailings().get(0).getTags().get(0), tag1);
		assertEquals(response.getSentMailings().get(0).getTags().get(1), tag2);
		assertEquals(response.getSentMailings().get(0).getTags().get(2), tag3);
	}

	@Test
	public void testReadResponseHonorsBlankNodes()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetSentMailingsForListCommand command = context.getBean(GetSentMailingsForListCommand.class);
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);

		Long mailingId = 1L;
		Long reportId = 2L;
		String scheduledTS = "2018-04-03 20:00:54.0";
		String mailingName = "Test MailingName";
		String listName = "Test ListName";
		Long listId = 3L;
		String userName = "Test UserName";
		String sentTS = "2018-04-03 20:00:57.0";
		Long numSent = 4L;
		String subject = "Test Subject";
		String visibility = "Private";
		Long parentTemplateId = 5L;
		String tag1 = "tag 1";
		String tag2 = "tag 2";
		String tag3 = "tag 3";

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Mailing><MailingId>" + mailingId + "</MailingId><ReportId>"
				+ reportId + "</ReportId><ScheduledTS>" + scheduledTS + "</ScheduledTS><MailingName>" + mailingName
				+ "</MailingName><ListName>" + listName + "</ListName><ListId>" + listId + "</ListId><UserName>"
				+ userName + "</UserName><SentTS>" + sentTS + "</SentTS><NumSent>" + numSent + "</NumSent><Subject>"
				+ subject + "</Subject><Visibility>" + visibility + "</Visibility><ParentTemplateId>" + parentTemplateId
				+ "</ParentTemplateId><Tags><Tag>" + tag1
				+ "</Tag><Tag>" + tag2 + "</Tag><Tag>" + tag3 + "</Tag></Tags></Mailing></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetSentMailingsForListResponse> responseContainer = command.readResponse(resultNode, options);
		GetSentMailingsForListResponse response = responseContainer.getResposne();

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
		assertEquals(response.getSentMailings().size(), 1);
		assertEquals(response.getSentMailings().get(0).getListId(), listId);
		assertEquals(response.getSentMailings().get(0).getListName(), listName);
		assertEquals(response.getSentMailings().get(0).getMailingId(), mailingId);
		assertEquals(response.getSentMailings().get(0).getMailingName(), mailingName);
		assertEquals(response.getSentMailings().get(0).getNumSent(), numSent);
		assertEquals(response.getSentMailings().get(0).getParentTemplateId(), parentTemplateId);
		assertEquals(response.getSentMailings().get(0).getReportId(), reportId);
		assertEquals(response.getSentMailings().get(0).getScheduledDateTime(),
				LocalDateTime.parse(scheduledTS, formatter));
		assertEquals(response.getSentMailings().get(0).getSentDateTime(), LocalDateTime.parse(sentTS, formatter));
		assertEquals(response.getSentMailings().get(0).getSubject(), subject);
		assertEquals(response.getSentMailings().get(0).getUserName(), userName);
		assertEquals(response.getSentMailings().get(0).getVisibility(), Visibility.getVisibilityByAlias(visibility));
		assertEquals(response.getSentMailings().get(0).getTags().size(), 3);
		assertEquals(response.getSentMailings().get(0).getTags().get(0), tag1);
		assertEquals(response.getSentMailings().get(0).getTags().get(1), tag2);
		assertEquals(response.getSentMailings().get(0).getTags().get(2), tag3);
	}
	
	@Test
	public void testReadResponseHonorsMailingCountOnly()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetSentMailingsForListCommand command = context.getBean(GetSentMailingsForListCommand.class);
		LocalDateTime startDate = LocalDateTime.of(2017, 05, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 05, 01, 0, 0, 0);
		DateTimeRange dateTimeRange = new DateTimeRange(startDate, endDate);
		GetSentMailingsForListOptions options = new GetSentMailingsForListOptions(1L, dateTimeRange);
		options.setMailingCountOnly(true);

		Long sentMailingsCount = 11L;

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><SentMailingsCount>" + sentMailingsCount
				+ "</SentMailingsCount></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetSentMailingsForListResponse> responseContainer = command.readResponse(resultNode, options);
		GetSentMailingsForListResponse response = responseContainer.getResposne();

		assertEquals(response.getSentMailingsCount(), sentMailingsCount);
	}
}
