package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImportFileFormatTest {
	@Test
	public void testGetValue() {
		assertEquals(ImportFileFormat.PIPE.value(), new Integer(2));
	}
}
