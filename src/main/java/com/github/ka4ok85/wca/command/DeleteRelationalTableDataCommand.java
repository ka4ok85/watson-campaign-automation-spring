package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.DeleteRelationalTableDataOptions;
import com.github.ka4ok85.wca.response.DeleteRelationalTableDataResponse;

public class DeleteRelationalTableDataCommand
		extends AbstractCommand<DeleteRelationalTableDataResponse, DeleteRelationalTableDataOptions> {

	private static final String apiMethodName = "DeleteRelationalTableData";
	private static final Logger log = LoggerFactory.getLogger(DeleteRelationalTableDataCommand.class);

}
