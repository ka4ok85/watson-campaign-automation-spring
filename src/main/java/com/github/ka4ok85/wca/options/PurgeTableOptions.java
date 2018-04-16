package com.github.ka4ok85.wca.options;

import java.time.LocalDateTime;

import com.github.ka4ok85.wca.constants.Visibility;

public class PurgeTableOptions extends AbstractOptions {

	private Long tableId;
	private String tableName;
	private Visibility tableVisibility;
	private LocalDateTime deleteBefore;

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

	public LocalDateTime getDeleteBefore() {
		return deleteBefore;
	}

	public void setDeleteBefore(LocalDateTime deleteBefore) {
		if (deleteBefore == null) {
			throw new RuntimeException("Delete Before must be non-null");
		}

		this.deleteBefore = deleteBefore;
	}

	@Override
	public String toString() {
		return "PurgeTableOptions [tableId=" + tableId + ", tableName=" + tableName + ", tableVisibility="
				+ tableVisibility + ", deleteBefore=" + deleteBefore + "]";
	}

}
