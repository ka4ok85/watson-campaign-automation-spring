package com.github.ka4ok85.wca.options;

import java.time.LocalDateTime;

import com.github.ka4ok85.wca.constants.ExportFormat;

public class TrackingMetricExportOptions extends AbstractOptions {

	private Long mailingId;
	private Long reportId;
	private ExportFormat exportFormat = ExportFormat.CSV;
	private Long listId;
	private LocalDateTime sendStartDate;
	private LocalDateTime sendEndDate;
	private boolean moveToFtp = true;
	private boolean allAggregateMetrics = false;
	private boolean aggregateSummary = false;
	private boolean allMetricsForwards = false;
	private boolean aggregateClicks = false;
	private boolean aggregateClickStreams = false;
	private boolean aggregateConversions = false;
	private boolean aggregateAttachments = false;
	private boolean aggregateMedia = false;
	private boolean aggregateSuppressions = false;
	private boolean mailTrackInterval = false;
	private boolean topDomains = false;
	private boolean excludeImFromMetrics = false;
	private String localAbsoluteFilePath;

	public Long getMailingId() {
		return mailingId;
	}

	public void setMailingId(Long mailingId) {
		if (mailingId < 1) {
			throw new RuntimeException("Mailing Id must be greater than zero, but provided value is: " + mailingId);
		}

		this.mailingId = mailingId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		if (mailingId < 1) {
			throw new RuntimeException("Report Id must be greater than zero, but provided value is: " + reportId);
		}

		this.reportId = reportId;
	}

	public ExportFormat getExportFormat() {
		return exportFormat;
	}

	public void setExportFormat(ExportFormat exportFormat) {
		this.exportFormat = exportFormat;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		if (listId < 1) {
			throw new RuntimeException("List Id must be greater than zero, but provided value is: " + listId);
		}

		this.listId = listId;
	}

	public LocalDateTime getSendStartDate() {
		return sendStartDate;
	}

	public void setSendStartDate(LocalDateTime sendStartDate) {
		this.sendStartDate = sendStartDate;
	}

	public LocalDateTime getSendEndDate() {
		return sendEndDate;
	}

	public void setSendEndDate(LocalDateTime sendEndDate) {
		this.sendEndDate = sendEndDate;
	}

	public boolean isMoveToFtp() {
		return moveToFtp;
	}

	public void setMoveToFtp(boolean moveToFtp) {
		this.moveToFtp = moveToFtp;
	}

	public boolean isAllAggregateMetrics() {
		return allAggregateMetrics;
	}

	public void setAllAggregateMetrics(boolean allAggregateMetrics) {
		this.allAggregateMetrics = allAggregateMetrics;
	}

	public boolean isAggregateSummary() {
		return aggregateSummary;
	}

	public void setAggregateSummary(boolean aggregateSummary) {
		this.aggregateSummary = aggregateSummary;
	}

	public boolean isAllMetricsForwards() {
		return allMetricsForwards;
	}

	public void setAllMetricsForwards(boolean allMetricsForwards) {
		this.allMetricsForwards = allMetricsForwards;
	}

	public boolean isAggregateClicks() {
		return aggregateClicks;
	}

	public void setAggregateClicks(boolean aggregateClicks) {
		this.aggregateClicks = aggregateClicks;
	}

	public boolean isAggregateClickStreams() {
		return aggregateClickStreams;
	}

	public void setAggregateClickStreams(boolean aggregateClickStreams) {
		this.aggregateClickStreams = aggregateClickStreams;
	}

	public boolean isAggregateConversions() {
		return aggregateConversions;
	}

	public void setAggregateConversions(boolean aggregateConversions) {
		this.aggregateConversions = aggregateConversions;
	}

	public boolean isAggregateAttachments() {
		return aggregateAttachments;
	}

	public void setAggregateAttachments(boolean aggregateAttachments) {
		this.aggregateAttachments = aggregateAttachments;
	}

	public boolean isAggregateMedia() {
		return aggregateMedia;
	}

	public void setAggregateMedia(boolean aggregateMedia) {
		this.aggregateMedia = aggregateMedia;
	}

	public boolean isAggregateSuppressions() {
		return aggregateSuppressions;
	}

	public void setAggregateSuppressions(boolean aggregateSuppressions) {
		this.aggregateSuppressions = aggregateSuppressions;
	}

	public boolean isMailTrackInterval() {
		return mailTrackInterval;
	}

	public void setMailTrackInterval(boolean mailTrackInterval) {
		this.mailTrackInterval = mailTrackInterval;
	}

	public boolean isTopDomains() {
		return topDomains;
	}

	public void setTopDomains(boolean topDomains) {
		this.topDomains = topDomains;
	}

	public boolean isExcludeImFromMetrics() {
		return excludeImFromMetrics;
	}

	public void setExcludeImFromMetrics(boolean excludeImFromMetrics) {
		this.excludeImFromMetrics = excludeImFromMetrics;
	}

	public String getLocalAbsoluteFilePath() {
		return localAbsoluteFilePath;
	}

	public void setLocalAbsoluteFilePath(String localAbsoluteFilePath) {
		this.localAbsoluteFilePath = localAbsoluteFilePath;
	}

	@Override
	public String toString() {
		return "TrackingMetricExportOptions [mailingId=" + mailingId + ", reportId=" + reportId + ", exportFormat="
				+ exportFormat + ", listId=" + listId + ", sendStartDate=" + sendStartDate + ", sendEndDate="
				+ sendEndDate + ", moveToFtp=" + moveToFtp + ", allAggregateMetrics=" + allAggregateMetrics
				+ ", aggregateSummary=" + aggregateSummary + ", allMetricsForwards=" + allMetricsForwards
				+ ", aggregateClicks=" + aggregateClicks + ", aggregateClickStreams=" + aggregateClickStreams
				+ ", aggregateConversions=" + aggregateConversions + ", aggregateAttachments=" + aggregateAttachments
				+ ", aggregateMedia=" + aggregateMedia + ", aggregateSuppressions=" + aggregateSuppressions
				+ ", mailTrackInterval=" + mailTrackInterval + ", topDomains=" + topDomains + ", excludeImFromMetrics="
				+ excludeImFromMetrics + ", localAbsoluteFilePath=" + localAbsoluteFilePath + "]";
	}

}
