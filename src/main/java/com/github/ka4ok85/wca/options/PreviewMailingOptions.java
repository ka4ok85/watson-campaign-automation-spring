package com.github.ka4ok85.wca.options;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class PreviewMailingOptions extends AbstractOptions {

	private final Long mailingId;
	private String recipientEmail;

	public PreviewMailingOptions(Long mailingId) {
		super();
		if (mailingId == null || mailingId < 1) {
			throw new RuntimeException("Mailing ID must be greater than zero. Provided Query ID = " + mailingId);
		}
		this.mailingId = mailingId;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public void setRecipientEmail(String recipientEmail) {
		try {
			InternetAddress emailAddr = new InternetAddress(recipientEmail);
			emailAddr.validate();
		} catch (AddressException ex) {
			throw new RuntimeException("Bad Recipient Email: " + recipientEmail);
		}

		this.recipientEmail = recipientEmail;
	}

	public Long getMailingId() {
		return mailingId;
	}

	@Override
	public String toString() {
		return "PreviewMailingOptions [mailingId=" + mailingId + ", recipientEmail=" + recipientEmail + "]";
	}

}
