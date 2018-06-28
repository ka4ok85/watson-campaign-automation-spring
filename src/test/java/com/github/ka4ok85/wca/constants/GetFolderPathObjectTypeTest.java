package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GetFolderPathObjectTypeTest {

	@Test
	public void testGetObjectType() {
		assertEquals(GetFolderPathObjectType.getObjectType("Mailing"), GetFolderPathObjectType.Mailing);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetObjectTypeRejectsBadValue() {
		GetFolderPathObjectType.getObjectType("Bad Object Type");
	}
}
