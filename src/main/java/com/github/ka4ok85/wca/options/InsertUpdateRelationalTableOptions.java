package com.github.ka4ok85.wca.options;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertUpdateRelationalTableOptions extends AbstractOptions {

	private final Long tableId;
	private List<Map<String, String>> rows = new ArrayList<Map<String, String>>();

	public InsertUpdateRelationalTableOptions(Long tableId) {
		super();
		if (tableId < 1) {
			throw new RuntimeException("Table ID must be greater than zero. Provided Table ID = " + tableId);
		}

		this.tableId = tableId;
	}

	public List<Map<String, String>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, String>> rows) {
		if (rows == null || rows.isEmpty()) {
			throw new RuntimeException("Rows can not be empty");
		}

		this.rows = rows;
	}

	public Long getTableId() {
		return tableId;
	}

	@Override
	public String toString() {
		return "InsertUpdateRelationalTableOptions [tableId=" + tableId + ", rows=" + rows + "]";
	}

}
