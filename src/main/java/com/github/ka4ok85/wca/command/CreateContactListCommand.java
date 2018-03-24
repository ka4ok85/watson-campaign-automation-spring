package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.CreateContactListOptions;
import com.github.ka4ok85.wca.response.CreateContactListResponse;

@Service
public class CreateContactListCommand extends AbstractCommand<CreateContactListResponse, CreateContactListOptions> {

	private static final String apiMethodName = "CreateContactList";
	private static final Logger log = LoggerFactory.getLogger(CreateContactListCommand.class);

}
