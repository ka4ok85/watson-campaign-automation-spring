package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.constants.FileEncoding;

@Component
@Scope("prototype")
public class RawRecipientDataExportResponse extends AbstractResponse {

	private Long jobId;
	private String description;
	private FileEncoding fileEncoding;
	private Long timeZone;
	private String eventTypes;
	private String mailingTypes;
	private String remoteFileName;
	private Long exportedRowCount;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FileEncoding getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(FileEncoding fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public Long getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(Long timeZone) {
		this.timeZone = timeZone;
	}

	public String getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(String eventTypes) {
		this.eventTypes = eventTypes;
	}

	public String getMailingTypes() {
		return mailingTypes;
	}

	public void setMailingTypes(String mailingTypes) {
		this.mailingTypes = mailingTypes;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	public Long getExportedRowCount() {
		return exportedRowCount;
	}

	public void setExportedRowCount(Long exportedRowCount) {
		this.exportedRowCount = exportedRowCount;
	}

	@Override
	public String toString() {
		return "RawRecipientDataExportResponse [jobId=" + jobId + ", description=" + description + ", fileEncoding="
				+ fileEncoding + ", timeZone=" + timeZone + ", eventTypes=" + eventTypes + ", mailingTypes="
				+ mailingTypes + ", remoteFileName=" + remoteFileName + ", exportedRowCount=" + exportedRowCount + "]";
	}

}
