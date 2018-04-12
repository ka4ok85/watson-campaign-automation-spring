package com.github.ka4ok85.wca.options.containers;

import java.util.ArrayList;
import java.util.List;

import com.github.ka4ok85.wca.constants.RelationalTableColumnType;

public class RelationalTableColumn {
	private String name;
	private RelationalTableColumnType type;
	private boolean isRequired = false;
	private boolean isKeyColumn = false;
	private String defaultValue;
	private List<String> selectionValues = new ArrayList<String>();

	public RelationalTableColumn(String name, RelationalTableColumnType type) {
		super();
		if (name == null || name.isEmpty()) {
			throw new RuntimeException("Column Name must be non-empty String");
		}

		if (type == null) {
			throw new RuntimeException("Column Type must not be null");
		}

		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public RelationalTableColumnType getType() {
		return type;
	}

	public boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public boolean getIsKeyColumn() {
		return isKeyColumn;
	}

	public void setIsKeyColumn(boolean isKeyColumn) {
		this.isKeyColumn = isKeyColumn;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<String> getSelectionValues() {
		return selectionValues;
	}

	public void setSelectionValues(List<String> selectionValues) {
		this.selectionValues = selectionValues;
	}

	@Override
	public String toString() {
		return "RelationalTableColumn [name=" + name + ", type=" + type + ", isRequired=" + isRequired
				+ ", isKeyColumn=" + isKeyColumn + ", defaultValue=" + defaultValue + ", selectionValues="
				+ selectionValues + "]";
	}

}
