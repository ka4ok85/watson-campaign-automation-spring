package com.github.ka4ok85.wca.constants;

public enum Visibility {
	PRIVATE(0), SHARED(1);

	private int value;

	private Visibility(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}

	public static Visibility getVisibility(int value) {
		for (Visibility visibility : Visibility.values()) {
			if (visibility.value == value) {
				return visibility;
			}
		}

		throw new IllegalArgumentException("Visibility not found. Provided value is: " + value);
	}
}
