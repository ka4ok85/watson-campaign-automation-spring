package com.github.ka4ok85.wca.response;

public class CreateTableResponse extends AbstractResponse {

	private Long tableId;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	@Override
	public String toString() {
		return "CreateTableResponse [tableId=" + tableId + "]";
	}

}
