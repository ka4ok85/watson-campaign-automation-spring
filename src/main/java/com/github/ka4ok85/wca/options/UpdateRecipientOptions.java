package com.github.ka4ok85.wca.options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.ka4ok85.wca.constants.CreatedFrom;

public class UpdateRecipientOptions extends AbstractOptions {
	private final Long listId;
	private String oldEmail;
	private Long recipientId;
	private String encodedRecipientId;
	private boolean sendAutoReply = false;
	private boolean allowHtml = true;
	private String visitorKey;
	private Map<String, String> syncFields = new HashMap<String, String>();
	private Map<String, String> columns = new HashMap<String, String>();

	public UpdateRecipientOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
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

	public boolean isSendAutoReply() {
		return sendAutoReply;
	}

	public void setSendAutoReply(boolean sendAutoReply) {
		this.sendAutoReply = sendAutoReply;
	}

	public boolean isAllowHtml() {
		return allowHtml;
	}

	public void setAllowHtml(boolean allowHtml) {
		this.allowHtml = allowHtml;
	}

	public String getVisitorKey() {
		return visitorKey;
	}

	public void setVisitorKey(String visitorKey) {
		this.visitorKey = visitorKey;
	}

	public Map<String, String> getSyncFields() {
		return syncFields;
	}

	public void setSyncFields(Map<String, String> syncFields) {
		this.syncFields = syncFields;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}

	public Long getListId() {
		return listId;
	}
	
	
}
