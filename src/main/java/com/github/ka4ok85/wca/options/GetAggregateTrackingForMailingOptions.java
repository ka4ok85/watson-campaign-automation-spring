package com.github.ka4ok85.wca.options;

public class GetAggregateTrackingForMailingOptions extends AbstractOptions {

	private final Long mailingId;
	private final Long reportId;
	private boolean topDomain;
	private boolean inboxMonitoring;
	private boolean perClick;

	public GetAggregateTrackingForMailingOptions(Long mailingId, Long reportId) {
		super();
		if (mailingId == null || mailingId < 1) {
			throw new RuntimeException("Mailing ID must be greater than zero. Provided Mailing ID = " + mailingId);
		}

		if (reportId == null || reportId < 1) {
			throw new RuntimeException("Report ID must be greater than zero. Provided Report ID = " + reportId);
		}

		this.mailingId = mailingId;
		this.reportId = reportId;
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

	public Long getMailingId() {
		return mailingId;
	}

	public Long getReportId() {
		return reportId;
	}

	@Override
	public String toString() {
		return "GetAggregateTrackingForMailingOptions [mailingId=" + mailingId + ", reportId=" + reportId
				+ ", topDomain=" + topDomain + ", inboxMonitoring=" + inboxMonitoring + ", perClick=" + perClick + "]";
	}

}
