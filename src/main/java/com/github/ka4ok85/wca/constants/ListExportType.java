package com.github.ka4ok85.wca.constants;

public enum ListExportType {
	ALL(0), OPTIN(1), OPTOUT(2), UNDELIVERABLE(3);

	private int value;

	private ListExportType(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
