package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ImportMapperActionTest {
	@Test
	public void testGetValue() {
		assertEquals(ImportMapperAction.OPT_OUT.value(), "OPT_OUT");
	}
}
