package com.github.ka4ok85.wca.response.containers;

import java.time.LocalDateTime;

public class ReportIdByDateMailing {
	private Long reportId;
	private LocalDateTime sentDateTime;

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public LocalDateTime getSentDateTime() {
		return sentDateTime;
	}

	public void setSentDateTime(LocalDateTime sentDateTime) {
		this.sentDateTime = sentDateTime;
	}

	@Override
	public String toString() {
		return "ReportIdByDateMailing [reportId=" + reportId + ", sentDateTime=" + sentDateTime + "]";
	}

}
