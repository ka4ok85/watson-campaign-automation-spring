package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

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
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.JoinTableOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.JoinTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class JoinTableCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<JoinTable>",
			"<TABLE_ID>1</TABLE_ID>", "<LIST_ID>2</LIST_ID>", "<MAP_FIELD>", "<LIST_FIELD><![CDATA[rid]]></LIST_FIELD>",
			"<TABLE_FIELD><![CDATA[recipid]]></TABLE_FIELD>", "</MAP_FIELD>", "</JoinTable>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		JoinTableCommand command = new JoinTableCommand();
		JoinTableOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		JoinTableCommand command = new JoinTableCommand();

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		options.setListId(2L);
		options.setTableId(1L);
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
	public void testBuildXmlAcceptsNames() {
		// get XML from command
		JoinTableCommand command = new JoinTableCommand();

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		String listName = "test list 1";
		options.setListName(listName);
		String tableName = "test table 2";
		options.setTableName(tableName);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<TABLE_ID>1</TABLE_ID>",
				"<TABLE_NAME>" + tableName + "</TABLE_NAME>");
		controlString = controlString.replace("<LIST_ID>2</LIST_ID>", "<LIST_NAME>" + listName + "</LIST_NAME>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresTableIdOrTableName() {
		JoinTableCommand command = new JoinTableCommand();

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		String tableName = "test table 2";
		options.setTableName(tableName);
		command.buildXmlRequest(options);
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlRequiresListIdOrListName() {
		JoinTableCommand command = new JoinTableCommand();

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlHonorsTableVisibility() {
		// get XML from command
		JoinTableCommand command = new JoinTableCommand();

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		options.setListId(2L);
		options.setTableId(1L);

		Visibility tableVisibility = Visibility.SHARED;
		options.setTableVisibility(tableVisibility);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</TABLE_ID>",
				"</TABLE_ID><TABLE_VISIBILITY>" + tableVisibility.value().toString() + "</TABLE_VISIBILITY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsListVisibility() {
		// get XML from command
		JoinTableCommand command = new JoinTableCommand();

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		options.setListId(2L);
		options.setTableId(1L);

		Visibility listVisibility = Visibility.PRIVATE;
		options.setListVisibility(listVisibility);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</LIST_ID>",
				"</LIST_ID><LIST_VISIBILITY>" + listVisibility.value().toString() + "</LIST_VISIBILITY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsRemoveParam() {
		// get XML from command
		JoinTableCommand command = new JoinTableCommand();

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		options.setListId(2L);
		options.setTableId(1L);

		options.setIsRemoveRelationship(true);
		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</LIST_ID>", "</LIST_ID><REMOVE>TRUE</REMOVE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse() {
		JoinTableCommand command = context.getBean(JoinTableCommand.class);

		Map<String, String> mapFields = new HashMap<String, String>();
		mapFields.put("rid", "recipid");
		JoinTableOptions options = new JoinTableOptions(mapFields);
		options.setListId(2L);
		options.setTableId(1L);

		JobPollingContainer jobPollingContainer = new JobPollingContainer();
		Long jobId = 10L;
		jobPollingContainer.setJobId(10L);

		String jobDescription = "String Job Description";
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobDescription(jobDescription);

		ResponseContainer<JoinTableResponse> responseContainer = command.readResponse(jobPollingContainer, jobResponse,
				options);
		JoinTableResponse response = responseContainer.getResposne();

		assertEquals(response.getJobId(), jobId);
		assertEquals(response.getDescription(), jobDescription);
	}
}
