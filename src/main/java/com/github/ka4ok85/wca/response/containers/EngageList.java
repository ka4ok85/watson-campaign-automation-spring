package com.github.ka4ok85.wca.response.containers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.ka4ok85.wca.constants.ListType;
import com.github.ka4ok85.wca.constants.Visibility;

public class EngageList {

	private Long id;
	private String name;
	private ListType type;
	private Long size;
	private Long numberOptOuts;
	private Long numberUndeliverables;
	private LocalDateTime lastModifiedDate;
	private Visibility visibility;
	private String parentName;
	private String userId;
	private Long folderId;
	private boolean isFolder;
	private boolean flaggedForBackup;
	private Long suppressionList;
	private boolean isDatabaseTemplate;
	private List<String> tags;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ListType getType() {
		return type;
	}

	public void setType(ListType type) {
		this.type = type;
	}

	public void setType(int type) {
		this.type = ListType.getListType(type);
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getNumberOptOuts() {
		return numberOptOuts;
	}

	public void setNumberOptOuts(Long numberOptOuts) {
		this.numberOptOuts = numberOptOuts;
	}

	public Long getNumberUndeliverables() {
		return numberUndeliverables;
	}

	public void setNumberUndeliverables(Long numberUndeliverables) {
		this.numberUndeliverables = numberUndeliverables;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedText) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		this.lastModifiedDate = LocalDateTime.parse(lastModifiedText, formatter);
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = Visibility.getVisibility(visibility);
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public boolean isFlaggedForBackup() {
		return flaggedForBackup;
	}

	public void setFlaggedForBackup(boolean flaggedForBackup) {
		this.flaggedForBackup = flaggedForBackup;
	}

	public Long getSuppressionList() {
		return suppressionList;
	}

	public void setSuppressionList(Long suppressionList) {
		this.suppressionList = suppressionList;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public boolean isDatabaseTemplate() {
		return isDatabaseTemplate;
	}

	public void setIsDatabaseTemplate(boolean isDatabaseTemplate) {
		this.isDatabaseTemplate = isDatabaseTemplate;
	}

	@Override
	public String toString() {
		return "EngageList [id=" + id + ", name=" + name + ", type=" + type + ", size=" + size + ", numberOptOuts="
				+ numberOptOuts + ", numberUndeliverables=" + numberUndeliverables + ", lastModifiedDate="
				+ lastModifiedDate + ", visibility=" + visibility + ", parentName=" + parentName + ", userId=" + userId
				+ ", folderId=" + folderId + ", isFolder=" + isFolder + ", flaggedForBackup=" + flaggedForBackup
				+ ", suppressionList=" + suppressionList + ", isDatabaseTemplate=" + isDatabaseTemplate + ", tags="
				+ tags + "]";
	}

}
