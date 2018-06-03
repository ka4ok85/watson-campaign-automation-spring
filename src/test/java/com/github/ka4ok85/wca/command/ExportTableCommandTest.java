package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
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
import com.github.ka4ok85.wca.options.ExportTableOptions;
import com.github.ka4ok85.wca.response.ExportTableResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class ExportTableCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<ExportTable>",
			"<TABLE_ID>1</TABLE_ID>", "<EXPORT_FORMAT>CSV</EXPORT_FORMAT>", "<FILE_ENCODING>utf-8</FILE_ENCODING>",
			"<ADD_TO_STORED_FILES>FALSE</ADD_TO_STORED_FILES>", "</ExportTable>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		ExportTableCommand command = new ExportTableCommand();
		ExportTableOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		ExportTableCommand command = new ExportTableCommand();
		ExportTableOptions options = new ExportTableOptions(1L);
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
	public void testBuildXmlHonorsExportFormat() {
		// get XML from command
		ExportTableCommand command = new ExportTableCommand();
		ExportTableOptions options = new ExportTableOptions(1L);
		options.setExportFormat(ExportFormat.PIPE);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EXPORT_FORMAT>CSV</EXPORT_FORMAT>",
				"<EXPORT_FORMAT>" + ExportFormat.PIPE.value() + "</EXPORT_FORMAT>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsFileEncoding() {
		// get XML from command
		ExportTableCommand command = new ExportTableCommand();
		ExportTableOptions options = new ExportTableOptions(1L);
		options.setFileEncoding(FileEncoding.ISO_8859_1);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<FILE_ENCODING>utf-8</FILE_ENCODING>",
				"<FILE_ENCODING>" + FileEncoding.ISO_8859_1.value() + "</FILE_ENCODING>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsAddToStoredFiles() {
		// get XML from command
		ExportTableCommand command = new ExportTableCommand();
		ExportTableOptions options = new ExportTableOptions(1L);
		options.setAddToStoredFiles(true);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<ADD_TO_STORED_FILES>FALSE</ADD_TO_STORED_FILES>",
				"<ADD_TO_STORED_FILES>" + "TRUE" + "</ADD_TO_STORED_FILES>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsLastModifiedRange() {
		// get XML from command
		ExportTableCommand command = new ExportTableCommand();
		ExportTableOptions options = new ExportTableOptions(1L);
		LocalDateTime startDateTime = LocalDateTime.of(2017, 2, 3, 4, 5);
		LocalDateTime endDateTime = LocalDateTime.of(2018, 6, 7, 8, 9);
		DateTimeRange lastModifiedRange = new DateTimeRange(startDateTime, endDateTime);
		options.setLastModifiedRange(lastModifiedRange);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String lastModifiedRangeString = "<DATE_START>" + startDateTime.format(formatter) + "</DATE_START><DATE_END>"
				+ endDateTime.format(formatter) + "</DATE_END>";
		String controlString = defaultRequest.replace("<ADD_TO_STORED_FILES>",
				lastModifiedRangeString + "<ADD_TO_STORED_FILES>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse() {
		ExportTableCommand command = context.getBean(ExportTableCommand.class);

		ExportTableOptions options = new ExportTableOptions(1L);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		jobPollingContainer.setJobId(10L);
		Map<String, String> parameters = new HashMap<String, String>();
		String filePath = "/path/to/file.csv";
		parameters.put("FILE_PATH", filePath);
		jobPollingContainer.setParameters(parameters);

		String jobDescription = "String Job Description";
		String listName = "Test List";
		String listId = "1";
		String fileName = "file.csv";
		String numProcessed = "5";
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("FILENAME", fileName);
		jobParameters.put("LIST_NAME", listName);
		jobParameters.put("LIST_ID", listId);
		jobParameters.put("NUM_PROCESSED", numProcessed);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<ExportTableResponse> responseContainer = command.readResponse(jobPollingContainer,
				jobResponse, options);
		ExportTableResponse response = responseContainer.getResposne();

		assertEquals(response.getDescription(), jobDescription);
		assertEquals(response.getListName(), listName);
		assertEquals(response.getListId(), Long.valueOf(listId));
		assertEquals(response.getNumProcessed(), Long.valueOf(numProcessed));
		assertEquals(response.getRemoteFilePath(), filePath);
		assertEquals(response.getRemoteFileName(), fileName);
	}
}
