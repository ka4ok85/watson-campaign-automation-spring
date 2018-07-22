package com.github.ka4ok85.wca.options;

public class ListRecipientMailingsOptions extends AbstractOptions {
	private final Long listId;
	private final Long recipientId;

	public ListRecipientMailingsOptions(Long listId, Long recipientId) {
		super();
		if (listId == null || listId < 1) {
			throw new RuntimeException("List ID must be greater than zero. Provided List ID = " + listId);
		}

		if (recipientId == null || recipientId < 1) {
			throw new RuntimeException("Recipient ID must be greater than zero. Provided List ID = " + recipientId);
		}

		this.listId = listId;
		this.recipientId = recipientId;
	}

	public Long getListId() {
		return listId;
	}

	public Long getRecipientId() {
		return recipientId;
	}

	@Override
	public String toString() {
		return "ListRecipientMailingsOptions [listId=" + listId + ", recipientId=" + recipientId + "]";
	}

}
