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
import com.github.ka4ok85.wca.options.AddContactToProgramOptions;
import com.github.ka4ok85.wca.response.AddContactToProgramResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class AddContactToProgramCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<AddContactToProgram>", "<PROGRAM_ID>1</PROGRAM_ID>", "<CONTACT_ID>2</CONTACT_ID>",
			"</AddContactToProgram>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		AddContactToProgramCommand command = new AddContactToProgramCommand();
		AddContactToProgramOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		AddContactToProgramCommand command = new AddContactToProgramCommand();
		AddContactToProgramOptions options = new AddContactToProgramOptions(1L, 2L);
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
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		AddContactToProgramCommand command = new AddContactToProgramCommand();
		AddContactToProgramOptions options = new AddContactToProgramOptions(1L, 2L);

		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(
						"<Envelope><Body><RESULT><SUCCESS>TRUE</SUCCESS></RESULT></Body></Envelope>".getBytes()))
				.getDocumentElement();

		ResponseContainer<AddContactToProgramResponse> responseContainer = command.readResponse(resultNode, options);
		AddContactToProgramResponse response = responseContainer.getResposne();
		assertEquals(response, null);
	}
}
