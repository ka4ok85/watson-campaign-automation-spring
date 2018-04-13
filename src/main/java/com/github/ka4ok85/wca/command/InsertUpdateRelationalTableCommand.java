package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse;

public class InsertUpdateRelationalTableCommand
		extends AbstractCommand<InsertUpdateRelationalTableResponse, InsertUpdateRelationalTableOptions> {

	private static final String apiMethodName = "InsertUpdateRelationalTable";
	private static final Logger log = LoggerFactory.getLogger(InsertUpdateRelationalTableCommand.class);

}
