package com.github.ka4ok85.wca.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.ka4ok85.wca.response.containers.MailingTemplate;

@Component
@Scope("prototype")
public class GetMailingTemplatesResponse extends AbstractResponse {

	private List<MailingTemplate> mailingTempaltes = new ArrayList<MailingTemplate>();

	public List<MailingTemplate> getMailingTempaltes() {
		return mailingTempaltes;
	}

	public void setMailingTempaltes(List<MailingTemplate> mailingTempaltes) {
		this.mailingTempaltes = mailingTempaltes;
	}

	@Override
	public String toString() {
		return "GetMailingTemplatesResponse [mailingTempaltes=" + mailingTempaltes + "]";
	}

}
