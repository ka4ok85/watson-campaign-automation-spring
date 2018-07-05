package com.github.ka4ok85.wca.options;

import java.util.ArrayList;
import java.util.List;

import com.github.ka4ok85.wca.constants.ListColumnType;

public class AddListColumnOptions extends AbstractOptions {
	private final Long listId;
	private final String columnName;
	private final ListColumnType columnType;
	private final String defaultValue;
	private List<String> selectionValues = new ArrayList<String>();

	/**
	 * @param listId
	 *            - The ID of the Watson Campaign Automation database the column
	 *            is being added to
	 * @param columnName
	 *            - The name of the column being added
	 * @param columnType
	 *            - Defines what type of column to create
	 * @param defaultValue
	 *            - Specifies the default value for the new column. For a
	 *            multi-select column, the default value is a semi-colon
	 *            delimited list of values
	 * 
	 */
	public AddListColumnOptions(Long listId, String columnName, ListColumnType columnType, String defaultValue) {
		super();
		if (listId == null || listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		if (columnName == null || columnName.isEmpty()) {
			throw new RuntimeException("Column Name must be non-empty String");
		}

		if (columnType == null) {
			throw new RuntimeException("Column Type can not be null");
		}

		this.listId = listId;
		this.columnName = columnName;
		this.columnType = columnType;
		this.defaultValue = defaultValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public List<String> getSelectionValues() {
		return selectionValues;
	}

	public void setSelectionValues(List<String> selectionValues) {
		this.selectionValues = selectionValues;
	}

	public Long getListId() {
		return listId;
	}

	public String getColumnName() {
		return columnName;
	}

	public ListColumnType getColumnType() {
		return columnType;
	}

	@Override
	public String toString() {
		return "AddListColumnOptions [listId=" + listId + ", columnName=" + columnName + ", columnType=" + columnType
				+ ", defaultValue=" + defaultValue + ", selectionValues=" + selectionValues + "]";
	}

}
