package com.github.ka4ok85.wca.response;

public class CreateContactListResponse extends AbstractResponse {

	private int contactListId;

	public int getContactListId() {
		return contactListId;
	}

	public void setContactListId(int contactListId) {
		this.contactListId = contactListId;
	}

	@Override
	public String toString() {
		return "CreateContactListResponse [contactListId=" + contactListId + "]";
	}

}
