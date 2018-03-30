package com.github.ka4ok85.wca.response;

public class AddRecipientResponse extends AbstractResponse {

	private Long recipientId;
	private boolean visitorAssocation;

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	public boolean isVisitorAssocation() {
		return visitorAssocation;
	}

	public void setVisitorAssocation(boolean visitorAssocation) {
		this.visitorAssocation = visitorAssocation;
	}

	@Override
	public String toString() {
		return "AddRecipientResponse [recipientId=" + recipientId + ", visitorAssocation=" + visitorAssocation + "]";
	}

}
