package com.github.ka4ok85.wca.constants;

public enum ListColumnType {
	TEXT(0), YESNO(1), NUMERIC(2), DATE(3), TIME(4), COUNTRY(5), SELECTION(6), SEGMENTING(8), SYSTEM(9), SMS_OPT_IN(
			13), SMS_OPTED_OUT_DATE(14), SMS_PHONE_NUMBER(15), PHONE_NUMBER(16), TIMESTAMP(17), MULTI_SELECT(20);

	private int value;

	private ListColumnType(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}

	public static ListColumnType getListColumnType(int value) {
		for (ListColumnType listColumnType : ListColumnType.values()) {
			if (listColumnType.value == value) {
				return listColumnType;
			}
		}

		throw new IllegalArgumentException("ListColumnType not found. Provided value is: " + value);
	}
}
