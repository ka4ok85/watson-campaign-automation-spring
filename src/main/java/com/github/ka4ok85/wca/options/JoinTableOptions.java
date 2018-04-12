package com.github.ka4ok85.wca.options;

import java.util.HashMap;
import java.util.Map;

import com.github.ka4ok85.wca.constants.Visibility;

public class JoinTableOptions extends AbstractOptions {

	private Long tableId;
	private String tableName;
	private Visibility tableVisibility;
	private Long listId;
	private String listName;
	private Visibility listVisibility;
	private boolean removeRelationship = false;
	private Map<String, String> mapFields = new HashMap<String, String>();

	public JoinTableOptions(Map<String, String> mapFields) {
		super();
		if (mapFields == null || mapFields.isEmpty()) {
			throw new RuntimeException("Map Fields can not be empty");
		}

		this.mapFields = mapFields;
	}

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

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		this.listId = listId;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		if (listName == null || listName.isEmpty()) {
			throw new RuntimeException("List Name must be non-empty String");
		}

		this.listName = listName;
	}

	public Visibility getListVisibility() {
		return listVisibility;
	}

	public void setListVisibility(Visibility listVisibility) {
		this.listVisibility = listVisibility;
	}

	public boolean getIsRemoveRelationship() {
		return removeRelationship;
	}

	public void setIsRemoveRelationship(boolean removeRelationship) {
		this.removeRelationship = removeRelationship;
	}

	public Map<String, String> getMapFields() {
		return mapFields;
	}

	@Override
	public String toString() {
		return "JoinTableOptions [tableId=" + tableId + ", tableName=" + tableName + ", tableVisibility="
				+ tableVisibility + ", listId=" + listId + ", listName=" + listName + ", listVisibility="
				+ listVisibility + ", removeRelationship=" + removeRelationship + ", mapFields=" + mapFields + "]";
	}

}
