package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.GetSentMailingsForListOptions;
import com.github.ka4ok85.wca.response.GetSentMailingsForListResponse;

@Service
@Scope("prototype")
public class GetSentMailingsForListCommand
		extends AbstractCommand<GetSentMailingsForListResponse, GetSentMailingsForListOptions> {

	private static final String apiMethodName = "GetSentMailingsForList";
	private static final Logger log = LoggerFactory.getLogger(GetSentMailingsForListCommand.class);

	@Autowired
	private GetSentMailingsForListResponse getSentMailingsForListResponse;
}