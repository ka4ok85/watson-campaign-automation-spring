package com.github.ka4ok85.wca.options;

public class CalculateQueryOptions extends AbstractOptions {

	private final Long queryId;

	public CalculateQueryOptions(Long queryId) {
		super();
		if (queryId < 1) {
			throw new RuntimeException("Query ID must be greater than zero. Provided Query ID = " + queryId);
		}
		this.queryId = queryId;
	}

	public Long getQueryId() {
		return queryId;
	}

	@Override
	public String toString() {
		return "CalculateQueryOptions [queryId=" + queryId + "]";
	}

}
