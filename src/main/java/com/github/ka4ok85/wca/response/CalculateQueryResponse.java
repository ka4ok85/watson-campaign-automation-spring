package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CalculateQueryResponse extends AbstractResponse {
	private Long JobId;
	private String description;
	private String listName;
	private Long listSize;
	private Long parentListId;
	private String parentListName;
	private String parentListType;
	private Long queryExpressionId;

	public Long getJobId() {
		return JobId;
	}

	public void setJobId(Long jobId) {
		JobId = jobId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public Long getListSize() {
		return listSize;
	}

	public void setListSize(Long listSize) {
		this.listSize = listSize;
	}

	public Long getParentListId() {
		return parentListId;
	}

	public void setParentListId(Long parentListId) {
		this.parentListId = parentListId;
	}

	public String getParentListName() {
		return parentListName;
	}

	public void setParentListName(String parentListName) {
		this.parentListName = parentListName;
	}

	public String getParentListType() {
		return parentListType;
	}

	public void setParentListType(String parentListType) {
		this.parentListType = parentListType;
	}

	public Long getQueryExpressionId() {
		return queryExpressionId;
	}

	public void setQueryExpressionId(Long queryExpressionId) {
		this.queryExpressionId = queryExpressionId;
	}

	@Override
	public String toString() {
		return "CalculateQueryResponse [JobId=" + JobId + ", description=" + description + ", listName=" + listName
				+ ", listSize=" + listSize + ", parentListId=" + parentListId + ", parentListName=" + parentListName
				+ ", parentListType=" + parentListType + ", queryExpressionId=" + queryExpressionId + "]";
	}

}
