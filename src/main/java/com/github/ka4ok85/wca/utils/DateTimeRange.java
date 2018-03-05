package com.github.ka4ok85.wca.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeRange {

	private final LocalDateTime startDateTime;
	private final LocalDateTime endDateTime;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

	public DateTimeRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		super();
		this.startDateTime = Objects.requireNonNull(startDateTime, "StartDateTime must not be null");
		this.endDateTime = Objects.requireNonNull(endDateTime, "EndDateTime must not be null");

		if (false == startDateTime.isBefore(endDateTime)) {
			throw new RuntimeException("Start DateTime must be before End DateTime. Start DateTime is "
					+ startDateTime.format(formatter) + ", End DateTime is " + endDateTime.format(formatter));
		}
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public String getFormattedStartDateTime() {
		return startDateTime.format(formatter);
	}

	public String getFormattedEndDateTime() {
		return endDateTime.format(formatter);
	}

	@Override
	public String toString() {
		return "DateTimeRange [startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + "]";
	}

}
