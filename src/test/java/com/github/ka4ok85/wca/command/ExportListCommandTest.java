package com.github.ka4ok85.wca.command;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.ka4ok85.wca.options.ExportListOptions;

public class ExportListCommandTest {

	
	@Test(expected = NullPointerException.class)
	public void testBuildXmlDoesNotAcceptNullOptions() {

		ExportListCommand command = new ExportListCommand();
		ExportListOptions options = null;
		command.buildXmlRequest(options);
	}

}
