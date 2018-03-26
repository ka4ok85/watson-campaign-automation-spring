package com.github.ka4ok85.wca.options;

public class DeleteListOptions extends AbstractOptions {

	private Long listId;
	private String listName;
	private boolean keepListDetails = true;
	private boolean isRecursive;

	public DeleteListOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
	}

	public DeleteListOptions(String listName) {
		super();
		if (listName != null && !listName.trim().isEmpty()) {
			throw new RuntimeException("List Name must be non-empty String. Provided List Name = " + listName);
		}
		this.listName = listName;
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

	@Override
	public String toString() {
		return "DeleteListOptions [listId=" + listId + ", listName=" + listName + ", keepListDetails=" + keepListDetails
				+ ", isRecursive=" + isRecursive + "]";
	}

}
