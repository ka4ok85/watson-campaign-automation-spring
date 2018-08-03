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
import com.github.ka4ok85.wca.options.PreviewMailingOptions;
import com.github.ka4ok85.wca.response.PreviewMailingResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class PreviewMailingCommandTest {

	@Autowired
	ApplicationContext context;
	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>", "<PreviewMailing>",
			"<MailingId>1</MailingId>", "</PreviewMailing>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		PreviewMailingCommand command = new PreviewMailingCommand();
		PreviewMailingOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		PreviewMailingCommand command = new PreviewMailingCommand();
		PreviewMailingOptions options = new PreviewMailingOptions(1L);

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
	public void testBuildXmlHonorsRecipientEmail() {
		// get XML from command
		PreviewMailingCommand command = new PreviewMailingCommand();
		PreviewMailingOptions options = new PreviewMailingOptions(1L);
		options.setRecipientEmail("test@github.com");

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</MailingId>",
				"</MailingId><RecipientEmail>test@github.com</RecipientEmail>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		PreviewMailingCommand command = context.getBean(PreviewMailingCommand.class);

		PreviewMailingOptions options = new PreviewMailingOptions(1L);
		String hTMLBody = "test html body";
		String aOLBody = "test AOL";
		String textBody = "test text";
		String sPAMScore = "test spam info";

		String envelope = "<RESULT><SUCCESS>true</SUCCESS><HTMLBody>" + hTMLBody + "</HTMLBody><AOLBody>" + aOLBody
				+ "</AOLBody><TextBody>" + textBody + "</TextBody><SPAMScore>" + sPAMScore + "</SPAMScore></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<PreviewMailingResponse> responseContainer = command.readResponse(resultNode, options);
		PreviewMailingResponse response = responseContainer.getResposne();

		assertEquals(response.getHtmlBody(), hTMLBody);
		assertEquals(response.getAolBody(), aOLBody);
		assertEquals(response.getTextBody(), textBody);
		assertEquals(response.getSpamScore(), sPAMScore);
	}
}
