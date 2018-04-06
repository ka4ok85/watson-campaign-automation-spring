package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.response.GetListsResponse;

public class GetListsCommand extends AbstractCommand<GetListsResponse, GetListsOptions> {

	private static final String apiMethodName = "GetLists";
	private static final Logger log = LoggerFactory.getLogger(GetListsCommand.class);

}
