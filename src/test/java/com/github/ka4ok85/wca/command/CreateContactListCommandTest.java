package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.CreateContactListOptions;
import com.github.ka4ok85.wca.response.CreateContactListResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class CreateContactListCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<CreateContactList>", "<DATABASE_ID>1</DATABASE_ID>",
			"<CONTACT_LIST_NAME><![CDATA[test]]></CONTACT_LIST_NAME>", "<VISIBILITY>0</VISIBILITY>",
			"</CreateContactList>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		CreateContactListCommand command = new CreateContactListCommand();
		CreateContactListOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		CreateContactListCommand command = new CreateContactListCommand();
		CreateContactListOptions options = new CreateContactListOptions(1L, "test", Visibility.PRIVATE);

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
	public void testBuildXmlAcceptsParentFolderId() {
		// get XML from command
		CreateContactListCommand command = new CreateContactListCommand();
		CreateContactListOptions options = new CreateContactListOptions(1L, "test", Visibility.PRIVATE);
		Long parentFolderId = 123L;
		options.setParentFolderId(parentFolderId);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</VISIBILITY>",
				"</VISIBILITY><PARENT_FOLDER_ID>" + parentFolderId.toString() + "</PARENT_FOLDER_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlAcceptsParentFolderPath() {
		// get XML from command
		CreateContactListCommand command = new CreateContactListCommand();
		CreateContactListOptions options = new CreateContactListOptions(1L, "test", Visibility.PRIVATE);
		String parentFolderPath = "/test/path";
		options.setParentFolderPath(parentFolderPath);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</VISIBILITY>",
				"</VISIBILITY><PARENT_FOLDER_PATH>" + parentFolderPath + "</PARENT_FOLDER_PATH>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		CreateContactListCommand command = context.getBean(CreateContactListCommand.class);

		CreateContactListOptions options = new CreateContactListOptions(1L, "test", Visibility.PRIVATE);

		Long contactListId = 91348L;
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><CONTACT_LIST_ID>" + contactListId.toString()
				+ "</CONTACT_LIST_ID><PARENT_FOLDER_ID>123</PARENT_FOLDER_ID></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<CreateContactListResponse> responseContainer = command.readResponse(resultNode, options);
		CreateContactListResponse response = responseContainer.getResposne();
		assertEquals(response.getContactListId(), contactListId);
	}
}
