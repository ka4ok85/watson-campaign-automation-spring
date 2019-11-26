package com.github.ka4ok85.wca.options;

import java.util.HashMap;
import java.util.List;

import com.github.ka4ok85.wca.constants.ExportFormat;
import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.utils.DateTimeRange;

public class RawRecipientDataExportOptions extends AbstractOptions {

	private List<HashMap<String, Long>> mailingReportId;
	private Long campaignId;
	private Long listId;
	private boolean includeChildren = false;
	private boolean allNonExported = false;
	private DateTimeRange eventRange;
	private DateTimeRange sendRange;
	private ExportFormat exportFormat = ExportFormat.CSV;
	private FileEncoding fileEncoding = FileEncoding.UTF_8;
	private boolean returnFromAddress = false;
	private boolean returnFromName = false;
	private String exportFileName;
	private boolean moveToFtp = true;
	private Visibility visibility;

	private boolean includeSentMailings = true;
	private boolean includeSendingMailings = false;
	private boolean includeOptinConfirmationMailings = false;
	private boolean includeProfileConfirmationMailings = false;
	private boolean includeAutomatedMailings = false;
	private boolean includeCampaignActiveMailings = false;
	private boolean includeCampaignCompletedMailings = false;
	private boolean includeCampaignCancelledMailings = false;
	private boolean includeCampaignScrapeTemplateMailings = false;
	private boolean includeTestMailings = false;

	private boolean allEventTypes = true;
	private boolean eventSent = false;
	private boolean eventSuppressed = false;
	private boolean eventOpens = false;
	private boolean eventClicks = false;
	private boolean eventOptins = false;
	private boolean eventOptouts = false;
	private boolean eventForwards = false;
	private boolean eventAttachments = false;
	private boolean eventConversions = false;
	private boolean eventClickstreams = false;
	private boolean eventHardBounces = false;
	private boolean eventSoftBounces = false;
	private boolean eventReplyAbuse = false;
	private boolean eventReplyCOA = false;
	private boolean eventReplyOther = false;
	private boolean eventMailBlocks = false;
	private boolean eventMailRestrictions = false;
	private boolean eventSMSError = false;
	private boolean eventSMSReject = false;
	private boolean eventSMSOptout = false;

	private boolean includeSeeds = false;
	private boolean includeForwards = false;
	private boolean includeInboxMonitoring = false;
	private boolean codedTypeFields = false;
	private boolean excludeDeleted = false;
	private boolean includeForwardsOnly = false;
	private boolean returnMailingName = false;
	private boolean returnMailingSubject = false;
	private boolean returnCRMCampaignId = false;
	private boolean returnProgramId = false;

	private List<String> columns;
	private String localAbsoluteFilePath;

	public List<HashMap<String, Long>> getMailingReportId() {
		return mailingReportId;
	}

	/**
	 * Setter for mailingReportId
	 * {@link com.github.ka4ok85.wca.response.ExportListResponse}
	 * 
	 * @param mailingReportId
	 *            - List of mailingId/reportId pairs Each element is HashMap
	 *            with "mailingId" and "reportId" keys. Both keys are not
	 *            required.
	 * 
	 */
	public void setMailingReportId(List<HashMap<String, Long>> mailingReportId) {
		if (campaignId != null || listId != null) {
			throw new RuntimeException("You can't specify MailingId if you already specified Campaign or List");
		}

		if (mailingReportId == null || mailingReportId.size() < 1) {
			throw new RuntimeException("You must provide at least one MailingId.");
		}

		this.mailingReportId = mailingReportId;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		if ((mailingReportId != null && !mailingReportId.isEmpty()) || listId != null) {
			throw new RuntimeException("You can't specify CampaignId if you already specified Mailings or List");
		}

		if (campaignId == null || campaignId < 1) {
			throw new RuntimeException("Campaign ID must be greater than zero. Provided Campaign ID = " + campaignId);
		}
		this.campaignId = campaignId;
	}

	public Long getListId() {

		return listId;
	}

