package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.GetMailingTemplatesOptions;
import com.github.ka4ok85.wca.response.GetMailingTemplatesResponse;

public class GetMailingTemplatesCommand extends AbstractCommand<GetMailingTemplatesResponse, GetMailingTemplatesOptions> {

	private static final String apiMethodName = "GetMailingTemplates";
	private static final Logger log = LoggerFactory.getLogger(GetMailingTemplatesCommand.class);

}
