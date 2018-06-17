package com.github.ka4ok85.wca.command;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.xml.transform.Source;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.GetSentMailingsForListOptions;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@RunWith(value = Parameterized.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetSentMailingsForListCommandTest {

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
	public void testIsValidEmailId() {
		System.out.println(this.xmlNodeName);
		System.out.println(methodName);

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

}
