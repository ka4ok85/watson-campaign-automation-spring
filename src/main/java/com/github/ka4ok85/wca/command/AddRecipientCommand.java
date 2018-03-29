package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.AddRecipientOptions;
import com.github.ka4ok85.wca.response.AddRecipientResponse;

public class AddRecipientCommand extends AbstractCommand<AddRecipientResponse, AddRecipientOptions> {

	private static final String apiMethodName = "AddRecipient";
	private static final Logger log = LoggerFactory.getLogger(AddRecipientCommand.class);

}
