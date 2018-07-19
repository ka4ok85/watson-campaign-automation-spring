package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CreatedFromTest {
	@Test
	public void testGetValue() {
		assertEquals(CreatedFrom.ADDED_MANUALLY.value(), new Integer(1));
	}
}
