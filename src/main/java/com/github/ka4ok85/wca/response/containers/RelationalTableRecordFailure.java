package com.github.ka4ok85.wca.response.containers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RelationalTableRecordFailure {

	private String failureType;
	private String description;
	private List<Map<String, String>> columns = new ArrayList<Map<String, String>>();

	public String getFailureType() {
		return failureType;
	}

	public void setFailureType(String failureType) {
		this.failureType = failureType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Map<String, String>> getColumns() {
		return columns;
	}

	public void setColumns(List<Map<String, String>> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "RelationalTableRecordFailure [failureType=" + failureType + ", description=" + description
				+ ", columns=" + columns + "]";
	}

}
