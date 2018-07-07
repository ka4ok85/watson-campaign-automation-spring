package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.ColumnValueAction;

public class SetColumnValueOptions extends AbstractOptions {
	private final Long listId;
	private final String columnName;
	private String columnValue;
	private final ColumnValueAction columnValueAction;

	public SetColumnValueOptions(Long listId, String columnName, ColumnValueAction columnValueAction) {
		super();

		if (listId == null || listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		if (columnName == null || columnName.isEmpty()) {
			throw new RuntimeException("Column Name can not be empty");
		}

		if (columnValueAction == null) {
			throw new RuntimeException("Column Value Action can not be null");
		}

		this.listId = listId;
		this.columnName = columnName;
		this.columnValueAction = columnValueAction;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	public Long getListId() {
		return listId;
	}

	public String getColumnName() {
		return columnName;
	}

	public ColumnValueAction getColumnValueAction() {
		return columnValueAction;
	}

	@Override
	public String toString() {
		return "SetColumnValueOptions [listId=" + listId + ", columnName=" + columnName + ", columnValue=" + columnValue
				+ ", columnValueAction=" + columnValueAction + "]";
	}

}
