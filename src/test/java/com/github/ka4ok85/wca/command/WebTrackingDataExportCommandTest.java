package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;

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
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.RawRecipientDataExportOptions;
import com.github.ka4ok85.wca.options.WebTrackingDataExportOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.RawRecipientDataExportResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.sftp.SFTP;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@RunWith(value = Parameterized.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class WebTrackingDataExportCommandTest {

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
			"<WebTrackingDataExport>", "<EXPORT_FORMAT>0</EXPORT_FORMAT>", "<FILE_ENCODING>utf-8</FILE_ENCODING>",
			"<MOVE_TO_FTP/>", "<ALL_EVENT_TYPES/>", "</WebTrackingDataExport>", "</Body>",
			"</Envelope>");
	
	public WebTrackingDataExportCommandTest(String xmlNodeName, String methodName) {
		this.xmlNodeName = xmlNodeName;
		this.methodName = methodName;
	}

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest;
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	
	@Parameterized.Parameters(name = "{index}: isValid({0})={1}")
	public static Iterable<Object[]> data() {
		return Arrays
				.asList(new Object[][] { { "<SENDING></SENDING>", "setIncludeSendingMailings" },
						{ "<OPTIN_CONFIRMATION></OPTIN_CONFIRMATION>", "setIncludeOptinConfirmationMailings" },
						{ "<PROFILE_CONFIRMATION></PROFILE_CONFIRMATION>", "setIncludeProfileConfirmationMailings" },
						{ "<AUTOMATED></AUTOMATED>", "setIncludeAutomatedMailings" },
						{ "<CAMPAIGN_ACTIVE></CAMPAIGN_ACTIVE>", "setIncludeCampaignActiveMailings" },
						{ "<CAMPAIGN_COMPLETED></CAMPAIGN_COMPLETED>", "setIncludeCampaignCompletedMailings" },
						{ "<CAMPAIGN_CANCELLED></CAMPAIGN_CANCELLED>", "setIncludeCampaignCancelledMailings" },
						{ "<CAMPAIGN_SCRAPE_TEMPLATE></CAMPAIGN_SCRAPE_TEMPLATE>",
								"setIncludeCampaignScrapeTemplateMailings" },
						{ "<INCLUDE_TEST_MAILINGS></INCLUDE_TEST_MAILINGS>", "setIncludeTestMailings" } });
	}
	
	
	@Test
	public void testBuildXmlHonorsEventStartDate() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		LocalDateTime eventStartDate = LocalDateTime.of(2017, 2, 3, 4, 5);
		options.setEventStartDate(eventStartDate);
		
		List<Long> domains = new ArrayList<Long>();
		options.setDomains(domains);
		
		List<Long> sites = new ArrayList<Long>();
		options.setSites(sites);
		
		List<String> columns = new ArrayList<String>();
		options.setColumns(columns);
		
		//options.setDatabaseId(-5L);
		//options.setExportFileName("");
		
		//LocalDateTime endDateTime = LocalDateTime.of(2018, 6, 7, 8, 9);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String eventRangeString = "<EVENT_DATE_START>" + eventStartDate.format(formatter)
				+ "</EVENT_DATE_START>";
		String controlString = defaultRequest.replace("<WebTrackingDataExport>",
				"<WebTrackingDataExport>" + eventRangeString);
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testBuildXmlHonorsEventEndDate() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		LocalDateTime eventEndDate = LocalDateTime.of(2017, 2, 3, 4, 5);
		options.setEventEndDate(eventEndDate);
		
		
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String eventRangeString = "<EVENT_DATE_END>" + eventEndDate.format(formatter)
				+ "</EVENT_DATE_END>";
		String controlString = defaultRequest.replace("<WebTrackingDataExport>",
				"<WebTrackingDataExport>" + eventRangeString);
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testBuildXmlHonorsDateRange() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		LocalDateTime eventStartDate = LocalDateTime.of(2017, 2, 3, 4, 5);
		options.setEventStartDate(eventStartDate);
		
		LocalDateTime eventEndDate = LocalDateTime.of(2018, 2, 3, 4, 5);
		options.setEventEndDate(eventEndDate);
		
		
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String eventRangeString = "<EVENT_DATE_START>" + eventStartDate.format(formatter)
		+ "</EVENT_DATE_START><EVENT_DATE_END>" + eventEndDate.format(formatter)
				+ "</EVENT_DATE_END>";
		String controlString = defaultRequest.replace("<WebTrackingDataExport>",
				"<WebTrackingDataExport>" + eventRangeString);
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresCorrectEventRange() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		LocalDateTime eventStartDate = LocalDateTime.of(2018, 2, 3, 4, 5);
		options.setEventStartDate(eventStartDate);
		
		LocalDateTime eventEndDate = LocalDateTime.of(2017, 2, 3, 4, 5);
		options.setEventEndDate(eventEndDate);
		
		command.buildXmlRequest(options);
	}
	
	@Test
	public void testBuildXmlHonorsDomains() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		List<Long> domains = new ArrayList<Long>();
		domains.add(123L);
		options.setDomains(domains);
		
		
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String domainsString = "<DOMAINS>";
		for (Long domain : domains) {
			domainsString = domainsString + "<DOMAIN_ID>" + domain + "</DOMAIN_ID>";
		}
		
		domainsString = domainsString + "</DOMAINS>";
		
		String controlString = defaultRequest.replace("<WebTrackingDataExport>",
				"<WebTrackingDataExport>" + domainsString);
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testBuildXmlHonorsSites() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		List<Long> sites = new ArrayList<Long>();
		sites.add(345L);
		options.setSites(sites);
		
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String siteString = "<SITES>";
		for (Long site : sites) {
			siteString = siteString + "<SITE_ID>" + site + "</SITE_ID>";
		}
		
		siteString = siteString + "</SITES>";
		
		String controlString = defaultRequest.replace("<WebTrackingDataExport>",
				"<WebTrackingDataExport>" + siteString);
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	
	@Test
	public void testBuildXmlHonorsExportColumns() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		List<String> columns = new ArrayList<String>();
		columns.add("Column A");
		columns.add("Column B");
		options.setColumns(columns);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String columnsString = "<COLUMNS>";
		for (String column : columns) {
			columnsString = columnsString + "<COLUMN><NAME>" + column + "</NAME></COLUMN>";
		}

		columnsString = columnsString + "</COLUMNS>";
		String controlString = defaultRequest.replace("<ALL_EVENT_TYPES/>", "<ALL_EVENT_TYPES/>" + columnsString);
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testBuildXmlHonorsMoveToFTP() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		options.setMoveToFTP(false);
		
		
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<MOVE_TO_FTP/>",				"" );
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testBuildXmlHonorsDatabaseId() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		Long databaseId = 8L;
		options.setDatabaseId(databaseId);
		
		
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<WebTrackingDataExport>", "<WebTrackingDataExport><DATABASE_ID>" + databaseId + "</DATABASE_ID>" );
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
	@Test
	public void testBuildXmlHonorsExportFileName() {
		// get XML from command
		WebTrackingDataExportCommand command = new WebTrackingDataExportCommand();
		WebTrackingDataExportOptions options = new WebTrackingDataExportOptions();
		
		String exportFileName = "test file.zip";
		options.setExportFileName(exportFileName);
		
		
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</EXPORT_FORMAT>", "</EXPORT_FORMAT><EXPORT_FILE_NAME>" + exportFileName + "</EXPORT_FILE_NAME>" );
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
	
}
