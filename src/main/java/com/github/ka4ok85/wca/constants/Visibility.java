package com.github.ka4ok85.wca.constants;

public enum Visibility {
	PRIVATE(0), SHARED(1);

	private int value;

	private Visibility(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
