package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PurgeTableResponse extends AbstractResponse {
	private Long JobId;
	private String description;

	public Long getJobId() {
		return JobId;
	}

	public void setJobId(Long jobId) {
		JobId = jobId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "PurgeTableResponse [JobId=" + JobId + ", description=" + description + "]";
	}

}
