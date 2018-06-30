package com.github.ka4ok85.wca.command;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.options.RawRecipientDataExportOptions;
import com.github.ka4ok85.wca.response.RawRecipientDataExportResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;

/**
 * <strong>Class for interacting with WCA RawRecipientDataExport API.</strong>
 * It builds XML request for RawRecipientDataExport API using
 * {@link com.github.ka4ok85.wca.options.RawRecipientDataExportOptions} and
 * reads response into
 * {@link com.github.ka4ok85.wca.response.RawRecipientDataExportResponse}.
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
public class RawRecipientDataExportCommand
		extends AbstractJobCommand<RawRecipientDataExportResponse, RawRecipientDataExportOptions> {

	private static final String apiMethodName = "RawRecipientDataExport";
	private static final Logger log = LoggerFactory.getLogger(RawRecipientDataExportCommand.class);

	@Autowired
	private RawRecipientDataExportResponse rawRecipientDataExportResponse;

	/**
	 * Builds XML request for RawRecipientDataExport API using
	 * {@link com.github.ka4ok85.wca.options.RawRecipientDataExportOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(RawRecipientDataExportOptions options) {
		Objects.requireNonNull(options, "RawRecipientDataExportOptions must not be null");

		setJobIdPath("MAILING/JOB_ID");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		if (options.getMailingReportId() != null && options.getMailingReportId().size() > 0) {
			for (HashMap<String, Long> mailingReportId : options.getMailingReportId()) {
				Element mailingNode = doc.createElement("MAILING");
				addChildNode(mailingNode, currentNode);

				if (mailingReportId.containsKey("mailingId")) {
					Element mailingIdNode = doc.createElement("MAILING_ID");
					mailingIdNode.setTextContent(mailingReportId.get("mailingId").toString());
					addChildNode(mailingIdNode, mailingNode);
				}

				if (mailingReportId.containsKey("reportId")) {
					Element reportIdNode = doc.createElement("REPORT_ID");
					reportIdNode.setTextContent(mailingReportId.get("reportId").toString());
					addChildNode(reportIdNode, mailingNode);
				}
			}
		}

	}

	/**
	 * Reads RawRecipientDataExport API response into
	 * {@link com.github.ka4ok85.wca.response.RawRecipientDataExportResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for JobResponse API
	 * @param options
	 *            - settings for API call
	 * @return POJO RawRecipientDataExport Response
	 */
	@Override
	public ResponseContainer<RawRecipientDataExportResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, RawRecipientDataExportOptions options) {

		ResponseContainer<RawRecipientDataExportResponse> response = new ResponseContainer<RawRecipientDataExportResponse>(
				rawRecipientDataExportResponse);

		return response;
	}

}
