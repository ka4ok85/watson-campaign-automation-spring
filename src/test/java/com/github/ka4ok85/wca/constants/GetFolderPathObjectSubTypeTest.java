package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GetFolderPathObjectSubTypeTest {
	@Test
	public void testGetObjectSubType() {
		assertEquals(GetFolderPathObjectSubType.getObjectSubType("Database"), GetFolderPathObjectSubType.Database);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetObjectSubTypeRejectsBadValue() {
		GetFolderPathObjectSubType.getObjectSubType("Bad Object Sub Type");
	}
	
	@Test
	public void testGetValue() {
		assertEquals(GetFolderPathObjectSubType.Database.value(), "Database");
	}
}
