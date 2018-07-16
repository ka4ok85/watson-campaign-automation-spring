package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ListTypeTest {
	@Test
	public void testGetListType() {
		assertEquals(ListType.getListType(6), ListType.SEED_LISTS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetListTypeRejectsBadValue() {
		ListType.getListType(100);
	}

	@Test
	public void testGetValue() {
		assertEquals(ListType.RELATIONAL_TABLES.value(), new Integer(15));
	}
}
