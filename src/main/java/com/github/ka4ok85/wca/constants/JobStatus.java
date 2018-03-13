package com.github.ka4ok85.wca.constants;

public enum JobStatus {
	CANCELED("CANCELED"), COMPLETE("COMPLETE"), ERROR("ERROR"), RUNNING("RUNNING"), WAITING("WAITING");

	private String value;

	private JobStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
