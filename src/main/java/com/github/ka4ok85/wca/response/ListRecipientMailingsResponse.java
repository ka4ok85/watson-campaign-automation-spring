package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.response.containers.RecipientMailing;

@Component
@Scope("prototype")
public class ListRecipientMailingsResponse extends AbstractResponse {
	private List<RecipientMailing> mailings = new ArrayList<RecipientMailing>();

	public List<RecipientMailing> getMailings() {
		return mailings;
	}

	public void setMailings(List<RecipientMailing> mailings) {
		this.mailings = mailings;
	}

	@Override
	public String toString() {
		return "ListRecipientMailingsResponse [mailings=" + mailings + "]";
	}

}
