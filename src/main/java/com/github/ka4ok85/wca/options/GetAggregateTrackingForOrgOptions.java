package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.utils.DateTimeRange;

public class GetAggregateTrackingForOrgOptions extends AbstractOptions {
	private final DateTimeRange dateTimeRange;
	private Visibility visibility;
	private boolean scheduled;
	private boolean sent;
	private boolean sending;
	private boolean optinConfirmation;
	private boolean profileConfirmation;
	private boolean automated;
	private boolean campaignActive;
	private boolean campaignCompleted;
	private boolean campaignCancelled;
	private boolean topDomain;
	private boolean inboxMonitoring;
	private boolean perClick;
	private boolean excludeTestMailings;

	public GetAggregateTrackingForOrgOptions(DateTimeRange dateTimeRange) {
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

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
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

	public boolean isTopDomain() {
		return topDomain;
	}

	public void setTopDomain(boolean topDomain) {
		this.topDomain = topDomain;
	}

	public boolean isInboxMonitoring() {
		return inboxMonitoring;
	}

	public void setInboxMonitoring(boolean inboxMonitoring) {
		this.inboxMonitoring = inboxMonitoring;
	}

	public boolean isPerClick() {
		return perClick;
	}

	public void setPerClick(boolean perClick) {
		this.perClick = perClick;
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
		return "GetAggregateTrackingForOrgOptions [dateTimeRange=" + dateTimeRange + ", visibility=" + visibility
				+ ", scheduled=" + scheduled + ", sent=" + sent + ", sending=" + sending + ", optinConfirmation="
				+ optinConfirmation + ", profileConfirmation=" + profileConfirmation + ", automated=" + automated
				+ ", campaignActive=" + campaignActive + ", campaignCompleted=" + campaignCompleted
				+ ", campaignCancelled=" + campaignCancelled + ", topDomain=" + topDomain + ", inboxMonitoring="
				+ inboxMonitoring + ", perClick=" + perClick + ", excludeTestMailings=" + excludeTestMailings + "]";
	}

}
