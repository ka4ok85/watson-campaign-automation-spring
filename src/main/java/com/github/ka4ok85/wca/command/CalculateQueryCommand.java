package com.github.ka4ok85.wca.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.command.AbstractCommand;
import com.github.ka4ok85.wca.command.CalculateQueryOptions;
import com.github.ka4ok85.wca.response.CalculateQueryResponse;

public class CalculateQueryCommand extends AbstractCommand<CalculateQueryResponse, CalculateQueryOptions> {

	private static final String apiMethodName = "CalculateQuery";
	private static final Logger log = LoggerFactory.getLogger(CalculateQueryCommand.class);

}
