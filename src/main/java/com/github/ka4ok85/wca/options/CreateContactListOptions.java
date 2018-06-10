package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.Visibility;

public class CreateContactListOptions extends AbstractOptions {

	final private Long databaseId;
	final private String contactListName;
	final private Visibility visibility;
	private Long parentFolderId;
	private String parentFolderPath;

	public CreateContactListOptions(Long databaseId, String contactListName, Visibility visibility) {
		super();
		if (databaseId == null || databaseId < 1) {
			throw new RuntimeException("Database ID must be greater than zero. Provided Database ID = " + databaseId);
		}

		if (contactListName == null || contactListName.isEmpty()) {
			throw new RuntimeException(
					"Contact List Name must be non-empty String. Provided Contact List Name = " + contactListName);
		}

		if (visibility == null) {
			throw new RuntimeException("Visibility must be non-null");
		}

		this.databaseId = databaseId;
		this.contactListName = contactListName;
		this.visibility = visibility;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public String getContactListName() {
		return contactListName;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public Long getParentFolderId() {
		return parentFolderId;
	}

	public void setParentFolderId(Long parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public String getParentFolderPath() {
		return parentFolderPath;
	}

	public void setParentFolderPath(String parentFolderPath) {
		this.parentFolderPath = parentFolderPath;
	}

	@Override
	public String toString() {
		return "CreateContactListOptions [databaseId=" + databaseId + ", contactListName=" + contactListName
				+ ", visibility=" + visibility + ", parentFolderId=" + parentFolderId + ", parentFolderPath="
				+ parentFolderPath + "]";
	}

}
