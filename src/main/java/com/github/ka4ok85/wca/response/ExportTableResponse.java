package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ExportTableResponse extends AbstractResponse {
	private String description;
	private String remoteFilePath;
	private String remoteFileName;
	private String listName;
	private Long listId;
	private Long numProcessed;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemoteFilePath() {
		return remoteFilePath;
	}

	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
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

	public Long getNumProcessed() {
		return numProcessed;
	}

	public void setNumProcessed(Long numProcessed) {
		this.numProcessed = numProcessed;
	}

	@Override
	public String toString() {
		return "ExportTableResponse [description=" + description + ", remoteFilePath=" + remoteFilePath
				+ ", remoteFileName=" + remoteFileName + ", listName=" + listName + ", listId=" + listId
				+ ", numProcessed=" + numProcessed + "]";
	}

}
