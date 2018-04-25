package com.github.ka4ok85.wca.constants;

public enum FileEncoding {
	UTF_8("utf-8"), ISO_8859_1("iso-8859-1");

	private String value;

	private FileEncoding(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
	
	public static FileEncoding getFileEncoding(String value) {
		for (FileEncoding fileEncoding : FileEncoding.values()) {
			if (fileEncoding.value.equals(value)) {
				return fileEncoding;
			}
		}

		throw new IllegalArgumentException("File Encoding not found. Provided value is: " + value);
	}
}
