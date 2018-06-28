package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VisibilityTest {
	@Test
	public void testGetVisibility() {
		assertEquals(Visibility.getVisibility(1), Visibility.SHARED);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetVisibilityRejectsBadValue() {
		Visibility.getVisibility(10);
	}

	@Test
	public void testGetVisibilityByAlias() {
		assertEquals(Visibility.getVisibilityByAlias("Shared"), Visibility.SHARED);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetVisibilityByAliasRejectsBadValue() {
		Visibility.getVisibilityByAlias("Bad Alias");
	}

	@Test
	public void testGetValue() {
		assertEquals(Visibility.PRIVATE.value(), new Integer(0));
	}
}
