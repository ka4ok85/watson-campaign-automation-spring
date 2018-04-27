package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PurgeDataResponse extends AbstractResponse {
	private String description;
	private String purgedContactListName;
	private Long purgedContactListId;
	private Long purgedContactListSize;
	private String targetListName;
	private String sourceListName;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPurgedContactListName() {
		return purgedContactListName;
	}

	public void setPurgedContactListName(String purgedContactListName) {
		this.purgedContactListName = purgedContactListName;
	}

	public Long getPurgedContactListId() {
		return purgedContactListId;
	}

	public void setPurgedContactListId(Long purgedContactListId) {
		this.purgedContactListId = purgedContactListId;
	}

	public Long getPurgedContactListSize() {
		return purgedContactListSize;
	}

	public void setPurgedContactListSize(Long purgedContactListSize) {
		this.purgedContactListSize = purgedContactListSize;
	}

	public String getTargetListName() {
		return targetListName;
	}

	public void setTargetListName(String targetListName) {
		this.targetListName = targetListName;
	}

	public String getSourceListName() {
		return sourceListName;
	}

	public void setSourceListName(String sourceListName) {
		this.sourceListName = sourceListName;
	}

	@Override
	public String toString() {
		return "PurgeDataResponse [description=" + description + ", purgedContactListName=" + purgedContactListName
				+ ", purgedContactListId=" + purgedContactListId + ", purgedContactListSize=" + purgedContactListSize
				+ ", targetListName=" + targetListName + ", sourceListName=" + sourceListName + "]";
	}

}
