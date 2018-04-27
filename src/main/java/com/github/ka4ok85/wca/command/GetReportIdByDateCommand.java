package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ka4ok85.wca.options.GetReportIdByDateOptions;
import com.github.ka4ok85.wca.response.GetReportIdByDateResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class GetReportIdByDateCommand extends AbstractCommand<GetReportIdByDateResponse, GetReportIdByDateOptions> {

	private static final String apiMethodName = "GetReportIdByDate";
	private static final Logger log = LoggerFactory.getLogger(GetReportIdByDateCommand.class);

}