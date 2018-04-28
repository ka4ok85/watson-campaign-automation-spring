package com.github.ka4ok85.wca.options;

import java.time.LocalDateTime;
import java.util.Objects;

public class GetReportIdByDateOptions extends AbstractOptions {
	private final Long mailingId;
	private final LocalDateTime dateStart;
	private final LocalDateTime dateEnd;

	public GetReportIdByDateOptions(Long mailingId, LocalDateTime dateStart, LocalDateTime dateEnd) {
		super();
		if (mailingId < 1) {
			throw new RuntimeException("Target ID must be greater than zero. Provided Target ID = " + mailingId);
		}

		Objects.requireNonNull(dateStart, "Date Start must not be null");
		Objects.requireNonNull(dateEnd, "Date End must not be null");

		this.mailingId = mailingId;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}

	public Long getMailingId() {
		return mailingId;
	}

	public LocalDateTime getDateStart() {
		return dateStart;
	}

	public LocalDateTime getDateEnd() {
		return dateEnd;
	}

	@Override
	public String toString() {
		return "GetReportIdByDateOptions [mailingId=" + mailingId + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
				+ "]";
	}

}
