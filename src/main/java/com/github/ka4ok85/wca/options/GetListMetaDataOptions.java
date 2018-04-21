package com.github.ka4ok85.wca.options;

public class GetListMetaDataOptions extends AbstractOptions {
	private final Long listId;

	public GetListMetaDataOptions(Long listId) {
		super();
		if (listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		this.listId = listId;
	}

	public Long getListId() {
		return listId;
	}

	@Override
	public String toString() {
		return "GetListMetaDataOptions [listId=" + listId + "]";
	}

}
