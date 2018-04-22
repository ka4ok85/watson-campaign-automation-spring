package com.github.ka4ok85.wca.response.containers;

import java.util.ArrayList;
import java.util.List;

import com.github.ka4ok85.wca.constants.ListColumnType;

public class ListColumnLimited {
	private String name;
	private String defaultValue;
	private ListColumnType type;
	private List<String> selectionValues = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public ListColumnType getType() {
		return type;
	}

	public void setType(ListColumnType type) {
		this.type = type;
	}

	public List<String> getSelectionValues() {
		return selectionValues;
	}

	public void setSelectionValues(List<String> selectionValues) {
		this.selectionValues = selectionValues;
	}

	@Override
	public String toString() {
		return "ListColumnLimited [name=" + name + ", defaultValue=" + defaultValue + ", type=" + type
				+ ", selectionValues=" + selectionValues + "]";
	}

}