	public void setListId(Long listId) {
		if ((mailingReportId != null && !mailingReportId.isEmpty()) || campaignId != null) {
			throw new RuntimeException("You can't specify ListId if you already specified Mailings or Campaigns");
		}

		if (listId == null || listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
	}

	public boolean isIncludeChildren() {
		return includeChildren;
	}

	public void setIncludeChildren(boolean includeChildren) {
		this.includeChildren = includeChildren;
	}

	public boolean isAllNonExported() {
		return allNonExported;
	}

	public void setAllNonExported(boolean allNonExported) {
		this.allNonExported = allNonExported;
	}

	public DateTimeRange getEventRange() {
		return eventRange;
	}

	public void setEventRange(DateTimeRange eventRange) {
		if (eventRange == null) {
			throw new RuntimeException("EventRange can not be null");
		}
		this.eventRange = eventRange;
	}

	public DateTimeRange getSendRange() {
		return sendRange;
	}

	public void setSendRange(DateTimeRange sendRange) {
		if (sendRange == null) {
			throw new RuntimeException("SendRange can not be null");
		}
		this.sendRange = sendRange;
	}

	public ExportFormat getExportFormat() {
		return exportFormat;
	}

	public void setExportFormat(ExportFormat exportFormat) {
		if (exportFormat == null) {
			throw new RuntimeException("ExportFormat can not be null");
		}

		this.exportFormat = exportFormat;
	}

	public FileEncoding getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(FileEncoding fileEncoding) {
		if (fileEncoding == null) {
			throw new RuntimeException("FileEncoding can not be null");
		}
		this.fileEncoding = fileEncoding;
	}

	public boolean isReturnFromAddress() {
		return returnFromAddress;
	}

	public void setReturnFromAddress(boolean returnFromAddress) {
		this.returnFromAddress = returnFromAddress;
	}

	public boolean isReturnFromName() {
		return returnFromName;
	}

	public void setReturnFromName(boolean returnFromName) {
		this.returnFromName = returnFromName;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setExportFileName(String exportFileName) {
		if (exportFileName == null || exportFileName.trim().isEmpty()) {
			throw new RuntimeException(
					"Export File Name must be non-empty String. Provided Export File Name = " + exportFileName);
		}
		this.exportFileName = exportFileName;
	}

	public boolean isMoveToFtp() {
		return moveToFtp;
	}

	public void setMoveToFtp(boolean moveToFtp) {
		this.moveToFtp = moveToFtp;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		if (visibility == null) {
			throw new RuntimeException("Visibility can not be null");
		}
		this.visibility = visibility;
	}

	public boolean isIncludeSentMailings() {
		return includeSentMailings;
	}

	public void setIncludeSentMailings(boolean includeSentMailings) {
		this.includeSentMailings = includeSentMailings;
	}

	public boolean isIncludeSendingMailings() {
		return includeSendingMailings;
	}

	public void setIncludeSendingMailings(boolean includeSendingMailings) {
		this.includeSendingMailings = includeSendingMailings;
	}

	public boolean isIncludeOptinConfirmationMailings() {
		return includeOptinConfirmationMailings;
	}

	public void setIncludeOptinConfirmationMailings(boolean includeOptinConfirmationMailings) {
		this.includeOptinConfirmationMailings = includeOptinConfirmationMailings;
	}

	public boolean isIncludeProfileConfirmationMailings() {
		return includeProfileConfirmationMailings;
	}

	public void setIncludeProfileConfirmationMailings(boolean includeProfileConfirmationMailings) {
		this.includeProfileConfirmationMailings = includeProfileConfirmationMailings;
	}

	public boolean isIncludeAutomatedMailings() {
		return includeAutomatedMailings;
	}

	public void setIncludeAutomatedMailings(boolean includeAutomatedMailings) {
		this.includeAutomatedMailings = includeAutomatedMailings;
	}

	public boolean isIncludeCampaignActiveMailings() {
		return includeCampaignActiveMailings;
	}

	public void setIncludeCampaignActiveMailings(boolean includeCampaignActiveMailings) {
		this.includeCampaignActiveMailings = includeCampaignActiveMailings;
	}

	public boolean isIncludeCampaignCompletedMailings() {
		return includeCampaignCompletedMailings;
	}

	public void setIncludeCampaignCompletedMailings(boolean includeCampaignCompletedMailings) {
		this.includeCampaignCompletedMailings = includeCampaignCompletedMailings;
	}

	public boolean isIncludeCampaignCancelledMailings() {
		return includeCampaignCancelledMailings;
	}

	public void setIncludeCampaignCancelledMailings(boolean includeCampaignCancelledMailings) {
		this.includeCampaignCancelledMailings = includeCampaignCancelledMailings;
	}

	public boolean isIncludeCampaignScrapeTemplateMailings() {
		return includeCampaignScrapeTemplateMailings;
	}

	public void setIncludeCampaignScrapeTemplateMailings(boolean includeCampaignScrapeTemplateMailings) {
		this.includeCampaignScrapeTemplateMailings = includeCampaignScrapeTemplateMailings;
	}

	public boolean isIncludeTestMailings() {
		return includeTestMailings;
	}

	public void setIncludeTestMailings(boolean includeTestMailings) {
		this.includeTestMailings = includeTestMailings;
	}

	public boolean isAllEventTypes() {
		return allEventTypes;
	}

	public void setAllEventTypes(boolean allEventTypes) {
		this.allEventTypes = allEventTypes;
	}

	public boolean isEventSent() {
		return eventSent;
	}

	public void setEventSent(boolean eventSent) {
		if (eventSent == true) {
			allEventTypes = false;
		}

		this.eventSent = eventSent;
	}

	public boolean isEventSuppressed() {
		return eventSuppressed;
	}

	public void setEventSuppressed(boolean eventSuppressed) {
		if (eventSuppressed == true) {
			allEventTypes = false;
		}
		this.eventSuppressed = eventSuppressed;
	}

	public boolean isEventOpens() {
		return eventOpens;
	}

	public void setEventOpens(boolean eventOpens) {
		if (eventOpens == true) {
			allEventTypes = false;
		}
		this.eventOpens = eventOpens;
	}

	public boolean isEventClicks() {
		return eventClicks;
	}

	public void setEventClicks(boolean eventClicks) {
		if (eventClicks == true) {
			allEventTypes = false;
		}
		this.eventClicks = eventClicks;
	}

	public boolean isEventOptins() {
		return eventOptins;
	}

	public void setEventOptins(boolean eventOptins) {
		if (eventOptins == true) {
			allEventTypes = false;
		}
		this.eventOptins = eventOptins;
	}

	public boolean isEventOptouts() {
		return eventOptouts;
	}

	public void setEventOptouts(boolean eventOptouts) {
		if (eventOptouts == true) {
			allEventTypes = false;
		}
		this.eventOptouts = eventOptouts;
	}

	public boolean isEventForwards() {
		return eventForwards;
	}

	public void setEventForwards(boolean eventForwards) {
		if (eventForwards == true) {
			allEventTypes = false;
		}
		this.eventForwards = eventForwards;
	}

	public boolean isEventAttachments() {
		return eventAttachments;
	}

	public void setEventAttachments(boolean eventAttachments) {
		if (eventAttachments == true) {
			allEventTypes = false;
		}
		this.eventAttachments = eventAttachments;
	}

	public boolean isEventConversions() {
		return eventConversions;
	}

	public void setEventConversions(boolean eventConversions) {
		if (eventConversions == true) {
			allEventTypes = false;
		}
		this.eventConversions = eventConversions;
	}

	public boolean isEventClickstreams() {
		return eventClickstreams;
	}

	public void setEventClickstreams(boolean eventClickstreams) {
		if (eventClickstreams == true) {
			allEventTypes = false;
		}
		this.eventClickstreams = eventClickstreams;
	}

	public boolean isEventHardBounces() {
		return eventHardBounces;
	}

	public void setEventHardBounces(boolean eventHardBounces) {
		if (eventHardBounces == true) {
			allEventTypes = false;
		}
		this.eventHardBounces = eventHardBounces;
	}

	public boolean isEventSoftBounces() {
		return eventSoftBounces;
	}

	public void setEventSoftBounces(boolean eventSoftBounces) {
		if (eventSoftBounces == true) {
			allEventTypes = false;
		}
		this.eventSoftBounces = eventSoftBounces;
	}

	public boolean isEventReplyAbuse() {
		return eventReplyAbuse;
	}

	public void setEventReplyAbuse(boolean eventReplyAbuse) {
		if (eventReplyAbuse == true) {
			allEventTypes = false;
		}
		this.eventReplyAbuse = eventReplyAbuse;
	}

	public boolean isEventReplyCOA() {
		return eventReplyCOA;
	}

	public void setEventReplyCOA(boolean eventReplyCOA) {
		if (eventReplyCOA == true) {
			allEventTypes = false;
		}
		this.eventReplyCOA = eventReplyCOA;
	}

	public boolean isEventReplyOther() {
		return eventReplyOther;
	}

	public void setEventReplyOther(boolean eventReplyOther) {
		if (eventReplyOther == true) {
			allEventTypes = false;
		}
		this.eventReplyOther = eventReplyOther;
	}

	public boolean isEventMailBlocks() {
		return eventMailBlocks;
	}

	public void setEventMailBlocks(boolean eventMailBlocks) {
		if (eventMailBlocks == true) {
			allEventTypes = false;
		}
		this.eventMailBlocks = eventMailBlocks;
	}

	public boolean isEventMailRestrictions() {
		return eventMailRestrictions;
	}

	public void setEventMailRestrictions(boolean eventMailRestrictions) {
		if (eventMailRestrictions == true) {
			allEventTypes = false;
		}
		this.eventMailRestrictions = eventMailRestrictions;
	}

	public boolean isEventSMSError() {
		return eventSMSError;
	}

	public void setEventSMSError(boolean eventSMSError) {
		if (eventSMSError == true) {
			allEventTypes = false;
		}
		this.eventSMSError = eventSMSError;
	}

	public boolean isEventSMSReject() {
		return eventSMSReject;
	}

	public void setEventSMSReject(boolean eventSMSReject) {
		if (eventSMSReject == true) {
			allEventTypes = false;
		}
		this.eventSMSReject = eventSMSReject;
	}

	public boolean isEventSMSOptout() {
		return eventSMSOptout;
	}

	public void setEventSMSOptout(boolean eventSMSOptout) {
		if (eventSMSOptout == true) {
			allEventTypes = false;
		}
		this.eventSMSOptout = eventSMSOptout;
	}

	public boolean isIncludeSeeds() {
		return includeSeeds;
	}

	public void setIncludeSeeds(boolean includeSeeds) {
		this.includeSeeds = includeSeeds;
	}

	public boolean isIncludeForwards() {
		return includeForwards;
	}

	public void setIncludeForwards(boolean includeForwards) {
		this.includeForwards = includeForwards;
	}

	public boolean isIncludeInboxMonitoring() {
		return includeInboxMonitoring;
	}

	public void setIncludeInboxMonitoring(boolean includeInboxMonitoring) {
		this.includeInboxMonitoring = includeInboxMonitoring;
	}

	public boolean isCodedTypeFields() {
		return codedTypeFields;
	}

	public void setCodedTypeFields(boolean codedTypeFields) {
		this.codedTypeFields = codedTypeFields;
	}

	public boolean isExcludeDeleted() {
		return excludeDeleted;
	}

	public void setExcludeDeleted(boolean excludeDeleted) {
		this.excludeDeleted = excludeDeleted;
	}

	public boolean isIncludeForwardsOnly() {
		return includeForwardsOnly;
	}

	public void setIncludeForwardsOnly(boolean includeForwardsOnly) {
		this.includeForwardsOnly = includeForwardsOnly;
	}

	public boolean isReturnMailingName() {
		return returnMailingName;
	}

	public void setReturnMailingName(boolean returnMailingName) {
		this.returnMailingName = returnMailingName;
	}

	public boolean isReturnMailingSubject() {
		return returnMailingSubject;
	}

	public void setReturnMailingSubject(boolean returnMailingSubject) {
		this.returnMailingSubject = returnMailingSubject;
	}

	public boolean isReturnCRMCampaignId() {
		return returnCRMCampaignId;
	}

	public void setReturnCRMCampaignId(boolean returnCRMCampaignId) {
		this.returnCRMCampaignId = returnCRMCampaignId;
	}

	public boolean isReturnProgramId() {
		return returnProgramId;
	}

	public void setReturnProgramId(boolean returnProgramId) {
		this.returnProgramId = returnProgramId;
	}

	public String getLocalAbsoluteFilePath() {
		return localAbsoluteFilePath;
	}

	public void setLocalAbsoluteFilePath(String localAbsoluteFilePath) {
		this.localAbsoluteFilePath = localAbsoluteFilePath;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

}
