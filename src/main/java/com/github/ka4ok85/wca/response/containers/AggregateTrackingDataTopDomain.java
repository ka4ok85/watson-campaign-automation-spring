package com.github.ka4ok85.wca.response.containers;

public class AggregateTrackingDataTopDomain {
	private Long mailingId;
	private Long reportId;
	private String domain;
	private Long numSent;
	private Long numBounce;
	private Long numOpen;
	private Long numClick;
	private Long numUnsubscribe;
	private Long numConversion;
	private Long numConversionAmount;
	private Long numReplyAbuse;
	private Long numReplyMailBlock;
	private Long numReplyMailRestriction;

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

	public Long getNumBounce() {
		return numBounce;
	}

	public void setNumBounce(Long numBounce) {
		this.numBounce = numBounce;
	}

	public Long getNumOpen() {
		return numOpen;
	}

	public void setNumOpen(Long numOpen) {
		this.numOpen = numOpen;
	}

	public Long getNumClick() {
		return numClick;
	}

	public void setNumClick(Long numClick) {
		this.numClick = numClick;
	}

	public Long getNumUnsubscribe() {
		return numUnsubscribe;
	}

	public void setNumUnsubscribe(Long numUnsubscribe) {
		this.numUnsubscribe = numUnsubscribe;
	}

	public Long getNumConversion() {
		return numConversion;
	}

	public void setNumConversion(Long numConversion) {
		this.numConversion = numConversion;
	}

	public Long getNumConversionAmount() {
		return numConversionAmount;
	}

	public void setNumConversionAmount(Long numConversionAmount) {
		this.numConversionAmount = numConversionAmount;
	}

	public Long getNumReplyAbuse() {
		return numReplyAbuse;
	}

	public void setNumReplyAbuse(Long numReplyAbuse) {
		this.numReplyAbuse = numReplyAbuse;
	}

	public Long getNumReplyMailBlock() {
		return numReplyMailBlock;
	}

	public void setNumReplyMailBlock(Long numReplyMailBlock) {
		this.numReplyMailBlock = numReplyMailBlock;
	}

	public Long getNumReplyMailRestriction() {
		return numReplyMailRestriction;
	}

	public void setNumReplyMailRestriction(Long numReplyMailRestriction) {
		this.numReplyMailRestriction = numReplyMailRestriction;
	}

	@Override
	public String toString() {
		return "AggregateTrackingDataTopDomain [mailingId=" + mailingId + ", reportId=" + reportId + ", domain="
				+ domain + ", numSent=" + numSent + ", numBounce=" + numBounce + ", numOpen=" + numOpen + ", numClick="
				+ numClick + ", numUnsubscribe=" + numUnsubscribe + ", numConversion=" + numConversion
				+ ", numConversionAmount=" + numConversionAmount + ", numReplyAbuse=" + numReplyAbuse
				+ ", numReplyMailBlock=" + numReplyMailBlock + ", numReplyMailRestriction=" + numReplyMailRestriction
				+ "]";
	}

}
