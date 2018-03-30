package com.github.ka4ok85.wca.options;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.ka4ok85.wca.constants.CreatedFrom;

public class AddRecipientOptions extends AbstractOptions {

	private final Long listId;
	private CreatedFrom createdFrom = CreatedFrom.ADDED_MANUALLY;
	private boolean sendAutoReply = false;
	private boolean updateIfFound = true;
	private boolean allowHtml = true;
	private String visitorKey;
	private List<Long> contactLists = new ArrayList<Long>();
	private Map<String, String> syncFields = new HashMap<String, String>();
	private Map<String, String> columns = new HashMap<String, String>();

	public AddRecipientOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
	}

	public CreatedFrom getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(CreatedFrom createdFrom) {
		this.createdFrom = createdFrom;
	}

	public boolean isSendAutoReply() {
		return sendAutoReply;
	}

	public void setSendAutoReply(boolean sendAutoReply) {
		this.sendAutoReply = sendAutoReply;
	}

	public boolean isUpdateIfFound() {
		return updateIfFound;
	}

	public void setUpdateIfFound(boolean updateIfFound) {
		this.updateIfFound = updateIfFound;
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

	public List<Long> getContactLists() {
		return contactLists;
	}

	public void setContactLists(List<Long> contactLists) {
		this.contactLists = contactLists;
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

	@Override
	public String toString() {
		return "AddRecipientOptions [listId=" + listId + ", createdFrom=" + createdFrom + ", sendAutoReply="
				+ sendAutoReply + ", updateIfFound=" + updateIfFound + ", allowHtml=" + allowHtml + ", visitorKey="
				+ visitorKey + ", contactLists=" + contactLists + ", syncFields=" + syncFields + ", columns=" + columns
				+ "]";
	}

}
