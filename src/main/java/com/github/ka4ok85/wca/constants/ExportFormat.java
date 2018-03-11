package com.github.ka4ok85.wca.constants;

public enum ExportFormat {
	CSV("CSV"), TAB("TAB"), PIPE("PIPE");

	private String value;

	private ExportFormat(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
