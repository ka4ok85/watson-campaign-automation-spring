package com.github.ka4ok85.wca.constants;

public enum ImportFileFormat {
	CSV(0), TAB(1), PIPE(2);

	private Integer value;

	private ImportFileFormat(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}
