package com.github.ka4ok85.wca.command;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.github.ka4ok85.wca.options.GetReportIdByDateOptions;
import com.github.ka4ok85.wca.response.GetReportIdByDateResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.ReportIdByDateMailing;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetReportIdByDateCommandTest {

	@Autowired
	ApplicationContext context;
	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<GetReportIdByDate>", "<MAILING_ID>1</MAILING_ID>", "<DATE_START>02/01/2018 00:00:00</DATE_START>",
			"<DATE_END>03/01/2018 23:59:59</DATE_END>", "</GetReportIdByDate>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		GetReportIdByDateCommand command = new GetReportIdByDateCommand();
		GetReportIdByDateOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		GetReportIdByDateCommand command = new GetReportIdByDateCommand();
		LocalDateTime startDate = LocalDateTime.of(2018, 02, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 03, 01, 23, 59, 59);
		GetReportIdByDateOptions options = new GetReportIdByDateOptions(1L, startDate, endDate);

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
		GetReportIdByDateCommand command = context.getBean(GetReportIdByDateCommand.class);
		LocalDateTime startDate = LocalDateTime.of(2018, 02, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 03, 01, 23, 59, 59);
		GetReportIdByDateOptions options = new GetReportIdByDateOptions(1L, startDate, endDate);

		Long reportId1 = 1052022240L;
		Long reportId2 = 1052022241L;
		String datetime1 = "6/11/18 11:49 AM";
		String datetime2 = "7/12/18 02:59 PM";
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Mailing><ReportId>" + reportId1 + "</ReportId><SentTS>"
				+ datetime1 + "</SentTS></Mailing><Mailing><ReportId>" + reportId2 + "</ReportId><SentTS>" + datetime2
				+ "</SentTS></Mailing></RESULT>";

		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetReportIdByDateResponse> responseContainer = command.readResponse(resultNode, options);
		GetReportIdByDateResponse response = responseContainer.getResposne();

		List<ReportIdByDateMailing> mailings = response.getMailings();
		assertEquals(mailings.size(), 2);
		assertEquals(mailings.get(0).getReportId(), reportId1);
		assertEquals(mailings.get(1).getReportId(), reportId2);
		final DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		assertEquals(mailings.get(0).getSentDateTime(), LocalDateTime.parse(datetime1, responseFormatter));
		assertEquals(mailings.get(1).getSentDateTime(), LocalDateTime.parse(datetime2, responseFormatter));
	}
	
	@Test
	public void testReadResponseHonorsDateFormat()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetReportIdByDateCommand command = context.getBean(GetReportIdByDateCommand.class);
		LocalDateTime startDate = LocalDateTime.of(2018, 02, 01, 0, 0, 0);
		LocalDateTime endDate = LocalDateTime.of(2018, 03, 01, 23, 59, 59);
		GetReportIdByDateOptions options = new GetReportIdByDateOptions(1L, startDate, endDate);

		Long reportId1 = 1052022240L;
		Long reportId2 = 1052022241L;
		String datetime1 = "6/11/18 12:49 AM";
		String datetime2 = "7/12/18 02:59 PM";
		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Mailing><ReportId>" + reportId1 + "</ReportId><SentTS>"
				+ datetime1 + "</SentTS></Mailing><Mailing><ReportId>" + reportId2 + "</ReportId><SentTS>" + datetime2
				+ "</SentTS></Mailing></RESULT>";

		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetReportIdByDateResponse> responseContainer = command.readResponse(resultNode, options);
		GetReportIdByDateResponse response = responseContainer.getResposne();

		List<ReportIdByDateMailing> mailings = response.getMailings();
		assertEquals(mailings.size(), 2);
		assertEquals(mailings.get(0).getReportId(), reportId1);
		assertEquals(mailings.get(1).getReportId(), reportId2);
		final DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		assertEquals(mailings.get(0).getSentDateTime(), LocalDateTime.parse(datetime1, responseFormatter));
		assertEquals(mailings.get(1).getSentDateTime(), LocalDateTime.parse(datetime2, responseFormatter));
	}
}
