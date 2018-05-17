package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.GetAggregateTrackingForOrgOptions;
import com.github.ka4ok85.wca.response.GetAggregateTrackingForOrgResponse;

@Service
@Scope("prototype")
public class GetAggregateTrackingForOrgCommand
		extends AbstractCommand<GetAggregateTrackingForOrgResponse, GetAggregateTrackingForOrgOptions> {

	private static final String apiMethodName = "GetAggregateTrackingForOrg";
	private static final Logger log = LoggerFactory.getLogger(GetAggregateTrackingForOrgCommand.class);

	@Autowired
	private GetAggregateTrackingForOrgResponse getAggregateTrackingForOrgResponse;
}
