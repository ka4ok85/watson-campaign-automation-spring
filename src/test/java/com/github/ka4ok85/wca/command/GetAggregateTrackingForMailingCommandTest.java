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
import com.github.ka4ok85.wca.options.GetAggregateTrackingForMailingOptions;
import com.github.ka4ok85.wca.response.GetAggregateTrackingForMailingResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class })
public class GetAggregateTrackingForMailingCommandTest {

	@Autowired
	ApplicationContext context;
	private String defaultRequest = String.join(System.getProperty("line.separator"),
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "<Envelope>", "<Body>",
			"<GetAggregateTrackingForMailing>", "<MAILING_ID>1</MAILING_ID>", "<REPORT_ID>2</REPORT_ID>",
			"</GetAggregateTrackingForMailing>", "</Body>", "</Envelope>");

	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		GetAggregateTrackingForMailingCommand command = new GetAggregateTrackingForMailingCommand();
		GetAggregateTrackingForMailingOptions options = null;
		command.buildXmlRequest(options);
	}

	@Test
	public void testBuildXmlDefaultRequest() {
		// get XML from command
		GetAggregateTrackingForMailingCommand command = new GetAggregateTrackingForMailingCommand();
		GetAggregateTrackingForMailingOptions options = new GetAggregateTrackingForMailingOptions(1L, 2L);

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
	public void testBuildXmlHonorsTopDomain() {
		// get XML from command
		GetAggregateTrackingForMailingCommand command = new GetAggregateTrackingForMailingCommand();
		GetAggregateTrackingForMailingOptions options = new GetAggregateTrackingForMailingOptions(1L, 2L);
		options.setTopDomain(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</REPORT_ID>", "</REPORT_ID><TOP_DOMAIN>TRUE</TOP_DOMAIN>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsInboxMonitoring() {
		// get XML from command
		GetAggregateTrackingForMailingCommand command = new GetAggregateTrackingForMailingCommand();
		GetAggregateTrackingForMailingOptions options = new GetAggregateTrackingForMailingOptions(1L, 2L);
		options.setInboxMonitoring(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</REPORT_ID>",
				"</REPORT_ID><INBOX_MONITORING>TRUE</INBOX_MONITORING>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testBuildXmlHonorsPerClick() {
		// get XML from command
		GetAggregateTrackingForMailingCommand command = new GetAggregateTrackingForMailingCommand();
		GetAggregateTrackingForMailingOptions options = new GetAggregateTrackingForMailingOptions(1L, 2L);
		options.setPerClick(true);

		command.buildXmlRequest(options);
		String testString = command.getXML();
		Source test = Input.fromString(testString).build();

		// get control XML
		String controlString = defaultRequest.replace("</REPORT_ID>", "</REPORT_ID><PER_CLICK>TRUE</PER_CLICK>");
		Source control = Input.fromString(controlString).build();

		Diff myDiff = DiffBuilder.compare(control).withTest(test).ignoreWhitespace().checkForSimilar().build();
		Assert.assertFalse(myDiff.toString(), myDiff.hasDifferences());
	}

	@Test
	public void testReadResponse()
			throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		GetAggregateTrackingForMailingCommand command = context.getBean(GetAggregateTrackingForMailingCommand.class);
		GetAggregateTrackingForMailingOptions options = new GetAggregateTrackingForMailingOptions(1L, 2L);
		options.setInboxMonitoring(true);
		options.setPerClick(true);
		options.setTopDomain(true);

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
		Long mMailingId = 478360L;
		Long mReportId = 1052022258L;
		String mMailingName = "18_subject";
		String mSentDateTime = "2018-06-11 12:02:43.0";
		Long mNumSent = 3L;
		Long mNumSeeds = 1L;
		Long mNumSuppressed = 2L;
		Long mNumInboxMonitored = 4L;
		Long mNumBounceHard = 5L;
		Long mNumBounceSoft = 6L;
		Long mNumUniqueOpen = 7L;
		Long mNumGrossOpen = 8L;
		Long mNumUniqueClick = 9L;
		Long mNumGrossClick = 10L;
		Long mNumUniqueAttach = 11L;
		Long mNumGrossAttach = 12L;
		Long mNumUniqueClickstreams = 13L;
		Long mNumGrossClickstreams = 14L;
		Long mNumUniqueMedia = 15L;
		Long mNumGrossMedia = 16L;
		Long mNumGrossAbuse = 17L;
		Long mNumGrossChangeAddress = 18L;
		Long mNumGrossMailBlock = 19L;
		Long mNumGrossMailRestriction = 20L;
		Long mNumGrossOther = 21L;
		Long mNumConversions = 22L;
		Long mNumConversionAmount = 23L;
		Long mNumBounceHardFwd = 24L;
		Long mNumBounceSoftFwd = 25L;
		Long mNumConversionAmountFwd = 26L;
		Long mNumAttachOpenFwd = 27L;
		Long mNumClickFwd = 28L;
		Long mNumUniqueForwardFwd = 29L;
		Long mNumGrossForwardFwd = 30L;
		Long mNumUniqueConversionsFwd = 31L;
		Long mNumGrossConversionsFwd = 32L;
		Long mNumUniqueClickstreamFwd = 33L;
		Long mNumGrossClickstreamFwd = 34L;
		Long mNumUniqueClickFwd = 35L;
		Long mNumGrossClickFwd = 36L;
		Long mNumUniqueAttachOpenFwd = 37L;
		Long mNumGrossAttachOpenFwd = 38L;
		Long mNumUniqueMediaFwd = 39L;
		Long mNumGrossMediaFwd = 40L;
		Long mNumUniqueOpenFwd = 41L;
		Long mNumGrossOpenFwd = 42L;
		Long mNumAbuseFwd = 43L;
		Long mNumChangeAddressFwd = 44L;
		Long mNumMailRestrictionFwd = 45L;
		Long mNumMailBlockFwd = 46L;
		Long mNumOtherFwd = 47L;
		Long mNumSuppressedFwd = 48L;
		Long mNumUnsubscribes = 49L;

		Long tMailingId = 478360L;
		Long tReportId = 1052022258L;
		String tDomain = "ttest.com";
		Long tSent = 300L;
		Long tBounce = 301L;
		Long tOpen = 302L;
		Long tClick = 303L;
		Long tUnsubscribe = 304L;
		Long tConversion = 305L;
		Long tConversion_amount = 306L;
		Long tReply_abuse = 307L;
		Long tReply_mail_block = 308L;
		Long tReply_mail_restriction = 309L;

		String iDomain = "itest.com";
		Long iMailingId = 100L;
		Long iBulk = 101L;
		Long iInbox = 102L;
		Long iNotReceived = 103L;
		Long iSent = 104L;
		Long iReportId = 105L;

		String cLinkName = "test link";
		String cLinkURL = "github.com";
		Long cMailingId = 200L;
		Long cTotalAOL = 201L;
		Long cTotalHTML = 202L;
		Long cTotalTEXT = 203L;
		Long cTotalWEB = 204L;
		Long cReportId = 205L;

		String envelope = "<RESULT><SUCCESS>TRUE</SUCCESS><Mailing><MailingId>" + mMailingId + "</MailingId><ReportId>"
				+ mReportId + "</ReportId><MailingName>" + mMailingName + "</MailingName><SentDateTime>" + mSentDateTime
				+ "</SentDateTime><NumSent>" + mNumSent + "</NumSent><NumSeeds>" + mNumSeeds
				+ "</NumSeeds><NumSuppressed>" + mNumSuppressed + "</NumSuppressed><NumInboxMonitored>"
				+ mNumInboxMonitored + "</NumInboxMonitored><NumBounceHard>" + mNumBounceHard
				+ "</NumBounceHard><NumBounceSoft>" + mNumBounceSoft + "</NumBounceSoft><NumUniqueOpen>"
				+ mNumUniqueOpen + "</NumUniqueOpen><NumGrossOpen>" + mNumGrossOpen + "</NumGrossOpen><NumUniqueClick>"
				+ mNumUniqueClick + "</NumUniqueClick><NumGrossClick>" + mNumGrossClick
				+ "</NumGrossClick><NumUniqueAttach>" + mNumUniqueAttach + "</NumUniqueAttach><NumGrossAttach>"
				+ mNumGrossAttach + "</NumGrossAttach><NumUniqueClickstreams>" + mNumUniqueClickstreams
				+ "</NumUniqueClickstreams><NumGrossClickstreams>" + mNumGrossClickstreams
				+ "</NumGrossClickstreams><NumUniqueMedia>" + mNumUniqueMedia + "</NumUniqueMedia><NumGrossMedia>"
				+ mNumGrossMedia + "</NumGrossMedia><NumGrossAbuse>" + mNumGrossAbuse
				+ "</NumGrossAbuse><NumGrossChangeAddress>" + mNumGrossChangeAddress
				+ "</NumGrossChangeAddress><NumGrossMailBlock>" + mNumGrossMailBlock
				+ "</NumGrossMailBlock><NumGrossMailRestriction>" + mNumGrossMailRestriction
				+ "</NumGrossMailRestriction><NumGrossOther>" + mNumGrossOther + "</NumGrossOther><NumConversions>"
				+ mNumConversions + "</NumConversions><NumConversionAmount>" + mNumConversionAmount
				+ "</NumConversionAmount><NumBounceHardFwd>" + mNumBounceHardFwd
				+ "</NumBounceHardFwd><NumBounceSoftFwd>" + mNumBounceSoftFwd
				+ "</NumBounceSoftFwd><NumConversionAmountFwd>" + mNumConversionAmountFwd
				+ "</NumConversionAmountFwd><NumAttachOpenFwd>" + mNumAttachOpenFwd + "</NumAttachOpenFwd><NumClickFwd>"
				+ mNumClickFwd + "</NumClickFwd><NumUniqueForwardFwd>" + mNumUniqueForwardFwd
				+ "</NumUniqueForwardFwd><NumGrossForwardFwd>" + mNumGrossForwardFwd
				+ "</NumGrossForwardFwd><NumUniqueConversionsFwd>" + mNumUniqueConversionsFwd
				+ "</NumUniqueConversionsFwd><NumGrossConversionsFwd>" + mNumGrossConversionsFwd
				+ "</NumGrossConversionsFwd><NumUniqueClickstreamFwd>" + mNumUniqueClickstreamFwd
				+ "</NumUniqueClickstreamFwd><NumGrossClickstreamFwd>" + mNumGrossClickstreamFwd
				+ "</NumGrossClickstreamFwd><NumUniqueClickFwd>" + mNumUniqueClickFwd
				+ "</NumUniqueClickFwd><NumGrossClickFwd>" + mNumGrossClickFwd
				+ "</NumGrossClickFwd><NumUniqueAttachOpenFwd>" + mNumUniqueAttachOpenFwd
				+ "</NumUniqueAttachOpenFwd><NumGrossAttachOpenFwd>" + mNumGrossAttachOpenFwd
				+ "</NumGrossAttachOpenFwd><NumUniqueMediaFwd>" + mNumUniqueMediaFwd
				+ "</NumUniqueMediaFwd><NumGrossMediaFwd>" + mNumGrossMediaFwd + "</NumGrossMediaFwd><NumUniqueOpenFwd>"
				+ mNumUniqueOpenFwd + "</NumUniqueOpenFwd><NumGrossOpenFwd>" + mNumGrossOpenFwd
				+ "</NumGrossOpenFwd><NumAbuseFwd>" + mNumAbuseFwd + "</NumAbuseFwd><NumChangeAddressFwd>"
				+ mNumChangeAddressFwd + "</NumChangeAddressFwd><NumMailRestrictionFwd>" + mNumMailRestrictionFwd
				+ "</NumMailRestrictionFwd><NumMailBlockFwd>" + mNumMailBlockFwd + "</NumMailBlockFwd><NumOtherFwd>"
				+ mNumOtherFwd + "</NumOtherFwd><NumSuppressedFwd>" + mNumSuppressedFwd
				+ "</NumSuppressedFwd><NumUnsubscribes>" + mNumUnsubscribes
				+ "</NumUnsubscribes></Mailing><TopDomains><TopDomain><MailingId>" + tMailingId
				+ "</MailingId><ReportId>" + tReportId + "</ReportId><Domain>" + tDomain + "</Domain><Sent>" + tSent
				+ "</Sent><Bounce>" + tBounce + "</Bounce><Open>" + tOpen + "</Open><Click>" + tClick
				+ "</Click><Unsubscribe>" + tUnsubscribe + "</Unsubscribe><Conversion>" + tConversion
				+ "</Conversion><Conversion_amount>" + tConversion_amount + "</Conversion_amount><Reply_abuse>"
				+ tReply_abuse + "</Reply_abuse><Reply_mail_block>" + tReply_mail_block
				+ "</Reply_mail_block><Reply_mail_restriction>" + tReply_mail_restriction
				+ "</Reply_mail_restriction></TopDomain></TopDomains><InboxMonitored><InboxMonitoring><Domain>"
				+ iDomain + "</Domain><MailingId>" + iMailingId + "</MailingId><Bulk>" + iBulk + "</Bulk><Inbox>"
				+ iInbox + "</Inbox><NotReceived>" + iNotReceived + "</NotReceived><Sent>" + iSent + "</Sent><ReportId>"
				+ iReportId + "</ReportId></InboxMonitoring></InboxMonitored><Clicks><Click><LinkName>" + cLinkName
				+ "</LinkName><LinkURL>" + cLinkURL + "</LinkURL><MailingId>" + cMailingId + "</MailingId><TotalAOL>"
				+ cTotalAOL + "</TotalAOL><TotalHTML>" + cTotalHTML + "</TotalHTML><TotalTEXT>" + cTotalTEXT
				+ "</TotalTEXT><TotalWEB>" + cTotalWEB + "</TotalWEB><ReportId>" + cReportId
				+ "</ReportId></Click></Clicks></RESULT>";

		Element resultNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new ByteArrayInputStream(envelope.getBytes())).getDocumentElement();

		ResponseContainer<GetAggregateTrackingForMailingResponse> responseContainer = command.readResponse(resultNode,
				options);
		GetAggregateTrackingForMailingResponse response = responseContainer.getResposne();

		assertEquals(response.getClicks().size(), 1);
		assertEquals(response.getClicks().get(0).getLinkName(), cLinkName);
		assertEquals(response.getClicks().get(0).getLinkUrl(), cLinkURL);
		assertEquals(response.getClicks().get(0).getMailingId(), cMailingId);
		assertEquals(response.getClicks().get(0).getNumTotalAol(), cTotalAOL);
		assertEquals(response.getClicks().get(0).getNumTotalHtml(), cTotalHTML);
		assertEquals(response.getClicks().get(0).getNumTotalText(), cTotalTEXT);
		assertEquals(response.getClicks().get(0).getNumTotalWeb(), cTotalWEB);
		assertEquals(response.getClicks().get(0).getReportId(), cReportId);

		assertEquals(response.getInboxMonitorings().size(), 1);
		assertEquals(response.getInboxMonitorings().get(0).getDomain(), iDomain);
		assertEquals(response.getInboxMonitorings().get(0).getMailingId(), iMailingId);
		assertEquals(response.getInboxMonitorings().get(0).getNumBulk(), iBulk);
		assertEquals(response.getInboxMonitorings().get(0).getNumInbox(), iInbox);
		assertEquals(response.getInboxMonitorings().get(0).getNumNotReceived(), iNotReceived);
		assertEquals(response.getInboxMonitorings().get(0).getNumSent(), iSent);
		assertEquals(response.getInboxMonitorings().get(0).getReportId(), iReportId);

		assertEquals(response.getMailings().size(), 1);
		assertEquals(response.getMailings().get(0).getMailingId(), mMailingId);
		assertEquals(response.getMailings().get(0).getMailingName(), mMailingName);
		assertEquals(response.getMailings().get(0).getNumAbuseFwd(), mNumAbuseFwd);
		assertEquals(response.getMailings().get(0).getNumAttachOpenFwd(), mNumAttachOpenFwd);
		assertEquals(response.getMailings().get(0).getNumBounceHard(), mNumBounceHard);
		assertEquals(response.getMailings().get(0).getNumBounceHardFwd(), mNumBounceHardFwd);
		assertEquals(response.getMailings().get(0).getNumBounceSoft(), mNumBounceSoft);
		assertEquals(response.getMailings().get(0).getNumBounceSoftFwd(), mNumBounceSoftFwd);
		assertEquals(response.getMailings().get(0).getNumChangeAddressFwd(), mNumChangeAddressFwd);
		assertEquals(response.getMailings().get(0).getNumClickFwd(), mNumClickFwd);
		assertEquals(response.getMailings().get(0).getNumConversionAmount(), mNumConversionAmount);
		assertEquals(response.getMailings().get(0).getNumConversionAmountFwd(), mNumConversionAmountFwd);
		assertEquals(response.getMailings().get(0).getNumConversions(), mNumConversions);
		assertEquals(response.getMailings().get(0).getNumGrossAbuse(), mNumGrossAbuse);
		assertEquals(response.getMailings().get(0).getNumGrossAttach(), mNumGrossAttach);
		assertEquals(response.getMailings().get(0).getNumGrossAttachOpenFwd(), mNumGrossAttachOpenFwd);
		assertEquals(response.getMailings().get(0).getNumGrossChangeAddress(), mNumGrossChangeAddress);
		assertEquals(response.getMailings().get(0).getNumGrossClick(), mNumGrossClick);
		assertEquals(response.getMailings().get(0).getNumGrossClickFwd(), mNumGrossClickFwd);
		assertEquals(response.getMailings().get(0).getNumGrossClickstreamFwd(), mNumGrossClickstreamFwd);
		assertEquals(response.getMailings().get(0).getNumGrossClickstreams(), mNumGrossClickstreams);
		assertEquals(response.getMailings().get(0).getNumGrossConversionsFwd(), mNumGrossConversionsFwd);
		assertEquals(response.getMailings().get(0).getNumGrossForwardFwd(), mNumGrossForwardFwd);
		assertEquals(response.getMailings().get(0).getNumGrossMailBlock(), mNumGrossMailBlock);
		assertEquals(response.getMailings().get(0).getNumGrossMailRestriction(), mNumGrossMailRestriction);
		assertEquals(response.getMailings().get(0).getNumGrossMedia(), mNumGrossMedia);
		assertEquals(response.getMailings().get(0).getNumGrossMediaFwd(), mNumGrossMediaFwd);
		assertEquals(response.getMailings().get(0).getNumGrossOpen(), mNumGrossOpen);
		assertEquals(response.getMailings().get(0).getNumGrossOpenFwd(), mNumGrossOpenFwd);
		assertEquals(response.getMailings().get(0).getNumGrossOther(), mNumGrossOther);
		assertEquals(response.getMailings().get(0).getNumInboxMonitored(), mNumInboxMonitored);
		assertEquals(response.getMailings().get(0).getNumMailBlockFwd(), mNumMailBlockFwd);
		assertEquals(response.getMailings().get(0).getNumMailRestrictionFwd(), mNumMailRestrictionFwd);
		assertEquals(response.getMailings().get(0).getNumOtherFwd(), mNumOtherFwd);
		assertEquals(response.getMailings().get(0).getNumSeeds(), mNumSeeds);
		assertEquals(response.getMailings().get(0).getNumSent(), mNumSent);
		assertEquals(response.getMailings().get(0).getNumSuppressed(), mNumSuppressed);
		assertEquals(response.getMailings().get(0).getNumSuppressedFwd(), mNumSuppressedFwd);
		assertEquals(response.getMailings().get(0).getNumUniqueAttach(), mNumUniqueAttach);
		assertEquals(response.getMailings().get(0).getNumUniqueAttachOpenFwd(), mNumUniqueAttachOpenFwd);
		assertEquals(response.getMailings().get(0).getNumUniqueClick(), mNumUniqueClick);
		assertEquals(response.getMailings().get(0).getNumUniqueClickFwd(), mNumUniqueClickFwd);
		assertEquals(response.getMailings().get(0).getNumUniqueClickstreamFwd(), mNumUniqueClickstreamFwd);
		assertEquals(response.getMailings().get(0).getNumUniqueClickstreams(), mNumUniqueClickstreams);
		assertEquals(response.getMailings().get(0).getNumUniqueConversionsFwd(), mNumUniqueConversionsFwd);
		assertEquals(response.getMailings().get(0).getNumUniqueForwardFwd(), mNumUniqueForwardFwd);
		assertEquals(response.getMailings().get(0).getNumUniqueMedia(), mNumUniqueMedia);
		assertEquals(response.getMailings().get(0).getNumUniqueMediaFwd(), mNumUniqueMediaFwd);
		assertEquals(response.getMailings().get(0).getNumUniqueOpen(), mNumUniqueOpen);
		assertEquals(response.getMailings().get(0).getNumUniqueOpenFwd(), mNumUniqueOpenFwd);
		assertEquals(response.getMailings().get(0).getNumUnsubscribes(), mNumUnsubscribes);
		assertEquals(response.getMailings().get(0).getReportId(), mReportId);
		assertEquals(response.getMailings().get(0).getSentDateTime(), LocalDateTime.parse(mSentDateTime, formatter));

		assertEquals(response.getTopDomains().size(), 1);
		assertEquals(response.getTopDomains().get(0).getDomain(), tDomain);
		assertEquals(response.getTopDomains().get(0).getMailingId(), tMailingId);
		assertEquals(response.getTopDomains().get(0).getNumBounce(), tBounce);
		assertEquals(response.getTopDomains().get(0).getNumClick(), tClick);
		assertEquals(response.getTopDomains().get(0).getNumConversion(), tConversion);
		assertEquals(response.getTopDomains().get(0).getNumConversionAmount(), tConversion_amount);
		assertEquals(response.getTopDomains().get(0).getNumOpen(), tOpen);
		assertEquals(response.getTopDomains().get(0).getNumReplyAbuse(), tReply_abuse);
		assertEquals(response.getTopDomains().get(0).getNumReplyMailBlock(), tReply_mail_block);
		assertEquals(response.getTopDomains().get(0).getNumReplyMailRestriction(), tReply_mail_restriction);
		assertEquals(response.getTopDomains().get(0).getNumSent(), tSent);
		assertEquals(response.getTopDomains().get(0).getNumUnsubscribe(), tUnsubscribe);
		assertEquals(response.getTopDomains().get(0).getReportId(), tReportId);
	}

}
