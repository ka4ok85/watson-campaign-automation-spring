package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColumnValueActionTest {
	@Test
	public void testGetValue() {
		assertEquals(ColumnValueAction.UPDATE.value(), new Integer(1));
	}
}
