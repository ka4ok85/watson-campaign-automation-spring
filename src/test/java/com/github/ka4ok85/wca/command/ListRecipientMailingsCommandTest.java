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
import com.github.ka4ok85.wca.options.ListRecipientMailingsOptions;
import com.github.ka4ok85.wca.response.ListRecipientMailingsResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class ListRecipientMailingsCommandTest {

	@Autowired
	ApplicationContext context;

	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<ListRecipientMailings>", "<LIST_ID>1</LIST_ID>", "<RECIPIENT_ID>123</RECIPIENT_ID>",
			"</ListRecipientMailings>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		ListRecipientMailingsCommand command = new ListRecipientMailingsCommand();
		ListRecipientMailingsOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		ListRecipientMailingsCommand command = new ListRecipientMailingsCommand();
		ListRecipientMailingsOptions options = new ListRecipientMailingsOptions(1L, 123L);

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
		ListRecipientMailingsCommand command = context.getBean(ListRecipientMailingsCommand.class);
		ListRecipientMailingsOptions options = new ListRecipientMailingsOptions(1L, 123L);

		Long mailingId = 1L;
		String mailingName = "Test MailingName";
		String sentDateTime = "7/13/18 8:44 AM";
		Long totalOpens = 2L;
		Long totalClickstreams = 3L;
		Long totalClicks = 4L;
		Long totalConversions = 5L;
		Long totalAttachments = 6L;
		Long totalForwards = 7L;
		Long totalMediaPlays = 8L;
		Long totalBounces = 9L;
		Long totalOptOuts = 10L;

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Mailing><MailingId>" + mailingId
				+ "</MailingId><MailingName>" + mailingName + "</MailingName><SentTS>" + sentDateTime
				+ "</SentTS><TotalOpens>" + totalOpens + "</TotalOpens><TotalClickstreams>" + totalClickstreams
				+ "</TotalClickstreams><TotalClicks>" + totalClicks + "</TotalClicks><TotalConversions>"
				+ totalConversions + "</TotalConversions><TotalAttachments>" + totalAttachments
				+ "</TotalAttachments><TotalForwards>" + totalForwards + "</TotalForwards><TotalMediaPlays>"
				+ totalMediaPlays + "</TotalMediaPlays><TotalBounces>" + totalBounces + "</TotalBounces><TotalOptOuts>"
				+ totalOptOuts + "</TotalOptOuts></Mailing></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<ListRecipientMailingsResponse> responseContainer = command.readResponse(resultNode, options);
		ListRecipientMailingsResponse response = responseContainer.getResposne();

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		assertEquals(response.getMailings().size(), 1);
		assertEquals(response.getMailings().get(0).getMailingId(), mailingId);
		assertEquals(response.getMailings().get(0).getMailingName(), mailingName);
		assertEquals(response.getMailings().get(0).getSentDateTime(), LocalDateTime.parse(sentDateTime, formatter));
		assertEquals(response.getMailings().get(0).getSentDateTime(), LocalDateTime.parse(sentDateTime, formatter));
		assertEquals(response.getMailings().get(0).getTotalOpens(), totalOpens);
		assertEquals(response.getMailings().get(0).getTotalClickstreams(), totalClickstreams);
		assertEquals(response.getMailings().get(0).getTotalClicks(), totalClicks);
		assertEquals(response.getMailings().get(0).getTotalConversions(), totalConversions);
		assertEquals(response.getMailings().get(0).getTotalAttachments(), totalAttachments);
		assertEquals(response.getMailings().get(0).getTotalForwards(), totalForwards);
		assertEquals(response.getMailings().get(0).getTotalMediaPlays(), totalMediaPlays);
		assertEquals(response.getMailings().get(0).getTotalBounces(), totalBounces);
		assertEquals(response.getMailings().get(0).getTotalOptOuts(), totalOptOuts);
	}

	@Test
	public void testReadResponseHonorsDateFormat()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		ListRecipientMailingsCommand command = context.getBean(ListRecipientMailingsCommand.class);
		ListRecipientMailingsOptions options = new ListRecipientMailingsOptions(1L, 123L);

		Long mailingId = 1L;
		String mailingName = "Test MailingName";
		String sentDateTime = "7/13/18 12:44 AM";
		Long totalOpens = 2L;
		Long totalClickstreams = 3L;
		Long totalClicks = 4L;
		Long totalConversions = 5L;
		Long totalAttachments = 6L;
		Long totalForwards = 7L;
		Long totalMediaPlays = 8L;
		Long totalBounces = 9L;
		Long totalOptOuts = 10L;

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Mailing><MailingId>" + mailingId
				+ "</MailingId><MailingName>" + mailingName + "</MailingName><SentTS>" + sentDateTime
				+ "</SentTS><TotalOpens>" + totalOpens + "</TotalOpens><TotalClickstreams>" + totalClickstreams
				+ "</TotalClickstreams><TotalClicks>" + totalClicks + "</TotalClicks><TotalConversions>"
				+ totalConversions + "</TotalConversions><TotalAttachments>" + totalAttachments
				+ "</TotalAttachments><TotalForwards>" + totalForwards + "</TotalForwards><TotalMediaPlays>"
				+ totalMediaPlays + "</TotalMediaPlays><TotalBounces>" + totalBounces + "</TotalBounces><TotalOptOuts>"
				+ totalOptOuts + "</TotalOptOuts></Mailing></RESULT>";
		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<ListRecipientMailingsResponse> responseContainer = command.readResponse(resultNode, options);
		ListRecipientMailingsResponse response = responseContainer.getResposne();

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		assertEquals(response.getMailings().size(), 1);
		assertEquals(response.getMailings().get(0).getMailingId(), mailingId);
		assertEquals(response.getMailings().get(0).getMailingName(), mailingName);
		assertEquals(response.getMailings().get(0).getSentDateTime(), LocalDateTime.parse(sentDateTime, formatter));
		assertEquals(response.getMailings().get(0).getSentDateTime(), LocalDateTime.parse(sentDateTime, formatter));
		assertEquals(response.getMailings().get(0).getTotalOpens(), totalOpens);
		assertEquals(response.getMailings().get(0).getTotalClickstreams(), totalClickstreams);
		assertEquals(response.getMailings().get(0).getTotalClicks(), totalClicks);
		assertEquals(response.getMailings().get(0).getTotalConversions(), totalConversions);
		assertEquals(response.getMailings().get(0).getTotalAttachments(), totalAttachments);
		assertEquals(response.getMailings().get(0).getTotalForwards(), totalForwards);
		assertEquals(response.getMailings().get(0).getTotalMediaPlays(), totalMediaPlays);
		assertEquals(response.getMailings().get(0).getTotalBounces(), totalBounces);
		assertEquals(response.getMailings().get(0).getTotalOptOuts(), totalOptOuts);
	}
}
