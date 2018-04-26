package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.PurgeDataOptions;
import com.github.ka4ok85.wca.response.PurgeDataResponse;

public class PurgeDataCommand extends AbstractCommand<PurgeDataResponse, PurgeDataOptions> {

	private static final String apiMethodName = "PurgeData";
	private static final Logger log = LoggerFactory.getLogger(PurgeDataCommand.class);

}
