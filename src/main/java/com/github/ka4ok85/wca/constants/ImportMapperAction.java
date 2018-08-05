package com.github.ka4ok85.wca.constants;

public enum ImportMapperAction {
	CREATE("CREATE"), ADD_ONLY("ADD_ONLY"), UPDATE_ONLY("UPDATE_ONLY"), ADD_AND_UPDATE("ADD_AND_UPDATE"), OPT_OUT(
			"OPT_OUT");

	private String value;

	private ImportMapperAction(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

}