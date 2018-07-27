package com.github.ka4ok85.wca.command;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.options.WebTrackingDataExportOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.WebTrackingDataExportResponse;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

/**
 * <strong>Class for interacting with WCA WebTrackingDataExport API.</strong> It builds XML
 * request for WebTrackingDataExport API using
 * {@link com.github.ka4ok85.wca.options.WebTrackingDataExportOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.WebTrackingDataExportResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * It relies on parent {@link com.github.ka4ok85.wca.command.AbstractJobCommand}
 * for polling mechanism.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class WebTrackingDataExportCommand extends AbstractJobCommand<WebTrackingDataExportResponse, WebTrackingDataExportOptions> {

	private static final String apiMethodName = "WebTrackingDataExport";
	private static final Logger log = LoggerFactory.getLogger(WebTrackingDataExportCommand.class);

	@Autowired
	private WebTrackingDataExportResponse webTrackingDataExportResponse;

	/**
	 * Builds XML request for WebTrackingDataExport API using
	 * {@link com.github.ka4ok85.wca.options.WebTrackingDataExportOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(WebTrackingDataExportOptions options) {
		Objects.requireNonNull(options, "WebTrackingDataExportOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);


	}

	/**
	 * Reads WebTrackingDataExport API response into
	 * {@link com.github.ka4ok85.wca.response.WebTrackingDataExportResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for WebTrackingDataExportResponse API
	 * @param options
	 *            - settings for API call
	 * @return POJO WebTrackingDataExport Response
	 */
	@Override
	public ResponseContainer<WebTrackingDataExportResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, WebTrackingDataExportOptions options) {

		return null;
	}


}
