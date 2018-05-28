package com.github.ka4ok85.wca.command;

import javax.xml.transform.Source;

import org.junit.Assert;
import org.junit.Test;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.constants.ExportFormat;
import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.constants.ListExportType;
import com.github.ka4ok85.wca.options.ExportListOptions;


public class ExportListCommandTest {
	
	private String defaultRequest = String.join(
		    System.getProperty("line.separator"),
		    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
		    "<Envelope>",
		    	"<Body>",
		    		"<ExportList>",
		    			"<LIST_ID>1</LIST_ID>",
		    			"<EXPORT_TYPE>ALL</EXPORT_TYPE>",
		    			"<EXPORT_FORMAT>CSV</EXPORT_FORMAT>",
		    			"<FILE_ENCODING>utf-8</FILE_ENCODING>",
		    			"<ADD_TO_STORED_FILES>FALSE</ADD_TO_STORED_FILES>",
		    			"<INCLUDE_LEAD_SOURCE>FALSE</INCLUDE_LEAD_SOURCE>",
		    			"<INCLUDE_LIST_ID_IN_FILE>FALSE</INCLUDE_LIST_ID_IN_FILE>",
		    			"<INCLUDE_RECIPIENT_ID>FALSE</INCLUDE_RECIPIENT_ID>",
		    		"</ExportList>",
		    	"</Body>",
		    "</Envelope>"
		);

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
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
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
		String controlString = defaultRequest.replace("<EXPORT_TYPE>ALL</EXPORT_TYPE>", "<EXPORT_TYPE>" + ListExportType.UNDELIVERABLE.value() + "</EXPORT_TYPE>");
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
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
		String controlString = defaultRequest.replace("<EXPORT_FORMAT>CSV</EXPORT_FORMAT>", "<EXPORT_FORMAT>" + ExportFormat.PIPE.value() + "</EXPORT_FORMAT>");
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
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
		String controlString = defaultRequest.replace("<FILE_ENCODING>utf-8</FILE_ENCODING>", "<FILE_ENCODING>" + FileEncoding.ISO_8859_1.value() + "</FILE_ENCODING>");
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
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
		String controlString = defaultRequest.replace("<ADD_TO_STORED_FILES>FALSE</ADD_TO_STORED_FILES>", "<ADD_TO_STORED_FILES>" + "TRUE" + "</ADD_TO_STORED_FILES>");
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
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
		String controlString = defaultRequest.replace("<INCLUDE_LEAD_SOURCE>FALSE</INCLUDE_LEAD_SOURCE>", "<INCLUDE_LEAD_SOURCE>" + "TRUE" + "</INCLUDE_LEAD_SOURCE>");
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
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
		String controlString = defaultRequest.replace("<INCLUDE_LIST_ID_IN_FILE>FALSE</INCLUDE_LIST_ID_IN_FILE>", "<INCLUDE_LIST_ID_IN_FILE>" + "TRUE" + "</INCLUDE_LIST_ID_IN_FILE>");
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
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
		String controlString = defaultRequest.replace("<INCLUDE_RECIPIENT_ID>FALSE</INCLUDE_RECIPIENT_ID>", "<INCLUDE_RECIPIENT_ID>" + "TRUE" + "</INCLUDE_RECIPIENT_ID>");
		Source control = Input.fromString(controlString).build();
		
		Diff myDiff = DiffBuilder.compare(control).withTest(test)
				.ignoreWhitespace()
	            .checkForSimilar()
	            .build();
	    Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}
/*
	@Test
	public void testReadResponseNodeExportListOptions() {
		fail("Not yet implemented");
	}
*/
}
