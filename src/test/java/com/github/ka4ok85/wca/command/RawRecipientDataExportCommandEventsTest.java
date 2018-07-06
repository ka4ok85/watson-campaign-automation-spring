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
public class RawRecipientDataExportCommandEventsTest {

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

	public RawRecipientDataExportCommandEventsTest(String xmlNodeName, String methodName) {
		this.xmlNodeName = xmlNodeName;
		this.methodName = methodName;
	}

	@Parameterized.Parameters(name = "{index}: isValid({0})={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { { "<SENT></SENT>", "setEventSent" },
				{ "<SUPPRESSED></SUPPRESSED>", "setEventSuppressed" }, { "<OPENS></OPENS>", "setEventOpens" },
				{ "<CLICKS></CLICKS>", "setEventClicks" }, { "<OPTINS></OPTINS>", "setEventOptins" },
				{ "<OPTOUTS></OPTOUTS>", "setEventOptouts" }, { "<FORWARDS></FORWARDS>", "setEventForwards" },
				{ "<ATTACHMENTS></ATTACHMENTS>", "setEventAttachments" },
				{ "<CONVERSIONS></CONVERSIONS>", "setEventConversions" },
				{ "<CLICKSTREAMS></CLICKSTREAMS>", "setEventClickstreams" },
				{ "<HARD_BOUNCES></HARD_BOUNCES>", "setEventHardBounces" },
				{ "<SOFT_BOUNCES></SOFT_BOUNCES>", "setEventSoftBounces" },
				{ "<REPLY_ABUSE></REPLY_ABUSE>", "setEventReplyAbuse" },
				{ "<REPLY_COA></REPLY_COA>", "setEventReplyCOA" },
				{ "<REPLY_OTHER></REPLY_OTHER>", "setEventReplyOther" },
				{ "<MAIL_BLOCKS></MAIL_BLOCKS>", "setEventMailBlocks" },
				{ "<MAILING_RESTRICTIONS></MAILING_RESTRICTIONS>", "setEventMailRestrictions" },
				{ "<SMS_ERROR></SMS_ERROR>", "setEventSMSError" }, { "<SMS_REJECT></SMS_REJECT>", "setEventSMSReject" },
				{ "<SMS_OPTOUT></SMS_OPTOUT>", "setEventSMSOptout" } });
	}

	@Test
	public void testIsValidEventsParameters() {
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
			String controlString = defaultRequest.replace("<ALL_EVENT_TYPES/>", xmlNodeName);
			Source control = Input.fromString(controlString).build();

			Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
			Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());

		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

}
