package com.github.ka4ok85.wca.command;

import java.time.LocalDateTime;

import javax.xml.transform.Source;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.options.GetSentMailingsForListOptions;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetSentMailingsForListCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<GetSentMailingsForList>", "<LIST_ID>1</LIST_ID>", "<DATE_START>05/01/2017 00:00:00</DATE_START>",
			"<DATE_END>05/01/2018 00:00:00</DATE_END>", "</GetSentMailingsForList>", "</Body>", "</Envelope>");

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

}
