package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.AddContactToContactListOptions;
import com.github.ka4ok85.wca.response.AddContactToContactListResponse;

@Service
@Scope("prototype")
public class AddContactToContactListCommand extends AbstractCommand<AddContactToContactListResponse, AddContactToContactListOptions> {

	private static final String apiMethodName = "AddContactToContactList";
	private static final Logger log = LoggerFactory.getLogger(AddContactToContactListCommand.class);

	@Autowired
	private AddContactToContactListResponse addContactToContactListResponse;
}
