package com.github.ka4ok85.wca.response.containers;

import java.time.LocalDateTime;

public class RecipientMailing {
	private String mailingName;
	private Long mailingId;
	private LocalDateTime sentDateTime;
	private Long totalOpens;
	private Long totalClickstreams;
	private Long totalClicks;
	private Long totalConversions;
	private Long totalAttachments;
	private Long totalForwards;
	private Long totalMediaPlays;
	private Long totalBounces;
	private Long totalOptOuts;

	public String getMailingName() {
		return mailingName;
	}

	public void setMailingName(String mailingName) {
		this.mailingName = mailingName;
	}

	public Long getMailingId() {
		return mailingId;
	}

	public void setMailingId(Long mailingId) {
		this.mailingId = mailingId;
	}

	public LocalDateTime getSentDateTime() {
		return sentDateTime;
	}

	public void setSentDateTime(LocalDateTime sentDateTime) {
		this.sentDateTime = sentDateTime;
	}

	public Long getTotalOpens() {
		return totalOpens;
	}

	public void setTotalOpens(Long totalOpens) {
		this.totalOpens = totalOpens;
	}

	public Long getTotalClickstreams() {
		return totalClickstreams;
	}

	public void setTotalClickstreams(Long totalClickstreams) {
		this.totalClickstreams = totalClickstreams;
	}

	public Long getTotalClicks() {
		return totalClicks;
	}

	public void setTotalClicks(Long totalClicks) {
		this.totalClicks = totalClicks;
	}

	public Long getTotalConversions() {
		return totalConversions;
	}

	public void setTotalConversions(Long totalConversions) {
		this.totalConversions = totalConversions;
	}

	public Long getTotalAttachments() {
		return totalAttachments;
	}

	public void setTotalAttachments(Long totalAttachments) {
		this.totalAttachments = totalAttachments;
	}

	public Long getTotalForwards() {
		return totalForwards;
	}

	public void setTotalForwards(Long totalForwards) {
		this.totalForwards = totalForwards;
	}

	public Long getTotalMediaPlays() {
		return totalMediaPlays;
	}

	public void setTotalMediaPlays(Long totalMediaPlays) {
		this.totalMediaPlays = totalMediaPlays;
	}

	public Long getTotalBounces() {
		return totalBounces;
	}

	public void setTotalBounces(Long totalBounces) {
		this.totalBounces = totalBounces;
	}

	public Long getTotalOptOuts() {
		return totalOptOuts;
	}

	public void setTotalOptOuts(Long totalOptOuts) {
		this.totalOptOuts = totalOptOuts;
	}

	@Override
	public String toString() {
		return "RecipientMailing [mailingName=" + mailingName + ", mailingId=" + mailingId + ", sentDateTime="
				+ sentDateTime + ", totalOpens=" + totalOpens + ", totalClickstreams=" + totalClickstreams
				+ ", totalClicks=" + totalClicks + ", totalConversions=" + totalConversions + ", totalAttachments="
				+ totalAttachments + ", totalForwards=" + totalForwards + ", totalMediaPlays=" + totalMediaPlays
				+ ", totalBounces=" + totalBounces + ", totalOptOuts=" + totalOptOuts + "]";
	}

}
