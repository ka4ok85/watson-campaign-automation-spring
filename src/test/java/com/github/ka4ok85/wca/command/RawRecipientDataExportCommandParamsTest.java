package com.github.ka4ok85.wca.command;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
import com.github.ka4ok85.wca.options.RawRecipientDataExportOptions;

@RunWith(value = Parameterized.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class RawRecipientDataExportCommandParamsTest {

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
			"<RawRecipientDataExport>", "<EXPORT_FORMAT>CSV</EXPORT_FORMAT>", "<FILE_ENCODING>utf-8</FILE_ENCODING>",
			"<MOVE_TO_FTP/>", "<SENT_MAILINGS/>", "<ALL_EVENT_TYPES/>", "</RawRecipientDataExport>", "</Body>",
			"</Envelope>");

	public RawRecipientDataExportCommandParamsTest(String xmlNodeName, String methodName) {
		this.xmlNodeName = xmlNodeName;
		this.methodName = methodName;
	}

	@Parameterized.Parameters(name = "{index}: isValid({0})={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { { "<INCLUDE_SEEDS></INCLUDE_SEEDS>", "setIncludeSeeds" },
				{ "<INCLUDE_FORWARDS></INCLUDE_FORWARDS>", "setIncludeForwards" },
				{ "<INCLUDE_INBOX_MONITORING></INCLUDE_INBOX_MONITORING>", "setIncludeInboxMonitoring" },
				{ "<CODED_TYPE_FIELDS></CODED_TYPE_FIELDS>", "setCodedTypeFields" },
				{ "<EXCLUDE_DELETED></EXCLUDE_DELETED>", "setExcludeDeleted" },
				{ "<FORWARDS_ONLY></FORWARDS_ONLY>", "setIncludeForwardsOnly" },
				{ "<RETURN_MAILING_NAME></RETURN_MAILING_NAME>", "setReturnMailingName" },
				{ "<RETURN_SUBJECT></RETURN_SUBJECT>", "setReturnMailingSubject" },
				{ "<RETURN_CRM_CAMPAIGN_ID></RETURN_CRM_CAMPAIGN_ID>", "setReturnCRMCampaignId" },
				{ "<RETURN_PROGRAM_ID></RETURN_PROGRAM_ID>", "setReturnProgramId" } });
	}

	@Test
	public void testIsValidOtherParameters() {
		RawRecipientDataExportCommand command = new RawRecipientDataExportCommand();
		RawRecipientDataExportOptions options = new RawRecipientDataExportOptions();
		java.lang.reflect.Method method;
		try {
			method = options.getClass().getMethod(methodName, boolean.class);
			method.invoke(options, true);

			command.buildXmlRequest(options);
			String testString = command.getXML();
			Source test = Input.fromString(testString).build();

			// get control XML
			String controlString = defaultRequest.replace("<ALL_EVENT_TYPES/>", "<ALL_EVENT_TYPES/>" + xmlNodeName);
			Source control = Input.fromString(controlString).build();

			Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
			Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
