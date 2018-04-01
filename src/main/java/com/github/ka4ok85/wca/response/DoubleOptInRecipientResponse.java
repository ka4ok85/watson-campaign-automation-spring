package com.github.ka4ok85.wca.response;

public class DoubleOptInRecipientResponse extends AbstractResponse {

	private Long recipientId;

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	@Override
	public String toString() {
		return "DoubleOptInRecipientResponse [recipientId=" + recipientId + "]";
	}

}
