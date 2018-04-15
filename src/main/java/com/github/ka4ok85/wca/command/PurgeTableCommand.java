package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.PurgeTableOptions;
import com.github.ka4ok85.wca.response.PurgeTableResponse;

public class PurgeTableCommand extends AbstractCommand<PurgeTableResponse, PurgeTableOptions> {

	private static final String apiMethodName = "PurgeTable";
	private static final Logger log = LoggerFactory.getLogger(PurgeTableCommand.class);

}
