package com.github.ka4ok85.wca.response.containers;

import java.time.LocalDateTime;

import com.github.ka4ok85.wca.constants.Visibility;

public class MailingTemplate {

	private Long mailingId;
	private String mailingName;
	private String subject;
	private LocalDateTime lastModified;
	private Visibility visibility;
	private String userId;
	private boolean flaggedForBackup;
	private boolean allowCrmBlock;

	public Long getMailingId() {
		return mailingId;
	}

	public void setMailingId(Long mailingId) {
		this.mailingId = mailingId;
	}

	public String getMailingName() {
		return mailingName;
	}

	public void setMailingName(String mailingName) {
		this.mailingName = mailingName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isFlaggedForBackup() {
		return flaggedForBackup;
	}

	public void setFlaggedForBackup(boolean flaggedForBackup) {
		this.flaggedForBackup = flaggedForBackup;
	}

	public boolean isAllowCrmBlock() {
		return allowCrmBlock;
	}

	public void setAllowCrmBlock(boolean allowCrmBlock) {
		this.allowCrmBlock = allowCrmBlock;
	}

	@Override
	public String toString() {
		return "MailingTemplate [mailingId=" + mailingId + ", mailingName=" + mailingName + ", subject=" + subject
				+ ", lastModified=" + lastModified + ", visibility=" + visibility + ", userId=" + userId
				+ ", flaggedForBackup=" + flaggedForBackup + ", allowCrmBlock=" + allowCrmBlock + "]";
	}

}
