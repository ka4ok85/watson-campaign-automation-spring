package com.github.ka4ok85.wca.options;

import com.github.ka4ok85.wca.constants.Visibility;

public class DeleteTableOptions extends AbstractOptions {

	private Long tableId;
	private String tableName;
	private Visibility tableVisibility;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		if (tableId < 1) {
			throw new RuntimeException("Table ID must be greater than zero. Provided Table ID = " + tableId);
		}

		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		if (tableName == null || tableName.isEmpty()) {
			throw new RuntimeException("Table Name must be non-empty String");
		}

		this.tableName = tableName;
	}

	public Visibility getTableVisibility() {
		return tableVisibility;
	}

	public void setTableVisibility(Visibility tableVisibility) {
		this.tableVisibility = tableVisibility;
	}

	@Override
	public String toString() {
		return "DeleteTableOptions [tableId=" + tableId + ", tableName=" + tableName + ", tableVisibility="
				+ tableVisibility + "]";
	}

}
