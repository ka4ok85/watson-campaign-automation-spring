package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

import com.github.ka4ok85.wca.config.SpringConfig;
import com.github.ka4ok85.wca.constants.ListType;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.response.GetListsResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetListsCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<GetLists>",
			"<VISIBILITY>1</VISIBILITY>", "<LIST_TYPE>2</LIST_TYPE>", "</GetLists>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		GetListsCommand command = new GetListsCommand();
		GetListsOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		GetListsCommand command = new GetListsCommand();
		GetListsOptions options = new GetListsOptions();

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
	public void testBuildXmlHonorsVisibility() {
		// get XML from command
		GetListsCommand command = new GetListsCommand();
		GetListsOptions options = new GetListsOptions();
		options.setVisibility(Visibility.PRIVATE);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<VISIBILITY>1</VISIBILITY>",
				"<VISIBILITY>" + Visibility.PRIVATE.value() + "</VISIBILITY>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsListType() {
		// get XML from command
		GetListsCommand command = new GetListsCommand();
		GetListsOptions options = new GetListsOptions();
		options.setListType(ListType.RELATIONAL_TABLES);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<LIST_TYPE>2</LIST_TYPE>",
				"<LIST_TYPE>" + ListType.RELATIONAL_TABLES.value() + "</LIST_TYPE>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsIncludeAllLists() {
		// get XML from command
		GetListsCommand command = new GetListsCommand();
		GetListsOptions options = new GetListsOptions();
		options.setIncludeAllLists(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</LIST_TYPE>",
				"</LIST_TYPE><INCLUDE_ALL_LISTS>TRUE</INCLUDE_ALL_LISTS>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsIncludeFolderId() {
		// get XML from command
		GetListsCommand command = new GetListsCommand();
		GetListsOptions options = new GetListsOptions();
		Long folderId = 345L;
		options.setFolderId(folderId);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</LIST_TYPE>",
				"</LIST_TYPE><FOLDER_ID>" + folderId + "</FOLDER_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsIncludeTags() {
		// get XML from command
		GetListsCommand command = new GetListsCommand();
		GetListsOptions options = new GetListsOptions();
		options.setIncludeTags(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</LIST_TYPE>", "</LIST_TYPE><INCLUDE_TAGS>TRUE</INCLUDE_TAGS>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetListsCommand command = context.getBean(GetListsCommand.class);
		GetListsOptions options = new GetListsOptions();
		options.setIncludeTags(true);

		Long id = 1L;
		String name = "test";
		ListType type = ListType.SEED_LISTS;
		Long size = 4L;
		Long numOptOuts = 5L;
		Long numUndeliverable = 6L;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		LocalDateTime lastModified = LocalDateTime.of(2018, 9, 18, 21, 34);
		Visibility visibility = Visibility.PRIVATE;
		String parentName = "test parent";
		String userId = "111-333-555";
		Long parentFolderId = 7L;
		boolean isFolder = false;
		boolean flaggedForBackup = false;
		Long suppressionListId = 8L;
		boolean isDatabaseTemplate = true;
		String tag1 = "tag 1";
		String tag2 = "tag 2";

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><LIST><ID>" + id + "</ID><NAME>" + name + "</NAME><TYPE>"
				+ type.value() + "</TYPE><SIZE>" + size + "</SIZE><NUM_OPT_OUTS>" + numOptOuts
				+ "</NUM_OPT_OUTS><NUM_UNDELIVERABLE>" + numUndeliverable + "</NUM_UNDELIVERABLE><LAST_MODIFIED>"
				+ lastModified.format(formatter) + "</LAST_MODIFIED><VISIBILITY>" + visibility.value()
				+ "</VISIBILITY><PARENT_NAME>" + parentName + "</PARENT_NAME><USER_ID>" + userId
				+ "</USER_ID><PARENT_FOLDER_ID>" + parentFolderId + "</PARENT_FOLDER_ID><IS_FOLDER>" + isFolder
				+ "</IS_FOLDER><FLAGGED_FOR_BACKUP>" + flaggedForBackup + "</FLAGGED_FOR_BACKUP><SUPPRESSION_LIST_ID>"
				+ suppressionListId + "</SUPPRESSION_LIST_ID><IS_DATABASE_TEMPLATE>" + isDatabaseTemplate
				+ "</IS_DATABASE_TEMPLATE><TAGS><TAG>" + tag1 + "</TAG><TAG>" + tag2 + "</TAG></TAGS></LIST></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetListsResponse> responseContainer = command.readResponse(resultNode, options);
		GetListsResponse response = responseContainer.getResposne();

		assertEquals(response.getLists().size(), 1);

		assertEquals(response.getLists().get(0).getFolderId(), parentFolderId);
		assertEquals(response.getLists().get(0).getId(), id);
		assertEquals(response.getLists().get(0).getLastModifiedDate(), lastModified);
		assertEquals(response.getLists().get(0).getName(), name);
		assertEquals(response.getLists().get(0).getNumberOptOuts(), numOptOuts);
		assertEquals(response.getLists().get(0).getNumberUndeliverables(), numUndeliverable);
		assertEquals(response.getLists().get(0).getParentName(), parentName);
		assertEquals(response.getLists().get(0).getSize(), size);
		assertEquals(response.getLists().get(0).getSuppressionList(), suppressionListId);
		assertEquals(response.getLists().get(0).getType(), type);
		assertEquals(response.getLists().get(0).getUserId(), userId);
		assertEquals(response.getLists().get(0).getVisibility(), visibility);
		assertEquals(response.getLists().get(0).isDatabaseTemplate(), isDatabaseTemplate);
		assertEquals(response.getLists().get(0).isFlaggedForBackup(), flaggedForBackup);
		assertEquals(response.getLists().get(0).isFolder(), isFolder);
		assertEquals(response.getLists().get(0).getTags().size(), 2);
		assertEquals(response.getLists().get(0).getTags().get(0), tag1);
		assertEquals(response.getLists().get(0).getTags().get(1), tag2);

	}

	@Test
	public void testReadResponseHonorsDateFormat()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetListsCommand command = context.getBean(GetListsCommand.class);
		GetListsOptions options = new GetListsOptions();
		options.setIncludeTags(true);

		Long id = 1L;
		String name = "test";
		ListType type = ListType.SEED_LISTS;
		Long size = 4L;
		Long numOptOuts = 5L;
		Long numUndeliverable = 6L;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		LocalDateTime lastModified = LocalDateTime.of(2018, 9, 18, 12, 34);
		Visibility visibility = Visibility.PRIVATE;
		String parentName = "test parent";
		String userId = "111-333-555";
		Long parentFolderId = 7L;
		boolean isFolder = false;
		boolean flaggedForBackup = false;
		Long suppressionListId = 8L;
		boolean isDatabaseTemplate = true;
		String tag1 = "tag 1";
		String tag2 = "tag 2";

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><LIST><ID>" + id + "</ID><NAME>" + name + "</NAME><TYPE>"
				+ type.value() + "</TYPE><SIZE>" + size + "</SIZE><NUM_OPT_OUTS>" + numOptOuts
				+ "</NUM_OPT_OUTS><NUM_UNDELIVERABLE>" + numUndeliverable + "</NUM_UNDELIVERABLE><LAST_MODIFIED>"
				+ lastModified.format(formatter) + "</LAST_MODIFIED><VISIBILITY>" + visibility.value()
				+ "</VISIBILITY><PARENT_NAME>" + parentName + "</PARENT_NAME><USER_ID>" + userId
				+ "</USER_ID><PARENT_FOLDER_ID>" + parentFolderId + "</PARENT_FOLDER_ID><IS_FOLDER>" + isFolder
				+ "</IS_FOLDER><FLAGGED_FOR_BACKUP>" + flaggedForBackup + "</FLAGGED_FOR_BACKUP><SUPPRESSION_LIST_ID>"
				+ suppressionListId + "</SUPPRESSION_LIST_ID><IS_DATABASE_TEMPLATE>" + isDatabaseTemplate
				+ "</IS_DATABASE_TEMPLATE><TAGS><TAG>" + tag1 + "</TAG><TAG>" + tag2 + "</TAG></TAGS></LIST></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetListsResponse> responseContainer = command.readResponse(resultNode, options);
		GetListsResponse response = responseContainer.getResposne();

		assertEquals(response.getLists().size(), 1);

		assertEquals(response.getLists().get(0).getFolderId(), parentFolderId);
		assertEquals(response.getLists().get(0).getId(), id);
		assertEquals(response.getLists().get(0).getLastModifiedDate(), lastModified);
		assertEquals(response.getLists().get(0).getName(), name);
		assertEquals(response.getLists().get(0).getNumberOptOuts(), numOptOuts);
		assertEquals(response.getLists().get(0).getNumberUndeliverables(), numUndeliverable);
		assertEquals(response.getLists().get(0).getParentName(), parentName);
		assertEquals(response.getLists().get(0).getSize(), size);
		assertEquals(response.getLists().get(0).getSuppressionList(), suppressionListId);
		assertEquals(response.getLists().get(0).getType(), type);
		assertEquals(response.getLists().get(0).getUserId(), userId);
		assertEquals(response.getLists().get(0).getVisibility(), visibility);
		assertEquals(response.getLists().get(0).isDatabaseTemplate(), isDatabaseTemplate);
		assertEquals(response.getLists().get(0).isFlaggedForBackup(), flaggedForBackup);
		assertEquals(response.getLists().get(0).isFolder(), isFolder);
		assertEquals(response.getLists().get(0).getTags().size(), 2);
		assertEquals(response.getLists().get(0).getTags().get(0), tag1);
		assertEquals(response.getLists().get(0).getTags().get(1), tag2);

	}

}
