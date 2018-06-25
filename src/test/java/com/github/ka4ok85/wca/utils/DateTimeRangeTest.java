package com.github.ka4ok85.wca.utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class DateTimeRangeTest {

	@Test
	public void testConstructor() {
		LocalDateTime startDate = LocalDateTime.of(2018, 02, 01, 12, 23, 34);
		LocalDateTime endDate = LocalDateTime.of(2019, 03, 04, 13, 44, 55);
		DateTimeRange lastModifiedRange = new DateTimeRange(startDate, endDate);

		assertEquals(lastModifiedRange.getStartDateTime(), startDate);
		assertEquals(lastModifiedRange.getEndDateTime(), endDate);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		String startDateString = startDate.format(formatter);
		String endDateString = endDate.format(formatter);

		assertEquals(startDateString, "02/01/2018 12:23:34");
		assertEquals(endDateString, "03/04/2019 13:44:55");
	}

	@Test(expected = RuntimeException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {
		LocalDateTime startDate = LocalDateTime.of(2019, 02, 01, 12, 23, 34);
		LocalDateTime endDate = LocalDateTime.of(2018, 03, 04, 13, 44, 55);
		DateTimeRange lastModifiedRange = new DateTimeRange(startDate, endDate);
		System.out.println(lastModifiedRange);
	}
}
