package com.github.ka4ok85.wca.response;

import com.github.ka4ok85.wca.constants.JobStatus;

public class JobResponse extends AbstractResponse {

	private int jobId;
	private JobStatus jobStatus;
	private String jobDescription;

	public JobResponse() {

	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
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

	@Override
	public String toString() {
		return "JobResponse [jobId=" + jobId + ", jobStatus=" + jobStatus + ", jobDescription=" + jobDescription + "]";
	}

	public boolean isRunning() {
		return (jobStatus == JobStatus.RUNNING);
	}

	public boolean isComplete() {
		return (jobStatus == JobStatus.COMPLETE);
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
