package com.github.ka4ok85.wca.response;

public class JobResponse extends AbstractResponse {

	private int jobId;
	private String jobStatus;
	private String jobDescription;

	public JobResponse(int responseCode, String output) {
		super(responseCode, output);
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
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

}
