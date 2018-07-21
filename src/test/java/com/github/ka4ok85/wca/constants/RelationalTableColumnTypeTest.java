package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RelationalTableColumnTypeTest {
	@Test
	public void testGetValue() {
		assertEquals(RelationalTableColumnType.PHONE_NUMBER.value(), "PHONE_NUMBER");
	}
}
