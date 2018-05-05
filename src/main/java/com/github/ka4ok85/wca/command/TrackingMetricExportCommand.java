package com.github.ka4ok85.wca.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.github.ka4ok85.wca.options.TrackingMetricExportOptions;
import com.github.ka4ok85.wca.response.TrackingMetricExportResponse;

@Service
@Scope("prototype")
public class TrackingMetricExportCommand
		extends AbstractCommand<TrackingMetricExportResponse, TrackingMetricExportOptions> {

	private static final String apiMethodName = "TrackingMetricExport";
	private static final Logger log = LoggerFactory.getLogger(TrackingMetricExportCommand.class);
	
}