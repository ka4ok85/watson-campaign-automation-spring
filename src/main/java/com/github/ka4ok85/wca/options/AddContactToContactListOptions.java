package com.github.ka4ok85.wca.options;

import java.util.HashMap;
import java.util.Map;

public class AddContactToContactListOptions extends AbstractOptions {
	private final Long contactListId;
	private Long contactId;
	private Map<String, String> columns = new HashMap<String, String>();

	public AddContactToContactListOptions(Long contactListId) {
		super();
		if (contactListId == null || contactListId < 1) {
			throw new RuntimeException(
					"Contact List ID must be greater than zero. Provided Contact List ID = " + contactListId);
		}

		this.contactListId = contactListId;
	}

	public Long getContactListId() {
		return contactListId;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		if (contactId == null || contactId < 1) {
			throw new RuntimeException("Contact ID must be greater than zero. Provided Contact ID = " + contactId);
		}

		this.contactId = contactId;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "AddContactToContactListOptions [contactListId=" + contactListId + ", contactId=" + contactId
				+ ", columns=" + columns + "]";
	}

}
