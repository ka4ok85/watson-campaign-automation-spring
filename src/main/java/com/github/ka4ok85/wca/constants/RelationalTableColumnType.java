package com.github.ka4ok85.wca.constants;

public enum RelationalTableColumnType {
	TEXT("TEXT"), YESNO("YESNO"), NUMERIC("NUMERIC"), DATE("DATE"), TIME("TIME"), COUNTRY("COUNTRY"), SELECTION(
			"SELECTION"), EMAIL("EMAIL"), SYNC_ID("SYNC_ID"), DATE_TIME("DATE_TIME"), PHONE_NUMBER("PHONE_NUMBER");

	private String value;

	private RelationalTableColumnType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}
