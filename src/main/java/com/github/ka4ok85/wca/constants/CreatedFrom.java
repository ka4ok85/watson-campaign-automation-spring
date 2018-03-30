package com.github.ka4ok85.wca.constants;

public enum CreatedFrom {
	IMPORTED_FROM_DATABASE(0), ADDED_MANUALLY(1), OPTED_IN(2), CREATED_FROM_TRACKING_DATABASE(4);

	private int value;

	private CreatedFrom(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}
