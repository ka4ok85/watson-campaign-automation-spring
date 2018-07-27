package com.github.ka4ok85.wca.constants;

public enum ExportFormatWebTrackingDataExport {
	CSV(0), TAB(2), PIPE(1);

	private Integer value;

	private ExportFormatWebTrackingDataExport(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}
