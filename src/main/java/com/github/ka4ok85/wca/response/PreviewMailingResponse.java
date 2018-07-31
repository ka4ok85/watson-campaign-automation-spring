package com.github.ka4ok85.wca.response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PreviewMailingResponse extends AbstractResponse {
	private String htmlBody;
	private String aolBody;
	private String textBody;
	private String spamScore;

	public String getHtmlBody() {
		return htmlBody;
	}

	public void setHtmlBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}

	public String getAolBody() {
		return aolBody;
	}

	public void setAolBody(String aolBody) {
		this.aolBody = aolBody;
	}

	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}

	public String getSpamScore() {
		return spamScore;
	}

	public void setSpamScore(String spamScore) {
		this.spamScore = spamScore;
	}

	@Override
	public String toString() {
		return "PreviewMailingResponse [htmlBody=" + htmlBody + ", aolBody=" + aolBody + ", textBody=" + textBody
				+ ", spamScore=" + spamScore + "]";
	}

}
