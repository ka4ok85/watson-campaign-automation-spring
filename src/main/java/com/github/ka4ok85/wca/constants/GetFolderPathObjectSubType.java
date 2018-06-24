package com.github.ka4ok85.wca.constants;

public enum GetFolderPathObjectSubType {
	Database("Database"), Contact_List("Contact List"), Query("Query"), Test_List("Test List"), Seed_List(
			"Seed List"), Suppression_List("Suppression List"), Relational_Table("Relational Table"), Template(
					"Template"), Sent("Sent"), Autoresponder(
							"Autoresponder"), Inactive_Autoresponder("Inactive Autoresponder"), Deleted("Deleted");

	private String value;

	private GetFolderPathObjectSubType(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public static GetFolderPathObjectSubType getObjectSubType(String value) {
		for (GetFolderPathObjectSubType objectSubType : GetFolderPathObjectSubType.values()) {
			if (objectSubType.value.equals(value)) {
				return objectSubType;
			}
		}

		throw new IllegalArgumentException("Object Sub Type not found. Provided value is: " + value);
	}
}
