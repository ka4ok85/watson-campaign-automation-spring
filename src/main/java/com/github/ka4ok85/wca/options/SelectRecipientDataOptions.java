package com.github.ka4ok85.wca.options;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class SelectRecipientDataOptions extends AbstractOptions {

	private final Long listId;
	private String email;
	private Long recipientId;
	private String encodedRecipientId;
	private String visitorKey;
	private boolean returnContactLists = false;
	private Map<String, String> keyColumns = new HashMap<String, String>();

	public SelectRecipientDataOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		this.listId = listId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new RuntimeException("Bad Email Address: " + email);
		}

		this.email = email;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	public String getEncodedRecipientId() {
		return encodedRecipientId;
	}

	public void setEncodedRecipientId(String encodedRecipientId) {
		this.encodedRecipientId = encodedRecipientId;
	}

	public String getVisitorKey() {
		return visitorKey;
	}

	public void setVisitorKey(String visitorKey) {
		this.visitorKey = visitorKey;
	}

	public boolean isReturnContactLists() {
		return returnContactLists;
	}

	public void setReturnContactLists(boolean returnContactLists) {
		this.returnContactLists = returnContactLists;
	}

	public Map<String, String> getKeyColumns() {
		return keyColumns;
	}

	public void setKeyColumns(Map<String, String> keyColumns) {
		this.keyColumns = keyColumns;
	}

	public Long getListId() {
		return listId;
	}

	@Override
	public String toString() {
		return "SelectRecipientDataOptions [listId=" + listId + ", email=" + email + ", recipientId=" + recipientId
				+ ", encodedRecipientId=" + encodedRecipientId + ", visitorKey=" + visitorKey + ", returnContactLists="
				+ returnContactLists + ", keyColumns=" + keyColumns + "]";
	}

}
