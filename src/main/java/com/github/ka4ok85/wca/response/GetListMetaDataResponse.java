package com.github.ka4ok85.wca.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.response.containers.ListColumnLimited;

public class GetListMetaDataResponse extends AbstractResponse {

	private Long id;
	private String name;
	private Integer type;
	private Long size;
	private Long numOptOuts;
	private Long numUndeliverable;
	private LocalDateTime lastModified;
	private LocalDateTime lastConfigured;
	private LocalDateTime created;
	private Long parentDatabaseId;
	private Visibility visibility;
	private String smsKeyword;
	private String userId;
	private String organizationId;
	private boolean optInFormDefined;
	private boolean optOutFormDefined;
	private boolean profileFormDefined;
	private boolean optInAutoreplyDefined;
	private boolean profileAutoreplyDefined;
	private List<String> keyColumns = new ArrayList<String>();
	private List<ListColumnLimited> columns = new ArrayList<ListColumnLimited>();

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getNumOptOuts() {
		return numOptOuts;
	}

	public void setNumOptOuts(Long numOptOuts) {
		this.numOptOuts = numOptOuts;
	}

	public Long getNumUndeliverable() {
		return numUndeliverable;
	}

	public void setNumUndeliverable(Long numUndeliverable) {
		this.numUndeliverable = numUndeliverable;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public LocalDateTime getLastConfigured() {
		return lastConfigured;
	}

	public void setLastConfigured(LocalDateTime lastConfigured) {
		this.lastConfigured = lastConfigured;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public Long getParentDatabaseId() {
		return parentDatabaseId;
	}

	public void setParentDatabaseId(Long parentDatabaseId) {
		this.parentDatabaseId = parentDatabaseId;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public String getSmsKeyword() {
		return smsKeyword;
	}

	public void setSmsKeyword(String smsKeyword) {
		this.smsKeyword = smsKeyword;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public boolean isOptInFormDefined() {
		return optInFormDefined;
	}

	public void setOptInFormDefined(boolean optInFormDefined) {
		this.optInFormDefined = optInFormDefined;
	}

	public boolean isOptOutFormDefined() {
		return optOutFormDefined;
	}

	public void setOptOutFormDefined(boolean optOutFormDefined) {
		this.optOutFormDefined = optOutFormDefined;
	}

	public boolean isProfileFormDefined() {
		return profileFormDefined;
	}

	public void setProfileFormDefined(boolean profileFormDefined) {
		this.profileFormDefined = profileFormDefined;
	}

	public boolean isOptInAutoreplyDefined() {
		return optInAutoreplyDefined;
	}

	public void setOptInAutoreplyDefined(boolean optInAutoreplyDefined) {
		this.optInAutoreplyDefined = optInAutoreplyDefined;
	}

	public boolean isProfileAutoreplyDefined() {
		return profileAutoreplyDefined;
	}

	public void setProfileAutoreplyDefined(boolean profileAutoreplyDefined) {
		this.profileAutoreplyDefined = profileAutoreplyDefined;
	}

	public List<String> getKeyColumns() {
		return keyColumns;
	}

	public void setKeyColumns(List<String> keyColumns) {
		this.keyColumns = keyColumns;
	}

	public List<ListColumnLimited> getColumns() {
		return columns;
	}

	public void setColumns(List<ListColumnLimited> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "GetListMetaDataResponse [id=" + id + ", name=" + name + ", type=" + type + ", size=" + size
				+ ", numOptOuts=" + numOptOuts + ", numUndeliverable=" + numUndeliverable + ", lastModified="
				+ lastModified + ", lastConfigured=" + lastConfigured + ", created=" + created + ", parentDatabaseId="
				+ parentDatabaseId + ", visibility=" + visibility + ", smsKeyword=" + smsKeyword + ", userId=" + userId
				+ ", organizationId=" + organizationId + ", optInFormDefined=" + optInFormDefined
				+ ", optOutFormDefined=" + optOutFormDefined + ", profileFormDefined=" + profileFormDefined
				+ ", optInAutoreplyDefined=" + optInAutoreplyDefined + ", profileAutoreplyDefined="
				+ profileAutoreplyDefined + ", keyColumns=" + keyColumns + ", columns=" + columns + "]";
	}

}
