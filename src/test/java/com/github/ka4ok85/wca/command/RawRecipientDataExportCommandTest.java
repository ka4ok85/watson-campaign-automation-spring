package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.github.ka4ok85.wca.constants.ExportFormat;
import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.constants.ListExportType;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.options.RawRecipientDataExportOptions;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })

public class RawRecipientDataExportCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<RawRecipientDataExport>",
			"<EXPORT_FORMAT>CSV</EXPORT_FORMAT>", "<FILE_ENCODING>utf-8</FILE_ENCODING>", 
			"<MOVE_TO_FTP/>",
			"<SENT_MAILINGS/>", "<ALL_EVENT_TYPES/>", "</RawRecipientDataExport>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		RawRecipientDataExportCommand command = new RawRecipientDataExportCommand();
		RawRecipientDataExportOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		RawRecipientDataExportCommand command = new RawRecipientDataExportCommand();
		RawRecipientDataExportOptions options = new RawRecipientDataExportOptions();
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
