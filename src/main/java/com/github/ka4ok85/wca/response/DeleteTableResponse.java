package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DeleteTableResponse extends AbstractResponse {
	private Long jobId;
	private String description;
	private Long numberRemoved;
	private boolean ignoreDependentMailings;
	private boolean isKeepListDetails;
	private Long listId;
	private String listName;

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

	public Long getNumberRemoved() {
		return numberRemoved;
	}

	public void setNumberRemoved(Long numberRemoved) {
		this.numberRemoved = numberRemoved;
	}

	public boolean isIgnoreDependentMailings() {
		return ignoreDependentMailings;
	}

	public void setIgnoreDependentMailings(boolean ignoreDependentMailings) {
		this.ignoreDependentMailings = ignoreDependentMailings;
	}

	public boolean isKeepListDetails() {
		return isKeepListDetails;
	}

	public void setKeepListDetails(boolean isKeepListDetails) {
		this.isKeepListDetails = isKeepListDetails;
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

	@Override
	public String toString() {
		return "DeleteTableResponse [jobId=" + jobId + ", description=" + description + ", numberRemoved="
				+ numberRemoved + ", ignoreDependentMailings=" + ignoreDependentMailings + ", isKeepListDetails="
				+ isKeepListDetails + ", listId=" + listId + ", listName=" + listName + "]";
	}

}
