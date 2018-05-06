package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.GetSentMailingsForOrgOptions;
import com.github.ka4ok85.wca.response.GetSentMailingsForOrgResponse;



@Service
@Scope("prototype")
public class GetSentMailingsForOrgCommand extends AbstractCommand<GetSentMailingsForOrgResponse, GetSentMailingsForOrgOptions> {

	private static final String apiMethodName = "GetSentMailingsForOrg";
	private static final Logger log = LoggerFactory.getLogger(GetSentMailingsForOrgCommand.class);

	@Autowired
	private GetSentMailingsForOrgResponse getSentMailingsForOrgResponse;
	
}
