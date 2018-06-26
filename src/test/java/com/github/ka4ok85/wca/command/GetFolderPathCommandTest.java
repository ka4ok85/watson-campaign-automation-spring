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
import com.github.ka4ok85.wca.constants.GetFolderPathObjectSubType;
import com.github.ka4ok85.wca.constants.GetFolderPathObjectType;
import com.github.ka4ok85.wca.options.GetFolderPathOptions;
import com.github.ka4ok85.wca.response.GetFolderPathResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetFolderPathCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<GetFolderPath>",
			"<OBJECT_TYPE>Data</OBJECT_TYPE>", "<OBJECT_ID>1</OBJECT_ID>", "</GetFolderPath>", "</Body>",
			"</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		GetFolderPathCommand command = new GetFolderPathCommand();
		GetFolderPathOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		GetFolderPathCommand command = new GetFolderPathCommand();
		GetFolderPathOptions options = new GetFolderPathOptions(GetFolderPathObjectType.Data, 1L);

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
	public void testBuildXmlHonorsObjectType() {
		// get XML from command
		GetFolderPathCommand command = new GetFolderPathCommand();
		GetFolderPathOptions options = new GetFolderPathOptions(GetFolderPathObjectType.Mailing, "id");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<OBJECT_TYPE>Data</OBJECT_TYPE>",
				"<OBJECT_TYPE>Mailing</OBJECT_TYPE>");
		controlString = controlString.replace("<OBJECT_ID>1</OBJECT_ID>", "<FOLDER_ID>id</FOLDER_ID>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponseHonorsVisitorAssociation()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetFolderPathCommand command = context.getBean(GetFolderPathCommand.class);

		GetFolderPathOptions options = new GetFolderPathOptions(GetFolderPathObjectType.Data, 1L);

		String folderPath = "Shared";
		GetFolderPathObjectSubType objectSubType = GetFolderPathObjectSubType.Database;
		String envelope = "<RESULT><SUCCESS>true</SUCCESS><FOLDER_PATH>" + folderPath
				+ "</FOLDER_PATH><OBJECT_SUB_TYPE>" + objectSubType + "</OBJECT_SUB_TYPE></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetFolderPathResponse> responseContainer = command.readResponse(resultNode, options);
		GetFolderPathResponse response = responseContainer.getResposne();

		assertEquals(response.getFolderPath(), folderPath);
		assertEquals(response.getObjectSubType(), objectSubType);
	}
}
