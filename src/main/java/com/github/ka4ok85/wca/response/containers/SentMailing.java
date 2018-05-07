package com.github.ka4ok85.wca.response.containers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.ka4ok85.wca.constants.Visibility;

public class SentMailing {

	private Long mailingId;
	private Long parentTemplateId;
	private Long reportId;
	private LocalDateTime scheduledDateTime;
	private String mailingName;
	private String listName;
	private Long listId;
	private Long parentListId;
	private String userName;
	private LocalDateTime sentDateTime;
	private Long numSent;
	private String subject;
	private Visibility visibility;
	private List<String> tags = new ArrayList<String>();

	public Long getMailingId() {
		return mailingId;
	}

	public void setMailingId(Long mailingId) {
		this.mailingId = mailingId;
	}

	public Long getParentTemplateId() {
		return parentTemplateId;
	}

	public void setParentTemplateId(Long parentTemplateId) {
		this.parentTemplateId = parentTemplateId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public LocalDateTime getScheduledDateTime() {
		return scheduledDateTime;
	}

	public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
		this.scheduledDateTime = scheduledDateTime;
	}

	public String getMailingName() {
		return mailingName;
	}

	public void setMailingName(String mailingName) {
		this.mailingName = mailingName;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public Long getParentListId() {
		return parentListId;
	}

	public void setParentListId(Long parentListId) {
		this.parentListId = parentListId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getSentDateTime() {
		return sentDateTime;
	}

	public void setSentDateTime(LocalDateTime sentDateTime) {
		this.sentDateTime = sentDateTime;
	}

	public Long getNumSent() {
		return numSent;
	}

	public void setNumSent(Long numSent) {
		this.numSent = numSent;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "SentMailing [mailingId=" + mailingId + ", parentTemplateId=" + parentTemplateId + ", reportId="
				+ reportId + ", scheduledDateTime=" + scheduledDateTime + ", mailingName=" + mailingName + ", listName="
				+ listName + ", listId=" + listId + ", parentListId=" + parentListId + ", userName=" + userName
				+ ", sentDateTime=" + sentDateTime + ", numSent=" + numSent + ", subject=" + subject + ", visibility="
				+ visibility + ", tags=" + tags + "]";
	}

}
