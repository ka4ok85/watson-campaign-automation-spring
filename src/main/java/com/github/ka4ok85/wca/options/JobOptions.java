package com.github.ka4ok85.wca.options;

public class JobOptions extends AbstractOptions {
	private int jobId;

	public JobOptions(int jobId) {
		if (jobId < 1) {
			throw new RuntimeException("Job ID must be greater than zero. Provided Job ID = " + jobId);
		}

		this.jobId = jobId;
	}

	public int getJobId() {
		return jobId;
	}

	@Override
	public String toString() {
		return "JobOptions [jobId=" + jobId + "]";
	}

}
