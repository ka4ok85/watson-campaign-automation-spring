package com.github.ka4ok85.wca.command;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.options.RawRecipientDataExportOptions;
import com.github.ka4ok85.wca.response.RawRecipientDataExportResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

/**
 * <strong>Class for interacting with WCA RawRecipientDataExport API.</strong>
 * It builds XML request for RawRecipientDataExport API using
 * {@link com.github.ka4ok85.wca.options.RawRecipientDataExportOptions} and
 * reads response into
 * {@link com.github.ka4ok85.wca.response.RawRecipientDataExportResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * It relies on parent {@link com.github.ka4ok85.wca.command.AbstractJobCommand}
 * for polling mechanism.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class RawRecipientDataExportCommand
		extends AbstractJobCommand<RawRecipientDataExportResponse, RawRecipientDataExportOptions> {

	private static final String apiMethodName = "RawRecipientDataExport";
	private static final Logger log = LoggerFactory.getLogger(RawRecipientDataExportCommand.class);

	@Autowired
	private RawRecipientDataExportResponse rawRecipientDataExportResponse;

	/**
	 * Builds XML request for RawRecipientDataExport API using
	 * {@link com.github.ka4ok85.wca.options.RawRecipientDataExportOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(RawRecipientDataExportOptions options) {
		Objects.requireNonNull(options, "RawRecipientDataExportOptions must not be null");

		setJobIdPath("MAILING/JOB_ID");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		if (options.getMailingReportId() != null && options.getMailingReportId().size() > 0) {
			for (HashMap<String, Long> mailingReportId : options.getMailingReportId()) {
				Element mailingNode = doc.createElement("MAILING");
				addChildNode(mailingNode, currentNode);

				if (mailingReportId.containsKey("mailingId")) {
					Element mailingIdNode = doc.createElement("MAILING_ID");
					mailingIdNode.setTextContent(mailingReportId.get("mailingId").toString());
					addChildNode(mailingIdNode, mailingNode);
				}

				if (mailingReportId.containsKey("reportId")) {
					Element reportIdNode = doc.createElement("REPORT_ID");
					reportIdNode.setTextContent(mailingReportId.get("reportId").toString());
					addChildNode(reportIdNode, mailingNode);
				}
			}
		}

		if (options.getCampaignId() != null) {
			Element campaignID = doc.createElement("CAMPAIGN_ID");
			campaignID.setTextContent(options.getCampaignId().toString());
			addChildNode(campaignID, currentNode);
		}

		if (options.getListId() != null) {
			Element listID = doc.createElement("LIST_ID");
			listID.setTextContent(options.getListId().toString());
			addChildNode(listID, currentNode);
			if (options.isIncludeChildren()) {
				Element includeChildren = doc.createElement("INCLUDE_CHILDREN");
				addChildNode(includeChildren, currentNode);
			}
		}

		if (options.isAllNonExported()) {
			Element allNonExported = doc.createElement("ALL_NON_EXPORTED");
			addChildNode(allNonExported, currentNode);
		}

		if (options.getEventRange() != null) {
			addParameter(currentNode, "EVENT_DATE_START", options.getEventRange().getFormattedStartDateTime());
			addParameter(currentNode, "EVENT_DATE_END", options.getEventRange().getFormattedEndDateTime());
		}

		if (options.getSendRange() != null) {
			addParameter(currentNode, "SEND_DATE_START", options.getSendRange().getFormattedStartDateTime());
			addParameter(currentNode, "SEND_DATE_END", options.getSendRange().getFormattedEndDateTime());
		}

		Element exportFormat = doc.createElement("EXPORT_FORMAT");
		exportFormat.setTextContent(options.getExportFormat().value());
		addChildNode(exportFormat, currentNode);

		if (options.isReturnFromAddress()) {
			Element returnFromAddress = doc.createElement("RETURN_FROM_ADDRESS");
			addChildNode(returnFromAddress, currentNode);
		}

		if (options.isReturnFromName()) {
			Element returnFromName = doc.createElement("RETURN_FROM_NAME");
			addChildNode(returnFromName, currentNode);
		}

		Element fileEncoding = doc.createElement("FILE_ENCODING");
		fileEncoding.setTextContent(options.getFileEncoding().value());
		addChildNode(fileEncoding, currentNode);

		if (options.getExportFileName() != null) {
			Element exportFileName = doc.createElement("EXPORT_FILE_NAME");
			exportFileName.setTextContent(options.getExportFileName());
			addChildNode(exportFileName, currentNode);
		}

		if (options.isMoveToFtp()) {
			Element moveToFtp = doc.createElement("MOVE_TO_FTP");
			addChildNode(moveToFtp, currentNode);
		}

		if (options.getVisibility() == Visibility.SHARED) {
			addBooleanParameter(currentNode, "SHARED", true);
		} else if (options.getVisibility() == Visibility.PRIVATE) {
			addBooleanParameter(currentNode, "PRIVATE", true);
		}

		if (options.isIncludeSentMailings()) {
			Element includeSentMailings = doc.createElement("SENT_MAILINGS");
			addChildNode(includeSentMailings, currentNode);
		}

		if (options.isIncludeSendingMailings()) {
			Element includeSendingMailings = doc.createElement("SENDING");
			addChildNode(includeSendingMailings, currentNode);
		}

		if (options.isIncludeOptinConfirmationMailings()) {
			Element includeOptinConfirmationMailings = doc.createElement("OPTIN_CONFIRMATION");
			addChildNode(includeOptinConfirmationMailings, currentNode);
		}

		if (options.isIncludeProfileConfirmationMailings()) {
			Element includeProfileConfirmationMailings = doc.createElement("PROFILE_CONFIRMATION");
			addChildNode(includeProfileConfirmationMailings, currentNode);
		}

		if (options.isIncludeAutomatedMailings()) {
			Element includeAutomatedMailings = doc.createElement("AUTOMATED");
			addChildNode(includeAutomatedMailings, currentNode);
		}

		if (options.isIncludeCampaignActiveMailings()) {
			Element includeCampaignActiveMailings = doc.createElement("CAMPAIGN_ACTIVE");
			addChildNode(includeCampaignActiveMailings, currentNode);
		}

		if (options.isIncludeCampaignCompletedMailings()) {
			Element includeCampaignCompletedMailings = doc.createElement("CAMPAIGN_COMPLETED");
			addChildNode(includeCampaignCompletedMailings, currentNode);
		}

		if (options.isIncludeCampaignCancelledMailings()) {
			Element includeCampaignCancelledMailings = doc.createElement("CAMPAIGN_CANCELLED");
			addChildNode(includeCampaignCancelledMailings, currentNode);
		}

		if (options.isIncludeCampaignScrapeTemplateMailings()) {
			Element includeCampaignScrapeTemplateMailings = doc.createElement("CAMPAIGN_SCRAPE_TEMPLATE");
			addChildNode(includeCampaignScrapeTemplateMailings, currentNode);
		}

		if (options.isIncludeTestMailings()) {
			Element includeTestMailings = doc.createElement("INCLUDE_TEST_MAILINGS");
			addChildNode(includeTestMailings, currentNode);
		}

		if (options.isAllEventTypes()) {
			Element allEventTypes = doc.createElement("ALL_EVENT_TYPES");
			addChildNode(allEventTypes, currentNode);
		} else {
			if (options.isEventSent()) {
				Element eventSent = doc.createElement("SENT");
				addChildNode(eventSent, currentNode);
			}
			if (options.isEventSuppressed()) {
				Element eventSuppressed = doc.createElement("SUPPRESSED");
				addChildNode(eventSuppressed, currentNode);
			}
			if (options.isEventOpens()) {
				Element eventOpens = doc.createElement("OPENS");
				addChildNode(eventOpens, currentNode);
			}
			if (options.isEventClicks()) {
				Element eventClicks = doc.createElement("CLICKS");
				addChildNode(eventClicks, currentNode);
			}
			if (options.isEventOptins()) {
				Element eventOptins = doc.createElement("OPTINS");
				addChildNode(eventOptins, currentNode);
			}
			if (options.isEventOptouts()) {
				Element eventOptouts = doc.createElement("OPTOUTS");
				addChildNode(eventOptouts, currentNode);
			}
			if (options.isEventForwards()) {
				Element eventForwards = doc.createElement("FORWARDS");
				addChildNode(eventForwards, currentNode);
			}
			if (options.isEventAttachments()) {
				Element eventAttachments = doc.createElement("ATTACHMENTS");
				addChildNode(eventAttachments, currentNode);
			}
			if (options.isEventConversions()) {
				Element eventConversions = doc.createElement("CONVERSIONS");
				addChildNode(eventConversions, currentNode);
			}
			if (options.isEventClickstreams()) {
				Element eventClickstreams = doc.createElement("CLICKSTREAMS");
				addChildNode(eventClickstreams, currentNode);
			}
			if (options.isEventHardBounces()) {
				Element eventHardBounces = doc.createElement("HARD_BOUNCES");
				addChildNode(eventHardBounces, currentNode);
			}
			if (options.isEventSoftBounces()) {
				Element eventSoftBounces = doc.createElement("SOFT_BOUNCES");
				addChildNode(eventSoftBounces, currentNode);
			}
			if (options.isEventReplyAbuse()) {
				Element eventReplyAbuse = doc.createElement("REPLY_ABUSE");
				addChildNode(eventReplyAbuse, currentNode);
			}
			if (options.isEventReplyCOA()) {
				Element eventReplyCOA = doc.createElement("REPLY_COA");
				addChildNode(eventReplyCOA, currentNode);
			}
			if (options.isEventReplyOther()) {
				Element eventReplyOther = doc.createElement("REPLY_OTHER");
				addChildNode(eventReplyOther, currentNode);
			}
			if (options.isEventMailBlocks()) {
				Element eventMailBlocks = doc.createElement("MAIL_BLOCKS");
				addChildNode(eventMailBlocks, currentNode);
			}
			if (options.isEventMailRestrictions()) {
				Element eventMailRestrictions = doc.createElement("MAILING_RESTRICTIONS");
				addChildNode(eventMailRestrictions, currentNode);
			}
			if (options.isEventSMSError()) {
				Element eventSMSError = doc.createElement("SMS_ERROR");
				addChildNode(eventSMSError, currentNode);
			}
			if (options.isEventSMSReject()) {
				Element eventSMSReject = doc.createElement("SMS_REJECT");
				addChildNode(eventSMSReject, currentNode);
			}
			if (options.isEventSMSOptout()) {
				Element eventSMSOptout = doc.createElement("SMS_OPTOUT");
				addChildNode(eventSMSOptout, currentNode);
			}
		}

		if (options.isIncludeSeeds()) {
			Element includeSeeds = doc.createElement("INCLUDE_SEEDS");
			addChildNode(includeSeeds, currentNode);
		}

		if (options.isIncludeForwards()) {
			Element includeForwards = doc.createElement("INCLUDE_FORWARDS");
			addChildNode(includeForwards, currentNode);
		}

		if (options.isIncludeInboxMonitoring()) {
			Element includeInboxMonitoring = doc.createElement("INCLUDE_INBOX_MONITORING");
			addChildNode(includeInboxMonitoring, currentNode);
		}

		if (options.isCodedTypeFields()) {
			Element codedTypeFields = doc.createElement("CODED_TYPE_FIELDS");
			addChildNode(codedTypeFields, currentNode);
		}

		if (options.isExcludeDeleted()) {
			Element excludeDeleted = doc.createElement("EXCLUDE_DELETED");
			addChildNode(excludeDeleted, currentNode);
		}

		if (options.isIncludeForwardsOnly()) {
			Element includeForwardsOnly = doc.createElement("FORWARDS_ONLY");
			addChildNode(includeForwardsOnly, currentNode);
		}

		if (options.isReturnMailingName()) {
			Element returnMailingName = doc.createElement("RETURN_MAILING_NAME");
			addChildNode(returnMailingName, currentNode);
		}

		if (options.isReturnMailingSubject()) {
			Element returnMailingSubject = doc.createElement("RETURN_SUBJECT");
			addChildNode(returnMailingSubject, currentNode);
		}

		if (options.isReturnCRMCampaignId()) {
			Element returnCRMCampaignId = doc.createElement("RETURN_CRM_CAMPAIGN_ID");
			addChildNode(returnCRMCampaignId, currentNode);
		}

		if (options.isReturnProgramId()) {
			Element returnProgramId = doc.createElement("RETURN_PROGRAM_ID");
			addChildNode(returnProgramId, currentNode);
		}

	}

	/**
	 * Reads RawRecipientDataExport API response into
	 * {@link com.github.ka4ok85.wca.response.RawRecipientDataExportResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for JobResponse API
	 * @param options
	 *            - settings for API call
	 * @return POJO RawRecipientDataExport Response
	 */
	@Override
	public ResponseContainer<RawRecipientDataExportResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, RawRecipientDataExportOptions options) {

		ResponseContainer<RawRecipientDataExportResponse> response = new ResponseContainer<RawRecipientDataExportResponse>(
				rawRecipientDataExportResponse);

		return response;
	}

}
