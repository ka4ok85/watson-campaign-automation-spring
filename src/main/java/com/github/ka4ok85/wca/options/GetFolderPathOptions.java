package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.GetFolderPathObjectType;

public class GetFolderPathOptions extends AbstractOptions {

	private GetFolderPathObjectType objectType;
	private String folderId;
	private Long objectId;

	public GetFolderPathOptions(GetFolderPathObjectType objectType, String folderId) {
		super();

		if (objectType == GetFolderPathObjectType.Data) {
			throw new RuntimeException("Object Type must be Mailing");
		}

		if (folderId == null || folderId.isEmpty()) {
			throw new RuntimeException("Folder ID must be non-empty string");
		}

		this.objectType = objectType;
		this.folderId = folderId;
	}

	public GetFolderPathOptions(GetFolderPathObjectType objectType, Long objectId) {
		super();

		if (objectType == GetFolderPathObjectType.Mailing) {
			throw new RuntimeException("Object Type must be Data");
		}

		if (objectId == null || objectId < 1) {
			throw new RuntimeException("Object ID must be greater than zero. Provided Object ID = " + objectId);
		}

		this.objectType = objectType;
		this.objectId = objectId;
	}

	public GetFolderPathObjectType getObjectType() {
		return objectType;
	}

	public String getFolderId() {
		return folderId;
	}

	public Long getObjectId() {
		return objectId;
	}

	@Override
	public String toString() {
		return "GetFolderPathOptions [objectType=" + objectType + ", folderId=" + folderId + ", objectId=" + objectId
				+ "]";
	}

}
