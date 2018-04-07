package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.ListType;
import com.github.ka4ok85.wca.constants.Visibility;

public class GetListsOptions extends AbstractOptions {

	private Visibility visibility = Visibility.SHARED;
	private ListType listType = ListType.DATABASES_CONTACT_LISTS_QUERIES;
	private Long folderId;
	private boolean includeAllLists = false;
	private boolean includeTags = false;

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public ListType getListType() {
		return listType;
	}

	public void setListType(ListType listType) {
		this.listType = listType;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public boolean isIncludeAllLists() {
		return includeAllLists;
	}

	public void setIncludeAllLists(boolean includeAllLists) {
		this.includeAllLists = includeAllLists;
	}

	public boolean isIncludeTags() {
		return includeTags;
	}

	public void setIncludeTags(boolean includeTags) {
		this.includeTags = includeTags;
	}

	@Override
	public String toString() {
		return "GetListsOptions [visibility=" + visibility + ", listType=" + listType + ", folderId=" + folderId
				+ ", includeAllLists=" + includeAllLists + ", includeTags=" + includeTags + "]";
	}

}
