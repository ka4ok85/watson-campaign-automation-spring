package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.GetAggregateTrackingForMailingOptions;
import com.github.ka4ok85.wca.response.GetAggregateTrackingForMailingResponse;

@Service
@Scope("prototype")
public class GetAggregateTrackingForMailingCommand
		extends AbstractCommand<GetAggregateTrackingForMailingResponse, GetAggregateTrackingForMailingOptions> {

	private static final String apiMethodName = "GetAggregateTrackingForMailing";
	private static final Logger log = LoggerFactory.getLogger(GetAggregateTrackingForMailingCommand.class);

	@Autowired
	private GetAggregateTrackingForMailingResponse getAggregateTrackingForMailingResponse;
}