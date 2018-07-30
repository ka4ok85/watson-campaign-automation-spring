package com.github.ka4ok85.wca.command;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.WebTrackingDataExportOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.WebTrackingDataExportResponse;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA WebTrackingDataExport API.</strong> It
 * builds XML request for WebTrackingDataExport API using
 * {@link com.github.ka4ok85.wca.options.WebTrackingDataExportOptions} and reads
 * response into
 * {@link com.github.ka4ok85.wca.response.WebTrackingDataExportResponse}.
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
public class WebTrackingDataExportCommand
		extends AbstractJobCommand<WebTrackingDataExportResponse, WebTrackingDataExportOptions> {

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

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
		if (options.getEventStartDate() != null && options.getEventEndDate() != null) {
			if (options.getEventStartDate().isAfter(options.getEventEndDate())) {
				throw new RuntimeException("Start Date must be before End Date. Start Date: "
						+ options.getEventStartDate().format(formatter) + ", End Date: "
						+ options.getEventEndDate().format(formatter));
			}
		}

		if (options.getEventStartDate() != null) {
			Element eventStartDate = doc.createElement("EVENT_DATE_START");
			eventStartDate.setTextContent(options.getEventStartDate().format(formatter));
			addChildNode(eventStartDate, currentNode);
		}

		if (options.getEventEndDate() != null) {
			Element eventEndDate = doc.createElement("EVENT_DATE_END");
			eventEndDate.setTextContent(options.getEventEndDate().format(formatter));
			addChildNode(eventEndDate, currentNode);
		}

		if (options.getDomains() != null && options.getDomains().size() > 0) {
			Element domains = doc.createElement("DOMAINS");
			addChildNode(domains, currentNode);
			Element domainElement;
			for (Long domainId : options.getDomains()) {
				domainElement = doc.createElement("DOMAIN_ID");
				domainElement.setTextContent(domainId.toString());
				addChildNode(domainElement, domains);
			}
		}

		if (options.getSites() != null && options.getSites().size() > 0) {
			Element sites = doc.createElement("SITES");
			addChildNode(sites, currentNode);
			Element siteElement;
			for (Long siteId : options.getSites()) {
				siteElement = doc.createElement("SITE_ID");
				siteElement.setTextContent(siteId.toString());
				addChildNode(siteElement, sites);
			}
		}

		if (options.getDatabaseId() != null) {
			Element databaseId = doc.createElement("DATABASE_ID");
			databaseId.setTextContent(options.getDatabaseId().toString());
			addChildNode(databaseId, currentNode);
		}

		Element exportFormat = doc.createElement("EXPORT_FORMAT");
		exportFormat.setTextContent(options.getExportFormat().value().toString());
		addChildNode(exportFormat, currentNode);

		if (options.getExportFileName() != null) {
			Element exportFileName = doc.createElement("EXPORT_FILE_NAME");
			exportFileName.setTextContent(options.getExportFileName());
			addChildNode(exportFileName, currentNode);
		}

		Element fileEncoding = doc.createElement("FILE_ENCODING");
		fileEncoding.setTextContent(options.getFileEncoding().value());
		addChildNode(fileEncoding, currentNode);

		if (options.isMoveToFTP()) {
			Element moveToFtp = doc.createElement("MOVE_TO_FTP");
			addChildNode(moveToFtp, currentNode);
		}

		if (options.isAllEventTypes()) {
			Element allEventTypes = doc.createElement("ALL_EVENT_TYPES");
			addChildNode(allEventTypes, currentNode);
		} else {
			if (options.isEventSiteVisit()) {
				Element eventSiteVisit = doc.createElement("INCLUDE_SITE_VISIT_EVENTS");
				addChildNode(eventSiteVisit, currentNode);
			}
			if (options.isEventPageView()) {
				Element eventPageView = doc.createElement("INCLUDE_PAGE_VIEW_EVENTS");
				addChildNode(eventPageView, currentNode);
			}
			if (options.isEventClick()) {
				Element eventClick = doc.createElement("INCLUDE_CLICK_EVENTS");
				addChildNode(eventClick, currentNode);
			}
			if (options.isEventFormSubmit()) {
				Element eventFormSubmit = doc.createElement("INCLUDE_FORM_SUBMIT_EVENTS");
				addChildNode(eventFormSubmit, currentNode);
			}
			if (options.isEventDownload()) {
				Element eventDownload = doc.createElement("INCLUDE_DOWNLOAD_EVENTS");
				addChildNode(eventDownload, currentNode);
			}
			if (options.isEventMedia()) {
				Element eventMedia = doc.createElement("INCLUDE_MEDIA_EVENTS");
				addChildNode(eventMedia, currentNode);
			}
			if (options.isEventShareToSocial()) {
				Element eventShareToSocial = doc.createElement("INCLUDE_SHARE_TO_SOCIAL_EVENTS");
				addChildNode(eventShareToSocial, currentNode);
			}
			if (options.isEventCustom()) {
				Element eventCustom = doc.createElement("INCLUDE_CUSTOM_EVENTS");
				addChildNode(eventCustom, currentNode);
			}
		}

		if (options.getColumns() != null && options.getColumns().size() > 0) {
			Element exportColumns = doc.createElement("COLUMNS");
			addChildNode(exportColumns, currentNode);
			Element columnElement;
			Element nameElement;
			for (String column : options.getColumns()) {
				columnElement = doc.createElement("COLUMN");
				nameElement = doc.createElement("NAME");
				nameElement.setTextContent(column);
				addChildNode(nameElement, columnElement);
				addChildNode(columnElement, exportColumns);
			}
		}
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
		String filePath = jobPollingContainer.getParameters().get("FILE_PATH");
		String description = jobResponse.getJobDescription();
		Long jobId = jobResponse.getJobId();



		webTrackingDataExportResponse.setJobId(jobId);
		webTrackingDataExportResponse.setDescription(description);
		webTrackingDataExportResponse.setRemoteFileName(filePath);

		ResponseContainer<WebTrackingDataExportResponse> response = new ResponseContainer<WebTrackingDataExportResponse>(
				webTrackingDataExportResponse);

		return response;

	}

}
