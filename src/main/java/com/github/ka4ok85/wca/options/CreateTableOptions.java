package com.github.ka4ok85.wca.options;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.ka4ok85.wca.constants.RelationalTableColumnType;
import com.github.ka4ok85.wca.options.containers.RelationalTableColumn;

public class CreateTableOptions extends AbstractOptions {

	private String tableName;
	private List<RelationalTableColumn> columns = new ArrayList<RelationalTableColumn>();

	public CreateTableOptions(String tableName) {
		super();
		if (tableName == null || tableName.isEmpty()) {
			throw new RuntimeException("Table Name must be non-empty String");
		}

		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public List<RelationalTableColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<RelationalTableColumn> columns) {
		for (RelationalTableColumn relationalTableColumn : columns) {
			if (relationalTableColumn.getType().equals(RelationalTableColumnType.SELECTION) && relationalTableColumn.getSelectionValues().size() == 0) {
				throw new RuntimeException("Selection Value required for Column with Selection Type. Column name: " + relationalTableColumn.getName());
			} else if (relationalTableColumn.getType().equals(RelationalTableColumnType.SELECTION) && relationalTableColumn.getSelectionValues().size() > 0) {
				relationalTableColumn.setSelectionValues(relationalTableColumn.getSelectionValues().stream().distinct().collect(Collectors.toList()));
			}
		}
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "CreateTableOptions [tableName=" + tableName + ", columns=" + columns + "]";
	}

	
}
