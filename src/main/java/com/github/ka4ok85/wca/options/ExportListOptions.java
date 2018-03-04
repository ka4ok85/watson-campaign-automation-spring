package com.github.ka4ok85.wca.options;

public class ExportListOptions extends AbstractOptions {

	private Long ListId;

	public ExportListOptions(Long listId) {
		super();
		ListId = listId;
	}

	public Long getListId() {
		return ListId;
	}

	public void setListId(Long listId) {
		ListId = listId;
	}

}
