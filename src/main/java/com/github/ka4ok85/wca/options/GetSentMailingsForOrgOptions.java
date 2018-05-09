package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.utils.DateTimeRange;

public class GetSentMailingsForOrgOptions extends AbstractOptions {
	private final DateTimeRange dateTimeRange;
	private Visibility visibility;
	private boolean scheduled;
	private boolean send;
	private boolean sending;
	private boolean optinConfirmation;
	private boolean profileConfirmation;
	private boolean automated;
	private boolean campaignActive;
	private boolean campaignCompleted;
	private boolean campaignCancelled;
	private boolean campaignScrapeTemplate;
	private boolean includeTags;
	private boolean excludeZeroSent;
	private boolean mailingCountOnly;
	private boolean excludeTestMailings;

	public GetSentMailingsForOrgOptions(DateTimeRange dateTimeRange) {
		super();
		if (dateTimeRange == null) {
			throw new RuntimeException("DateTimeRange can not be null");
		}

		this.dateTimeRange = dateTimeRange;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	public boolean isSend() {
		return send;
	}

	public void setSend(boolean send) {
		this.send = send;
	}

	public boolean isSending() {
		return sending;
	}

	public void setSending(boolean sending) {
		this.sending = sending;
	}

	public boolean isOptinConfirmation() {
		return optinConfirmation;
	}

	public void setOptinConfirmation(boolean optinConfirmation) {
		this.optinConfirmation = optinConfirmation;
	}

	public boolean isProfileConfirmation() {
		return profileConfirmation;
	}

	public void setProfileConfirmation(boolean profileConfirmation) {
		this.profileConfirmation = profileConfirmation;
	}

	public boolean isAutomated() {
		return automated;
	}

	public void setAutomated(boolean automated) {
		this.automated = automated;
	}

	public boolean isCampaignActive() {
		return campaignActive;
	}

	public void setCampaignActive(boolean campaignActive) {
		this.campaignActive = campaignActive;
	}

	public boolean isCampaignCompleted() {
		return campaignCompleted;
	}

	public void setCampaignCompleted(boolean campaignCompleted) {
		this.campaignCompleted = campaignCompleted;
	}

	public boolean isCampaignCancelled() {
		return campaignCancelled;
	}

	public void setCampaignCancelled(boolean campaignCancelled) {
		this.campaignCancelled = campaignCancelled;
	}

	public boolean isCampaignScrapeTemplate() {
		return campaignScrapeTemplate;
	}

	public void setCampaignScrapeTemplate(boolean campaignScrapeTemplate) {
		this.campaignScrapeTemplate = campaignScrapeTemplate;
	}

	public boolean isIncludeTags() {
		return includeTags;
	}

	public void setIncludeTags(boolean includeTags) {
		this.includeTags = includeTags;
	}

	public boolean isExcludeZeroSent() {
		return excludeZeroSent;
	}

	public void setExcludeZeroSent(boolean excludeZeroSent) {
		this.excludeZeroSent = excludeZeroSent;
	}

	public boolean isMailingCountOnly() {
		return mailingCountOnly;
	}

	public void setMailingCountOnly(boolean mailingCountOnly) {
		this.mailingCountOnly = mailingCountOnly;
	}

	public boolean isExcludeTestMailings() {
		return excludeTestMailings;
	}

	public void setExcludeTestMailings(boolean excludeTestMailings) {
		this.excludeTestMailings = excludeTestMailings;
	}

	public DateTimeRange getDateTimeRange() {
		return dateTimeRange;
	}

	@Override
	public String toString() {
		return "GetSentMailingsForOrgOptions [dateTimeRange=" + dateTimeRange + ", visibility=" + visibility
				+ ", scheduled=" + scheduled + ", send=" + send + ", sending=" + sending + ", optinConfirmation="
				+ optinConfirmation + ", profileConfirmation=" + profileConfirmation + ", automated=" + automated
				+ ", campaignActive=" + campaignActive + ", campaignCompleted=" + campaignCompleted
				+ ", campaignCancelled=" + campaignCancelled + ", campaignScrapeTemplate=" + campaignScrapeTemplate
				+ ", includeTags=" + includeTags + ", excludeZeroSent=" + excludeZeroSent + ", mailingCountOnly="
				+ mailingCountOnly + ", excludeTestMailings=" + excludeTestMailings + "]";
	}

}
