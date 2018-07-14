package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.sftp.SFTP;
import com.github.ka4ok85.wca.utils.DateTimeRange;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })

public class ExportListCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<ExportList>",
			"<LIST_ID>1</LIST_ID>", "<EXPORT_TYPE>ALL</EXPORT_TYPE>", "<EXPORT_FORMAT>CSV</EXPORT_FORMAT>",
			"<FILE_ENCODING>utf-8</FILE_ENCODING>", "<ADD_TO_STORED_FILES>FALSE</ADD_TO_STORED_FILES>",
			"<INCLUDE_LEAD_SOURCE>FALSE</INCLUDE_LEAD_SOURCE>",
			"<INCLUDE_LIST_ID_IN_FILE>FALSE</INCLUDE_LIST_ID_IN_FILE>",
			"<INCLUDE_RECIPIENT_ID>FALSE</INCLUDE_RECIPIENT_ID>", "</ExportList>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
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
	public void testBuildXmlHonorsExportType() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
		options.setExportType(ListExportType.UNDELIVERABLE);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<EXPORT_TYPE>ALL</EXPORT_TYPE>",
				"<EXPORT_TYPE>" + ListExportType.UNDELIVERABLE.value() + "</EXPORT_TYPE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsExportFormat() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
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
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
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
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
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
	public void testBuildXmlHonorsIncludeLeadSource() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
		options.setIncludeLeadSource(true);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<INCLUDE_LEAD_SOURCE>FALSE</INCLUDE_LEAD_SOURCE>",
				"<INCLUDE_LEAD_SOURCE>" + "TRUE" + "</INCLUDE_LEAD_SOURCE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsIncludeListId() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
		options.setIncludeListId(true);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<INCLUDE_LIST_ID_IN_FILE>FALSE</INCLUDE_LIST_ID_IN_FILE>",
				"<INCLUDE_LIST_ID_IN_FILE>" + "TRUE" + "</INCLUDE_LIST_ID_IN_FILE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsIncludeRecipientId() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
		options.setIncludeRecipientId(true);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<INCLUDE_RECIPIENT_ID>FALSE</INCLUDE_RECIPIENT_ID>",
				"<INCLUDE_RECIPIENT_ID>" + "TRUE" + "</INCLUDE_RECIPIENT_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsLastModifiedRange() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
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
	public void testBuildXmlHonorsExportColumns() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
		List<String> exportColumns = new ArrayList<String>();
		exportColumns.add("Column A");
		exportColumns.add("Column B");
		options.setExportColumns(exportColumns);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String columnsString = "<EXPORT_COLUMNS>";
		for (String column : exportColumns) {
			columnsString = columnsString + "<COLUMN>" + column + "</COLUMN>";
		}

		columnsString = columnsString + "</EXPORT_COLUMNS>";
		String controlString = defaultRequest.replace("</INCLUDE_RECIPIENT_ID>",
				"</INCLUDE_RECIPIENT_ID>" + columnsString);
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsZeroExportColumns() {
		// get XML from command
		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = new ExportListOptions(1L);
		List<String> exportColumns = new ArrayList<String>();
		options.setExportColumns(exportColumns);
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
	public void testReadResponse() {
		ExportListCommand command = context.getBean(ExportListCommand.class);
		SFTP sftp = mock(SFTP.class);
		command.setSftp(sftp);
		ExportListOptions options = new ExportListOptions(1L);
		String localAbsoluteFilePath = "/local/path/data.csv";
		options.setLocalAbsoluteFilePath(localAbsoluteFilePath);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		jobPollingContainer.setJobId(10L);
		Map<String, String> parameters = new HashMap<String, String>();
		String filePath = "/path/to/file.csv";
		parameters.put("FILE_PATH", filePath);
		jobPollingContainer.setParameters(parameters);

		String jobDescription = "String Job Description";
		String listName = "Test List";
		String fileEncoding = "iso-8859-1";
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);
		Map<String, String> jobParameters = new HashMap<String, String>();
		jobParameters.put("LIST_NAME", listName);
		jobParameters.put("FILE_ENCODING", fileEncoding);
		jobResponse.setParameters(jobParameters);

		ResponseContainer<ExportListResponse> responseContainer = command.readResponse(jobPollingContainer, jobResponse,
				options);
		ExportListResponse response = responseContainer.getResposne();

		assertEquals(response.getDescription(), jobDescription);
		assertEquals(response.getFileEncoding(), FileEncoding.ISO_8859_1);
		assertEquals(response.getListName(), listName);
		assertEquals(response.getRemoteFileName(), filePath);
		verify(sftp, times(1)).download(filePath, localAbsoluteFilePath);
	}
}
