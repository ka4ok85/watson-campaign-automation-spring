package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class WebTrackingDataExportResponse extends AbstractResponse {

	private Long jobId;
	private String description;
	private String remoteFileName;

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

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	@Override
	public String toString() {
		return "WebTrackingDataExportResponse [jobId=" + jobId + ", description=" + description + ", remoteFileName="
				+ remoteFileName + "]";
	}

}
