package com.github.ka4ok85.wca.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.command.AbstractCommand;
import com.github.ka4ok85.wca.command.GetAggregateTrackingForUserOptions;
import com.github.ka4ok85.wca.response.GetAggregateTrackingForUserResponse;

@Service
@Scope("prototype")
public class GetAggregateTrackingForUserCommand
		extends AbstractCommand<GetAggregateTrackingForUserResponse, GetAggregateTrackingForUserOptions> {

	private static final String apiMethodName = "GetAggregateTrackingForUser";
	private static final Logger log = LoggerFactory.getLogger(GetAggregateTrackingForUserCommand.class);

	@Autowired
	private GetAggregateTrackingForUserResponse getAggregateTrackingForUserResponse;
	
}