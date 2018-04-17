package com.github.ka4ok85.wca.response;

public class JoinTableResponse extends AbstractResponse {
	private Long JobId;

	public Long getJobId() {
		return JobId;
	}

	public void setJobId(Long jobId) {
		JobId = jobId;
	}

	@Override
	public String toString() {
		return "JoinTableResponse [JobId=" + JobId + "]";
	}
}
