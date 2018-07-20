package com.github.ka4ok85.wca.response;

import java.util.HashMap;
import java.util.Map;

import com.github.ka4ok85.wca.constants.JobStatus;

public class JobResponse extends AbstractResponse {

	private Long jobId;
	private JobStatus jobStatus;
	private String jobDescription;
	private Map<String, String> parameters = new HashMap<String, String>();

	public JobResponse() {

	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "JobResponse [jobId=" + jobId + ", jobStatus=" + jobStatus + ", jobDescription=" + jobDescription
				+ ", parameters=" + parameters + "]";
	}

	public boolean isRunning() {
		return (jobStatus == JobStatus.RUNNING);
	}

	public boolean isWaiting() {
		return (jobStatus == JobStatus.WAITING);
	}

	public boolean isCanceled() {
		return (jobStatus == JobStatus.CANCELED);
	}

	public boolean isError() {
		return (jobStatus == JobStatus.ERROR);
	}
}
