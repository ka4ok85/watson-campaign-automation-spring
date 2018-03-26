package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.Visibility;

public class DeleteListOptions extends AbstractOptions {

	private Long listId;
	private String listName;
	private boolean keepListDetails = true;
	private boolean isRecursive;
	private Visibility visiblity;

	public DeleteListOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
	}

	public DeleteListOptions(String listName, Visibility visibility) {
		super();
		if (listName == null || listName.trim().isEmpty()) {
			throw new RuntimeException("List Name must be non-empty String. Provided List Name = " + listName);
		}
		this.listName = listName;
		this.visiblity = visibility;
	}

	public boolean isKeepListDetails() {
		return keepListDetails;
	}

	public void setKeepListDetails(boolean keepListDetails) {
		this.keepListDetails = keepListDetails;
	}

	public boolean isRecursive() {
		return isRecursive;
	}

	public void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}

	public Long getListId() {
		return listId;
	}

	public String getListName() {
		return listName;
	}

	public Visibility getVisiblity() {
		return visiblity;
	}

	@Override
	public String toString() {
		return "DeleteListOptions [listId=" + listId + ", listName=" + listName + ", keepListDetails=" + keepListDetails
				+ ", isRecursive=" + isRecursive + ", visiblity=" + visiblity + "]";
	}

}
