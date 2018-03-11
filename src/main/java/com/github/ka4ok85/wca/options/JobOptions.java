package com.github.ka4ok85.wca.options;

public class JobOptions extends AbstractOptions {
	private int jobId;

	public JobOptions(int jobId) {
		super();
		this.jobId = jobId;
	}

	public int getJobId() {
		return jobId;
	}

}
