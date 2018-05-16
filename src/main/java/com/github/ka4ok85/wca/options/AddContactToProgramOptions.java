package com.github.ka4ok85.wca.options;

public class AddContactToProgramOptions extends AbstractOptions {
	private final Long programId;
	private final Long contactId;

	public AddContactToProgramOptions(Long programId, Long contactId) {
		super();
		if (contactId == null || contactId < 1) {
			throw new RuntimeException("Contact ID must be greater than zero. Provided Contact ID = " + contactId);
		}

		if (programId == null || programId < 1) {
			throw new RuntimeException("Program ID must be greater than zero. Provided Program ID = " + programId);
		}

		this.programId = programId;
		this.contactId = contactId;
	}

	public Long getProgramId() {
		return programId;
	}

	public Long getContactId() {
		return contactId;
	}

	@Override
	public String toString() {
		return "AddContactToProgramOptions [programId=" + programId + ", contactId=" + contactId + "]";
	}

}
