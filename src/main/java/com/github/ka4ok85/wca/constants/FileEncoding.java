package com.github.ka4ok85.wca.constants;

public enum FileEncoding {
	UTF_8(0), ISO_8859_1(1);

	private int value;

	private FileEncoding(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
