package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FileEncodingTest {
	@Test
	public void testGetFileEncoding() {
		assertEquals(FileEncoding.getFileEncoding("iso-8859-1"), FileEncoding.ISO_8859_1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetFileEncodingRejectsBadValue() {
		FileEncoding.getFileEncoding("Bad File Encoding");
	}

	@Test
	public void testGetValue() {
		assertEquals(FileEncoding.ISO_8859_1.value(), "iso-8859-1");
	}
}
