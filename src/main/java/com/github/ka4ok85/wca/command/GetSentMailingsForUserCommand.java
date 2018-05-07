package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.GetSentMailingsForUserOptions;
import com.github.ka4ok85.wca.response.GetSentMailingsForUserResponse;

@Service
@Scope("prototype")
public class GetSentMailingsForUserCommand
		extends AbstractCommand<GetSentMailingsForUserResponse, GetSentMailingsForUserOptions> {

	private static final String apiMethodName = "GetSentMailingsForUser";
	private static final Logger log = LoggerFactory.getLogger(GetSentMailingsForUserCommand.class);

	@Autowired
	private GetSentMailingsForUserResponse getSentMailingsForUserResponse;
	
}