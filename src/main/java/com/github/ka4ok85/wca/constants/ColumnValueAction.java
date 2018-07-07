package com.github.ka4ok85.wca.constants;

public enum ColumnValueAction {
	RESET(0), UPDATE(1), INCREMENT(2);

	private int value;

	private ColumnValueAction(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}
