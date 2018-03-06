package com.github.ka4ok85.wca.constants;

public enum ExportFormat {
	CSV(0), TAB(1), PIPE(2);

	private int value;

	private ExportFormat(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
