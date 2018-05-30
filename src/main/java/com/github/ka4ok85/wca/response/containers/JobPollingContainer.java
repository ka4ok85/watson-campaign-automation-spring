package com.github.ka4ok85.wca.response.containers;

import java.util.HashMap;
import java.util.Map;

public class JobPollingContainer {
	private Long jobId;
	private Map<String, String> parameters = new HashMap<String, String>();

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "JobPollingContainer [jobId=" + jobId + ", parameters=" + parameters + "]";
	}

}
