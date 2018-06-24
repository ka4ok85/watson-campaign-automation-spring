package com.github.ka4ok85.wca.constants;

public enum GetFolderPathObjectType {
	Data("Data"), Mailing("Mailing");

	private String value;

	private GetFolderPathObjectType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
	
	public static GetFolderPathObjectType getObjectType(String value) {
		for (GetFolderPathObjectType objectType : GetFolderPathObjectType.values()) {
			if (objectType.value.equals(value)) {
				return objectType;
			}
		}

		throw new IllegalArgumentException("Object Type not found. Provided value is: " + value);
	}
}