package com.github.ka4ok85.wca.response;

public class DeleteTableResponse extends AbstractResponse {
	private Long JobId;

	public Long getJobId() {
		return JobId;
	}

	public void setJobId(Long jobId) {
		JobId = jobId;
	}

	@Override
	public String toString() {
		return "DeleteTableResponse [JobId=" + JobId + "]";
	}
}
