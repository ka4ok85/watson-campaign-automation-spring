package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.DeleteTableOptions;
import com.github.ka4ok85.wca.response.DeleteTableResponse;

public class DeleteTableCommand extends AbstractCommand<DeleteTableResponse, DeleteTableOptions> {

	private static final String apiMethodName = "DeleteTable";
	private static final Logger log = LoggerFactory.getLogger(DeleteTableCommand.class);

}
