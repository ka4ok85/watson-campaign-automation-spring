package com.github.ka4ok85.wca.options;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class RemoveRecipientOptions extends AbstractOptions {
	private final Long listId;
	private String email;
	private Map<String, String> columns = new HashMap<String, String>();

	public RemoveRecipientOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
	}

	public Long getListId() {
		return listId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new RuntimeException("Bad Email: " + email);
		}

		this.email = email;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "RemoveRecipientOptions [listId=" + listId + ", email=" + email + ", columns=" + columns + "]";
	}

}
