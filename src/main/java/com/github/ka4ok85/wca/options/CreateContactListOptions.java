package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.Visibility;

public class CreateContactListOptions extends AbstractOptions {

	private Long databaseId;
	private String contactListName;
	private Visibility visibility;
	private Long parentFolderId;
	private String parentFolderPath;

	public Long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public String getContactListName() {
		return contactListName;
	}

	public void setContactListName(String contactListName) {
		this.contactListName = contactListName;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
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
