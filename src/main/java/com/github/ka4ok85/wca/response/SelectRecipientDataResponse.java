package com.github.ka4ok85.wca.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectRecipientDataResponse extends AbstractResponse {
	private String email;
	private Long recipientId;
	private int emailType;
	private LocalDateTime lastModified;
	private int createdFrom;
	private LocalDateTime optedIn;
	private LocalDateTime optedOut;
	private LocalDateTime resumeSendDate;
	private String organiztionId;
	private String crmLeadSource;
	private Map<String, String> columns = new HashMap<String, String>();
	private List<Long> contactLists = new ArrayList<Long>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	public int getEmailType() {
		return emailType;
	}

	public void setEmailType(int emailType) {
		this.emailType = emailType;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public int getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(int createdFrom) {
		this.createdFrom = createdFrom;
	}

	public LocalDateTime getOptedIn() {
		return optedIn;
	}

	public void setOptedIn(LocalDateTime optedIn) {
		this.optedIn = optedIn;
	}

	public LocalDateTime getOptedOut() {
		return optedOut;
	}

	public void setOptedOut(LocalDateTime optedOut) {
		this.optedOut = optedOut;
	}

	public LocalDateTime getResumeSendDate() {
		return resumeSendDate;
	}

	public void setResumeSendDate(LocalDateTime resumeSendDate) {
		this.resumeSendDate = resumeSendDate;
	}

	public String getOrganiztionId() {
		return organiztionId;
	}

	public void setOrganiztionId(String organiztionId) {
		this.organiztionId = organiztionId;
	}

	public String getCrmLeadSource() {
		return crmLeadSource;
	}

	public void setCrmLeadSource(String crmLeadSource) {
		this.crmLeadSource = crmLeadSource;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}

	public List<Long> getContactLists() {
		return contactLists;
	}

	public void setContactLists(List<Long> contactLists) {
		this.contactLists = contactLists;
	}

	@Override
	public String toString() {
		return "SelectRecipientDataResponse [email=" + email + ", recipientId=" + recipientId + ", emailType="
				+ emailType + ", lastModified=" + lastModified + ", createdFrom=" + createdFrom + ", optedIn=" + optedIn
				+ ", optedOut=" + optedOut + ", resumeSendDate=" + resumeSendDate + ", organiztionId=" + organiztionId
				+ ", crmLeadSource=" + crmLeadSource + ", columns=" + columns + ", contactLists=" + contactLists + "]";
	}

}
