package com.github.ka4ok85.wca.utils;

import java.util.ArrayList;
import java.util.List;

import com.github.ka4ok85.wca.constants.ListColumnType;

public class ImportMapperListColumn {
	private final String name;
	private final ListColumnType listColumnType;
	private boolean isRequired = false;
	private boolean isKeyColumn = false;
	private boolean isIncluded = false;
	private final String defaultValue;
	private List<String> selectionValues = new ArrayList<String>();

	public ImportMapperListColumn(String name, ListColumnType listColumnType, boolean isRequired, boolean isKeyColumn,
			boolean isIncluded, String defaultValue) {
		super();

		if (name == null || name.trim().isEmpty()) {
			throw new RuntimeException("Name must be non-empty String. Provided Name = " + name);
		}

		if (listColumnType == null) {
			throw new RuntimeException("List Column Type can not be null");
		}

		this.name = name;
		this.listColumnType = listColumnType;
		this.isRequired = isRequired;
		this.isKeyColumn = isKeyColumn;
		this.isIncluded = isIncluded;
		this.defaultValue = defaultValue;
	}

	protected boolean isRequired() {
		return isRequired;
	}

	protected void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	protected boolean isKeyColumn() {
		return isKeyColumn;
	}

	protected void setKeyColumn(boolean isKeyColumn) {
		this.isKeyColumn = isKeyColumn;
	}

	protected boolean isIncluded() {
		return isIncluded;
	}

	protected void setIncluded(boolean isIncluded) {
		this.isIncluded = isIncluded;
	}

	protected List<String> getSelectionValues() {
		return selectionValues;
	}

	protected void setSelectionValues(List<String> selectionValues) {
		this.selectionValues = selectionValues;
	}

	protected String getName() {
		return name;
	}

	protected ListColumnType getListColumnType() {
		return listColumnType;
	}

	protected String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public String toString() {
		return "ImportMapperListColumn [name=" + name + ", listColumnType=" + listColumnType + ", isRequired="
				+ isRequired + ", isKeyColumn=" + isKeyColumn + ", isIncluded=" + isIncluded + ", defaultValue="
				+ defaultValue + ", selectionValues=" + selectionValues + "]";
	}

}
