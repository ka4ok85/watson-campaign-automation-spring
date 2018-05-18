package com.github.ka4ok85.wca.response.containers;

public class AggregateTrackingDataClicks {
	private Long mailingId;
	private Long reportId;
	private String linkName;
	private String linkUrl;
	private Long numTotalHtml;
	private Long numTotalAol;
	private Long numTotalWeb;
	private Long numTotalText;

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

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Long getNumTotalHtml() {
		return numTotalHtml;
	}

	public void setNumTotalHtml(Long numTotalHtml) {
		this.numTotalHtml = numTotalHtml;
	}

	public Long getNumTotalAol() {
		return numTotalAol;
	}

	public void setNumTotalAol(Long numTotalAol) {
		this.numTotalAol = numTotalAol;
	}

	public Long getNumTotalWeb() {
		return numTotalWeb;
	}

	public void setNumTotalWeb(Long numTotalWeb) {
		this.numTotalWeb = numTotalWeb;
	}

	public Long getNumTotalText() {
		return numTotalText;
	}

	public void setNumTotalText(Long numTotalText) {
		this.numTotalText = numTotalText;
	}

	@Override
	public String toString() {
		return "AggregateTrackingDataClicks [mailingId=" + mailingId + ", reportId=" + reportId + ", linkName="
				+ linkName + ", linkUrl=" + linkUrl + ", numTotalHtml=" + numTotalHtml + ", numTotalAol=" + numTotalAol
				+ ", numTotalWeb=" + numTotalWeb + ", numTotalText=" + numTotalText + "]";
	}

}
