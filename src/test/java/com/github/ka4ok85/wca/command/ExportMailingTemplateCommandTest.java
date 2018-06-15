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
import com.github.ka4ok85.wca.options.ExportMailingTemplateOptions;
import com.github.ka4ok85.wca.response.ExportMailingTemplateResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class ExportMailingTemplateCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<ExportMailingTemplate>", "<TEMPLATE_ID>1</TEMPLATE_ID>",
			"<ADD_TO_STORED_FILES>FALSE</ADD_TO_STORED_FILES>", "</ExportMailingTemplate>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		ExportMailingTemplateCommand command = new ExportMailingTemplateCommand();
		ExportMailingTemplateOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		ExportMailingTemplateCommand command = new ExportMailingTemplateCommand();
		ExportMailingTemplateOptions options = new ExportMailingTemplateOptions(1L);

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
	public void testBuildXmlHonorsAddToStoredFiles() {
		// get XML from command
		ExportMailingTemplateCommand command = new ExportMailingTemplateCommand();
		ExportMailingTemplateOptions options = new ExportMailingTemplateOptions(1L);
		options.setAddToStoredFiles(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("<ADD_TO_STORED_FILES>FALSE</ADD_TO_STORED_FILES>",
				"<ADD_TO_STORED_FILES>TRUE</ADD_TO_STORED_FILES>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		ExportMailingTemplateCommand command = context.getBean(ExportMailingTemplateCommand.class);
		ExportMailingTemplateOptions options = new ExportMailingTemplateOptions(1L);

		String remoteFileName = "SOME_PATH";
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><FILE_PATH>" + remoteFileName + "</FILE_PATH></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<ExportMailingTemplateResponse> responseContainer = command.readResponse(resultNode, options);
		ExportMailingTemplateResponse response = responseContainer.getResposne();

		assertEquals(response.getRemoteFileName(), remoteFileName);
	}

}
