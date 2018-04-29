package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CreateContactListResponse extends AbstractResponse {

	private Long contactListId;

	public Long getContactListId() {
		return contactListId;
	}

	public void setContactListId(Long contactListId) {
		this.contactListId = contactListId;
	}

	@Override
	public String toString() {
		return "CreateContactListResponse [contactListId=" + contactListId + "]";
	}

}
