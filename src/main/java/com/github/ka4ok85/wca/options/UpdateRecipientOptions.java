package com.github.ka4ok85.wca.options;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class UpdateRecipientOptions extends AbstractOptions {
	private final Long listId;
	private String oldEmail;
	private Long recipientId;
	private String encodedRecipientId;
	private boolean sendAutoReply = false;
	private boolean allowHtml = true;
	private String visitorKey;
	private boolean isSnoozed = false;
	private LocalDate snoozeResumeSendDate;
	private Integer snoozeDaysToSnooze;
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
		try {
			InternetAddress emailAddr = new InternetAddress(oldEmail);
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new RuntimeException("Bad Old Email Address: " + oldEmail);
		}

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

	public boolean isSnoozed() {
		return isSnoozed;
	}

	public void setSnoozed(boolean isSnoozed) {
		this.isSnoozed = isSnoozed;
	}

	public LocalDate getSnoozeResumeSendDate() {
		return snoozeResumeSendDate;
	}

	public void setSnoozeResumeSendDate(LocalDate snoozeResumeSendDate) {
		if (snoozeResumeSendDate.isBefore(LocalDate.now())) {
			throw new RuntimeException("ResumeSendDate is before current date");
		}

		this.snoozeResumeSendDate = snoozeResumeSendDate;
	}

	public Integer getSnoozeDaysToSnooze() {
		return snoozeDaysToSnooze;
	}

	public void setSnoozeDaysToSnooze(Integer snoozeDaysToSnooze) {
		if (snoozeDaysToSnooze < 1) {
			throw new RuntimeException("DaysToSnooze must be greater than zero, but provided value is: " + snoozeDaysToSnooze);
		}

		this.snoozeDaysToSnooze = snoozeDaysToSnooze;
	}

	@Override
	public String toString() {
		return "UpdateRecipientOptions [listId=" + listId + ", oldEmail=" + oldEmail + ", recipientId=" + recipientId
				+ ", encodedRecipientId=" + encodedRecipientId + ", sendAutoReply=" + sendAutoReply + ", allowHtml="
				+ allowHtml + ", visitorKey=" + visitorKey + ", isSnoozed=" + isSnoozed + ", snoozeResumeSendDate="
				+ snoozeResumeSendDate + ", snoozeDaysToSnooze=" + snoozeDaysToSnooze + ", syncFields=" + syncFields
				+ ", columns=" + columns + "]";
	}

}
