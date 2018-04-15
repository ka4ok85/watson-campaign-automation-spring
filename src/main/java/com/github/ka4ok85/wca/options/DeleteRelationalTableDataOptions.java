package com.github.ka4ok85.wca.options;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeleteRelationalTableDataOptions extends AbstractOptions {

	private final Long tableId;
	private List<Map<String, String>> rows = new ArrayList<Map<String, String>>();

	public DeleteRelationalTableDataOptions(Long tableId) {
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
		this.rows = rows;
	}

	public Long getTableId() {
		return tableId;
	}

	@Override
	public String toString() {
		return "DeleteRelationalTableDataOptions [tableId=" + tableId + ", rows=" + rows + "]";
	}

}
