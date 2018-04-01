package com.github.ka4ok85.wca.options;

import java.util.HashMap;
import java.util.Map;

public class DoubleOptInRecipientOptions extends AbstractOptions {

	private final Long listId;
	private boolean sendAutoReply = false;
	private boolean allowHtml = true;
	private Map<String, String> columns = new HashMap<String, String>();

	public DoubleOptInRecipientOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
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

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}

	public Long getListId() {
		return listId;
	}

	@Override
	public String toString() {
		return "DoubleOptInRecipientOptions [listId=" + listId + ", sendAutoReply=" + sendAutoReply + ", allowHtml="
				+ allowHtml + ", columns=" + columns + "]";
	}

}
