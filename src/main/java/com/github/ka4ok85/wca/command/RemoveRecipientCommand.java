package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;

public class RemoveRecipientCommand extends AbstractCommand<RemoveRecipientResponse, RemoveRecipientOptions> {

	private static final String apiMethodName = "RemoveRecipient";
	private static final Logger log = LoggerFactory.getLogger(RemoveRecipientCommand.class);

}
