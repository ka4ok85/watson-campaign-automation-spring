package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExportFormatTest {
	@Test
	public void testGetValue() {
		assertEquals(ExportFormat.PIPE.value(), "PIPE");
	}
}
