package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ListColumnTypeTest {
	@Test
	public void testGetListColumnType() {
		assertEquals(ListColumnType.getListColumnType(1), ListColumnType.YESNO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetListColumnTypeRejectsBadValue() {
		ListColumnType.getListColumnType(100);
	}

	@Test
	public void testGetValue() {
		assertEquals(ListColumnType.COUNTRY.value(), new Integer(5));
	}
}
