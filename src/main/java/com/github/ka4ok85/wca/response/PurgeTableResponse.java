package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PurgeTableResponse extends AbstractResponse {
	private Long JobId;

	public Long getJobId() {
		return JobId;
	}

	public void setJobId(Long jobId) {
		JobId = jobId;
	}

	@Override
	public String toString() {
		return "PurgeTableResponse [JobId=" + JobId + "]";
	}
}
