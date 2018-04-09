package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.CreateTableOptions;
import com.github.ka4ok85.wca.response.CreateTableResponse;

public class CreateTableCommand extends AbstractCommand<CreateTableResponse, CreateTableOptions> {

	private static final String apiMethodName = "CreateTable";
	private static final Logger log = LoggerFactory.getLogger(CreateTableCommand.class);

}
