package com.github.ka4ok85.wca.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.GetAggregateTrackingForOrgOptions;
import com.github.ka4ok85.wca.response.GetAggregateTrackingForOrgResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataClicks;
import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataInboxMonitoring;
import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataMailing;
import com.github.ka4ok85.wca.response.containers.AggregateTrackingDataTopDomain;
import com.github.ka4ok85.wca.utils.DateTimeRange;

/**
 * <strong>Class for interacting with WCA GetAggregateTrackingForOrg
 * API.</strong> It builds XML request for GetAggregateTrackingForOrg API using
 * {@link com.github.ka4ok85.wca.options.GetAggregateTrackingForOrgOptions} and
 * reads response into
 * {@link com.github.ka4ok85.wca.response.GetAggregateTrackingForOrgResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class GetAggregateTrackingForOrgCommand
		extends AbstractInstantCommand<GetAggregateTrackingForOrgResponse, GetAggregateTrackingForOrgOptions> {

	private static final String apiMethodName = "GetAggregateTrackingForOrg";

	@Autowired
	private GetAggregateTrackingForOrgResponse getAggregateTrackingForOrgResponse;

	/**
	 * Builds XML request for GetAggregateTrackingForOrg API using
	 * {@link com.github.ka4ok85.wca.options.GetAggregateTrackingForOrgOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(GetAggregateTrackingForOrgOptions options) {
		Objects.requireNonNull(options, "GetAggregateTrackingForOrgOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		DateTimeRange dateTimeRange = options.getDateTimeRange();
		addParameter(currentNode, "DATE_START", dateTimeRange.getFormattedStartDateTime());
		addParameter(currentNode, "DATE_END", dateTimeRange.getFormattedEndDateTime());

		if (options.getVisibility() == Visibility.SHARED) {
			addBooleanParameter(currentNode, "SHARED", true);
		} else if (options.getVisibility() == Visibility.PRIVATE) {
			addBooleanParameter(currentNode, "PRIVATE", true);
		}

		if (options.isAutomated()) {
			addBooleanParameter(currentNode, "AUTOMATED", true);
		}

		if (options.isCampaignActive()) {
			addBooleanParameter(currentNode, "CAMPAIGN_ACTIVE", true);
		}

		if (options.isCampaignCancelled()) {
			addBooleanParameter(currentNode, "CAMPAIGN_CANCELLED", true);
		}

		if (options.isCampaignCompleted()) {
			addBooleanParameter(currentNode, "CAMPAIGN_COMPLETED", true);
		}

		if (options.isExcludeTestMailings()) {
			addBooleanParameter(currentNode, "EXCLUDE_TEST_MAILINGS", true);
		}

		if (options.isOptinConfirmation()) {
			addBooleanParameter(currentNode, "OPTIN_CONFIRMATION", true);
		}

		if (options.isProfileConfirmation()) {
			addBooleanParameter(currentNode, "PROFILE_CONFIRMATION", true);
		}

		if (options.isScheduled()) {
			addBooleanParameter(currentNode, "SCHEDULED", true);
		}

		if (options.isSent()) {
			addBooleanParameter(currentNode, "SENT", true);
		}

		if (options.isSending()) {
			addBooleanParameter(currentNode, "SENDING", true);
		}

		if (options.isTopDomain()) {
			addBooleanParameter(currentNode, "TOP_DOMAIN", true);
		}

		if (options.isInboxMonitoring()) {
			addBooleanParameter(currentNode, "INBOX_MONITORING", true);
		}

		if (options.isPerClick()) {
			addBooleanParameter(currentNode, "PER_CLICK", true);
		}
	}

	/**
	 * Reads GetAggregateTrackingForOrg API response into
	 * {@link com.github.ka4ok85.wca.response.GetAggregateTrackingForOrgResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO GetAggregateTrackingForOrg Response
	 */
	@Override
	public ResponseContainer<GetAggregateTrackingForOrgResponse> readResponse(Node resultNode,
			GetAggregateTrackingForOrgOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		List<AggregateTrackingDataClicks> clicks = new ArrayList<AggregateTrackingDataClicks>();
		List<AggregateTrackingDataInboxMonitoring> inboxMonitorings = new ArrayList<AggregateTrackingDataInboxMonitoring>();
		List<AggregateTrackingDataMailing> mailings = new ArrayList<AggregateTrackingDataMailing>();
		List<AggregateTrackingDataTopDomain> topDomains = new ArrayList<AggregateTrackingDataTopDomain>();

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
		try {
			NodeList clicksNode = (NodeList) xpath.evaluate("Clicks/Click", resultNode, XPathConstants.NODESET);
			Node clickNode;
			for (int i = 0; i < clicksNode.getLength(); i++) {
				AggregateTrackingDataClicks click = new AggregateTrackingDataClicks();
				clickNode = clicksNode.item(i);
				click.setLinkName(((Node) xpath.evaluate("LinkName", clickNode, XPathConstants.NODE)).getTextContent());
				click.setLinkUrl(((Node) xpath.evaluate("LinkURL", clickNode, XPathConstants.NODE)).getTextContent());
				click.setMailingId(Long.parseLong(
						((Node) xpath.evaluate("MailingId", clickNode, XPathConstants.NODE)).getTextContent()));
				click.setNumTotalAol(Long.parseLong(
						((Node) xpath.evaluate("TotalAOL", clickNode, XPathConstants.NODE)).getTextContent()));
				click.setNumTotalHtml(Long.parseLong(
						((Node) xpath.evaluate("TotalHTML", clickNode, XPathConstants.NODE)).getTextContent()));
				click.setNumTotalText(Long.parseLong(
						((Node) xpath.evaluate("TotalTEXT", clickNode, XPathConstants.NODE)).getTextContent()));
				click.setNumTotalWeb(Long.parseLong(
						((Node) xpath.evaluate("TotalWEB", clickNode, XPathConstants.NODE)).getTextContent()));
				click.setReportId(Long.parseLong(
						((Node) xpath.evaluate("ReportId", clickNode, XPathConstants.NODE)).getTextContent()));
				clicks.add(click);
			}

			NodeList inboxMonitoringsNode = (NodeList) xpath.evaluate("InboxMonitored/InboxMonitoring", resultNode,
					XPathConstants.NODESET);
			Node inboxMonitoringNode;
			for (int i = 0; i < inboxMonitoringsNode.getLength(); i++) {
				AggregateTrackingDataInboxMonitoring inboxMonitoring = new AggregateTrackingDataInboxMonitoring();
				inboxMonitoringNode = inboxMonitoringsNode.item(i);
				inboxMonitoring.setDomain(
						((Node) xpath.evaluate("Domain", inboxMonitoringNode, XPathConstants.NODE)).getTextContent());
				inboxMonitoring.setMailingId(
						Long.parseLong(((Node) xpath.evaluate("MailingId", inboxMonitoringNode, XPathConstants.NODE))
								.getTextContent()));
				inboxMonitoring.setNumBulk(Long.parseLong(
						((Node) xpath.evaluate("Bulk", inboxMonitoringNode, XPathConstants.NODE)).getTextContent()));
				inboxMonitoring.setNumInbox(Long.parseLong(
						((Node) xpath.evaluate("Inbox", inboxMonitoringNode, XPathConstants.NODE)).getTextContent()));
				inboxMonitoring.setNumNotReceived(
						Long.parseLong(((Node) xpath.evaluate("NotReceived", inboxMonitoringNode, XPathConstants.NODE))
								.getTextContent()));
				inboxMonitoring.setNumSent(Long.parseLong(
						((Node) xpath.evaluate("Sent", inboxMonitoringNode, XPathConstants.NODE)).getTextContent()));
				inboxMonitoring.setReportId(
						Long.parseLong(((Node) xpath.evaluate("ReportId", inboxMonitoringNode, XPathConstants.NODE))
								.getTextContent()));
				inboxMonitorings.add(inboxMonitoring);
			}

			NodeList mailingsNode = (NodeList) xpath.evaluate("Mailing", resultNode, XPathConstants.NODESET);
			Node mailingNode;
			for (int i = 0; i < mailingsNode.getLength(); i++) {
				AggregateTrackingDataMailing mailing = new AggregateTrackingDataMailing();
				mailingNode = mailingsNode.item(i);
				mailing.setMailingId(Long.parseLong(
						((Node) xpath.evaluate("MailingId", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setMailingName(
						((Node) xpath.evaluate("MailingName", mailingNode, XPathConstants.NODE)).getTextContent());
				mailing.setNumAbuseFwd(Long.parseLong(
						((Node) xpath.evaluate("NumAbuseFwd", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumAttachOpenFwd(
						Long.parseLong(((Node) xpath.evaluate("NumAttachOpenFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumBounceHard(Long.parseLong(
						((Node) xpath.evaluate("NumBounceHard", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumBounceHardFwd(
						Long.parseLong(((Node) xpath.evaluate("NumBounceHardFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumBounceSoft(Long.parseLong(
						((Node) xpath.evaluate("NumBounceSoft", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumBounceSoftFwd(
						Long.parseLong(((Node) xpath.evaluate("NumBounceSoftFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumChangeAddressFwd(
						Long.parseLong(((Node) xpath.evaluate("NumChangeAddressFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumClickFwd(Long.parseLong(
						((Node) xpath.evaluate("NumClickFwd", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumConversionAmount(
						Long.parseLong(((Node) xpath.evaluate("NumConversionAmount", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumConversionAmountFwd(Long
						.parseLong(((Node) xpath.evaluate("NumConversionAmountFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumConversions(Long.parseLong(
						((Node) xpath.evaluate("NumConversions", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumGrossAbuse(Long.parseLong(
						((Node) xpath.evaluate("NumGrossAbuse", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumGrossAttach(Long.parseLong(
						((Node) xpath.evaluate("NumGrossAttach", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumGrossAttachOpenFwd(Long
						.parseLong(((Node) xpath.evaluate("NumGrossAttachOpenFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossChangeAddress(Long
						.parseLong(((Node) xpath.evaluate("NumGrossChangeAddress", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossClick(Long.parseLong(
						((Node) xpath.evaluate("NumGrossClick", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumGrossClickFwd(
						Long.parseLong(((Node) xpath.evaluate("NumGrossClickFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossClickstreamFwd(Long
						.parseLong(((Node) xpath.evaluate("NumGrossClickstreamFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossClickstreams(
						Long.parseLong(((Node) xpath.evaluate("NumGrossClickstreams", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossConversionsFwd(Long
						.parseLong(((Node) xpath.evaluate("NumGrossConversionsFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossForwardFwd(
						Long.parseLong(((Node) xpath.evaluate("NumGrossForwardFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossMailBlock(
						Long.parseLong(((Node) xpath.evaluate("NumGrossMailBlock", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossMailRestriction(Long
						.parseLong(((Node) xpath.evaluate("NumGrossMailRestriction", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossMedia(Long.parseLong(
						((Node) xpath.evaluate("NumGrossMedia", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumGrossMediaFwd(
						Long.parseLong(((Node) xpath.evaluate("NumGrossMediaFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumGrossOpen(Long.parseLong(
						((Node) xpath.evaluate("NumGrossOpen", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumGrossOpenFwd(Long.parseLong(
						((Node) xpath.evaluate("NumGrossOpenFwd", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumGrossOther(Long.parseLong(
						((Node) xpath.evaluate("NumGrossOther", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumInboxMonitored(
						Long.parseLong(((Node) xpath.evaluate("NumInboxMonitored", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumMailBlockFwd(Long.parseLong(
						((Node) xpath.evaluate("NumMailBlockFwd", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumMailRestrictionFwd(Long
						.parseLong(((Node) xpath.evaluate("NumMailRestrictionFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumOtherFwd(Long.parseLong(
						((Node) xpath.evaluate("NumOtherFwd", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumSeeds(Long.parseLong(
						((Node) xpath.evaluate("NumSeeds", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumSent(Long.parseLong(
						((Node) xpath.evaluate("NumSent", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumSuppressed(Long.parseLong(
						((Node) xpath.evaluate("NumSuppressed", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumSuppressedFwd(
						Long.parseLong(((Node) xpath.evaluate("NumSuppressedFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueAttach(Long.parseLong(
						((Node) xpath.evaluate("NumUniqueAttach", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumUniqueAttachOpenFwd(Long
						.parseLong(((Node) xpath.evaluate("NumUniqueAttachOpenFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueClick(Long.parseLong(
						((Node) xpath.evaluate("NumUniqueClick", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumUniqueClickFwd(
						Long.parseLong(((Node) xpath.evaluate("NumUniqueClickFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueClickstreamFwd(Long
						.parseLong(((Node) xpath.evaluate("NumUniqueClickstreamFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueClickstreams(Long
						.parseLong(((Node) xpath.evaluate("NumUniqueClickstreams", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueConversionsFwd(Long
						.parseLong(((Node) xpath.evaluate("NumUniqueConversionsFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueForwardFwd(
						Long.parseLong(((Node) xpath.evaluate("NumUniqueForwardFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueMedia(Long.parseLong(
						((Node) xpath.evaluate("NumUniqueMedia", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumUniqueMediaFwd(
						Long.parseLong(((Node) xpath.evaluate("NumUniqueMediaFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUniqueOpen(Long.parseLong(
						((Node) xpath.evaluate("NumUniqueOpen", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setNumUniqueOpenFwd(
						Long.parseLong(((Node) xpath.evaluate("NumUniqueOpenFwd", mailingNode, XPathConstants.NODE))
								.getTextContent()));
				mailing.setNumUnsubscribes(Long.parseLong(
						((Node) xpath.evaluate("NumUnsubscribes", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setReportId(Long.parseLong(
						((Node) xpath.evaluate("ReportId", mailingNode, XPathConstants.NODE)).getTextContent()));
				mailing.setSentDateTime(LocalDateTime.parse(
						((Node) xpath.evaluate("SentDateTime", mailingNode, XPathConstants.NODE)).getTextContent(),
						formatter));
				mailings.add(mailing);
			}

			NodeList topDomainsNode = (NodeList) xpath.evaluate("TopDomains/TopDomain", resultNode,
					XPathConstants.NODESET);
			Node topDomainNode;
			for (int i = 0; i < topDomainsNode.getLength(); i++) {
				AggregateTrackingDataTopDomain topDomain = new AggregateTrackingDataTopDomain();
				topDomainNode = topDomainsNode.item(i);
				topDomain.setDomain(
						((Node) xpath.evaluate("Domain", topDomainNode, XPathConstants.NODE)).getTextContent());
				topDomain.setMailingId(Long.parseLong(
						((Node) xpath.evaluate("MailingId", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setNumBounce(Long.parseLong(
						((Node) xpath.evaluate("Bounce", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setNumClick(Long.parseLong(
						((Node) xpath.evaluate("Click", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setNumConversion(Long.parseLong(
						((Node) xpath.evaluate("Conversion", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setNumConversionAmount(
						Long.parseLong(((Node) xpath.evaluate("Conversion_amount", topDomainNode, XPathConstants.NODE))
								.getTextContent()));
				topDomain.setNumOpen(Long.parseLong(
						((Node) xpath.evaluate("Open", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setNumReplyAbuse(Long.parseLong(
						((Node) xpath.evaluate("Reply_abuse", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setNumReplyMailBlock(
						Long.parseLong(((Node) xpath.evaluate("Reply_mail_block", topDomainNode, XPathConstants.NODE))
								.getTextContent()));
				topDomain.setNumReplyMailRestriction(Long
						.parseLong(((Node) xpath.evaluate("Reply_mail_restriction", topDomainNode, XPathConstants.NODE))
								.getTextContent()));
				topDomain.setNumSent(Long.parseLong(
						((Node) xpath.evaluate("Sent", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setNumUnsubscribe(Long.parseLong(
						((Node) xpath.evaluate("Unsubscribe", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomain.setReportId(Long.parseLong(
						((Node) xpath.evaluate("ReportId", topDomainNode, XPathConstants.NODE)).getTextContent()));
				topDomains.add(topDomain);
			}

			getAggregateTrackingForOrgResponse.setClicks(clicks);
			getAggregateTrackingForOrgResponse.setInboxMonitorings(inboxMonitorings);
			getAggregateTrackingForOrgResponse.setMailings(mailings);
			getAggregateTrackingForOrgResponse.setTopDomains(topDomains);

		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		ResponseContainer<GetAggregateTrackingForOrgResponse> response = new ResponseContainer<GetAggregateTrackingForOrgResponse>(
				getAggregateTrackingForOrgResponse);

		return response;
	}
}
