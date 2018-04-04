package com.github.ka4ok85.wca.options;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class OptOutRecipientOptions extends AbstractOptions {
	private final Long listId;
	private String email;
	private String recipientId; // encoded value
	private Long mailingId;
	private String jobId; // encoded value
	private Map<String, String> columns = new HashMap<String, String>();

	public OptOutRecipientOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}
		this.listId = listId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public Long getMailingId() {
		return mailingId;
	}

	public void setMailingId(Long mailingId) {
		this.mailingId = mailingId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
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
		return "OptOutRecipientOptions [listId=" + listId + ", recipientId=" + recipientId + ", mailingId=" + mailingId
				+ ", jobId=" + jobId + "]";
	}

}
