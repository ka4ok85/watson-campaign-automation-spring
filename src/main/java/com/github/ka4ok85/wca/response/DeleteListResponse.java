package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DeleteListResponse extends AbstractResponse {
	private Long jobId;
	private String description;
	private Long listId;
	private String listName;
	private boolean ignoreDependentMailings;
	private Long numberRemoved;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public boolean isIgnoreDependentMailings() {
		return ignoreDependentMailings;
	}

	public void setIgnoreDependentMailings(boolean ignoreDependentMailings) {
		this.ignoreDependentMailings = ignoreDependentMailings;
	}

	public Long getNumberRemoved() {
		return numberRemoved;
	}

	public void setNumberRemoved(Long numberRemoved) {
		this.numberRemoved = numberRemoved;
	}

	@Override
	public String toString() {
		return "DeleteListResponse [jobId=" + jobId + ", description=" + description + ", listId=" + listId
				+ ", listName=" + listName + ", ignoreDependentMailings=" + ignoreDependentMailings + ", numberRemoved="
				+ numberRemoved + "]";
	}

}
