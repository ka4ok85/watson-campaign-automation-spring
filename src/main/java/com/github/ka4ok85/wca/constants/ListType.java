package com.github.ka4ok85.wca.constants;

public enum ListType {
	DATABASES(0), QUERIES(1), DATABASES_CONTACT_LISTS_QUERIES(2), TEST_LISTS(5), SEED_LISTS(6),
	SUPPRESSION_LISTS(13), RELATIONAL_TABLES(15), CONTACT_LISTS(18);

	private int value;

	private ListType(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}
}
