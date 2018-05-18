package com.github.ka4ok85.wca.response.containers;

public class AggregateTrackingDataInboxMonitoring {
	private Long mailingId;
	private Long reportId;
	private String domain;
	private Long numSent;
	private Long numInbox;
	private Long numBulk;
	private Long numNotReceived;

	public Long getMailingId() {
		return mailingId;
	}

	public void setMailingId(Long mailingId) {
		this.mailingId = mailingId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Long getNumSent() {
		return numSent;
	}

	public void setNumSent(Long numSent) {
		this.numSent = numSent;
	}

	public Long getNumInbox() {
		return numInbox;
	}

	public void setNumInbox(Long numInbox) {
		this.numInbox = numInbox;
	}

	public Long getNumBulk() {
		return numBulk;
	}

	public void setNumBulk(Long numBulk) {
		this.numBulk = numBulk;
	}

	public Long getNumNotReceived() {
		return numNotReceived;
	}

	public void setNumNotReceived(Long numNotReceived) {
		this.numNotReceived = numNotReceived;
	}

	@Override
	public String toString() {
		return "AggregateTrackingDataInboxMonitoring [mailingId=" + mailingId + ", reportId=" + reportId + ", domain="
				+ domain + ", numSent=" + numSent + ", numInbox=" + numInbox + ", numBulk=" + numBulk
				+ ", numNotReceived=" + numNotReceived + "]";
	}

}
