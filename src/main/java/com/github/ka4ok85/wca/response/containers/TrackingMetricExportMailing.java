package com.github.ka4ok85.wca.response.containers;

import java.time.LocalDateTime;

public class TrackingMetricExportMailing {
	private Long jobId;
	private Long mailingId;
	private String filePath;
	private LocalDateTime sentDate;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getMailingId() {
		return mailingId;
	}

	public void setMailingId(Long mailingId) {
		this.mailingId = mailingId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public LocalDateTime getSentDate() {
		return sentDate;
	}

	public void setSentDate(LocalDateTime sentDate) {
		this.sentDate = sentDate;
	}

	@Override
	public String toString() {
		return "TrackingMetricExportMailing [jobId=" + jobId + ", mailingId=" + mailingId + ", filePath=" + filePath
				+ ", sentDate=" + sentDate + "]";
	}

}
