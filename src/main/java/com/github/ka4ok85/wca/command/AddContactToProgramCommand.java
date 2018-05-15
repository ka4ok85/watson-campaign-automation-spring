package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.AddContactToProgramOptions;
import com.github.ka4ok85.wca.response.AddContactToProgramResponse;

@Service
@Scope("prototype")
public class AddContactToProgramCommand
		extends AbstractCommand<AddContactToProgramResponse, AddContactToProgramOptions> {

	private static final String apiMethodName = "AddContactToProgram";
	private static final Logger log = LoggerFactory.getLogger(AddContactToProgramCommand.class);

	@Autowired
	private AddContactToProgramResponse addContactToProgramResponse;
}