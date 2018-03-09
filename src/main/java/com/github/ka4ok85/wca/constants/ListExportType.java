package com.github.ka4ok85.wca.constants;

public enum ListExportType {
	ALL("ALL"), OPTIN("OPT_IN"), OPTOUT("OPT_OUT"), UNDELIVERABLE("UNDELIVERABLE");

	private String value;

	private ListExportType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
