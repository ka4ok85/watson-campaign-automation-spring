package com.github.ka4ok85.wca.constants;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExportFormatWebTrackingDataExportTest {
	@Test
	public void testGetValue() {
		assertEquals(ExportFormatWebTrackingDataExport.PIPE.value(), new Integer(1));
	}
}
