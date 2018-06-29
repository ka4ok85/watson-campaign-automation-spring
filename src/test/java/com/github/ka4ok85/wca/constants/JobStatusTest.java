package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JobStatusTest {
	@Test
	public void testGetValue() {
		assertEquals(JobStatus.CANCELED.value(), "CANCELED");
	}
}
