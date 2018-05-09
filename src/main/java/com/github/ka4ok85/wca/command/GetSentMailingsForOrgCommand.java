package com.github.ka4ok85.wca.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.constants.FileEncoding;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.options.GetSentMailingsForOrgOptions;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.GetSentMailingsForOrgResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.ReportIdByDateMailing;
import com.github.ka4ok85.wca.response.containers.SentMailing;
import com.github.ka4ok85.wca.utils.DateTimeRange;



@Service
@Scope("prototype")
public class GetSentMailingsForOrgCommand extends AbstractCommand<GetSentMailingsForOrgResponse, GetSentMailingsForOrgOptions> {

	private static final String apiMethodName = "GetSentMailingsForOrg";
	private static final Logger log = LoggerFactory.getLogger(GetSentMailingsForOrgCommand.class);

	@Autowired
	private GetSentMailingsForOrgResponse getSentMailingsForOrgResponse;
	
	@Override
	public ResponseContainer<GetSentMailingsForOrgResponse> executeCommand(GetSentMailingsForOrgOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "GetSentMailingsForOrgOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);
		
		DateTimeRange dateTimeRange = options.getDateTimeRange();
		addParameter(currentNode, "DATE_START", dateTimeRange.getFormattedStartDateTime());
		addParameter(currentNode, "DATE_END", dateTimeRange.getFormattedEndDateTime());
		
		if (options.getVisibility() == Visibility.SHARED) {
			addBooleanParameter(currentNode, "SHARED", true);
		} else if (options.getVisibility() == Visibility.PRIVATE) {
			addBooleanParameter(currentNode, "PRIVATE", true);
		}
		
		if (options.isMailingCountOnly()) {
			addBooleanParameter(currentNode, "MAILING_COUNT_ONLY", true);
		}
		
		/*
		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element exportType = doc.createElement("EXPORT_TYPE");
		exportType.setTextContent(options.getExportType().value());
		addChildNode(exportType, currentNode);

		Element exportFormat = doc.createElement("EXPORT_FORMAT");
		exportFormat.setTextContent(options.getExportFormat().value());
		addChildNode(exportFormat, currentNode);

		Element fileEncoding = doc.createElement("FILE_ENCODING");
		fileEncoding.setTextContent(options.getFileEncoding().value());
		addChildNode(fileEncoding, currentNode);

		DateTimeRange lastModifiedRange = options.getLastModifiedRange();
		if (lastModifiedRange != null) {
			addParameter(currentNode, "DATE_START", lastModifiedRange.getFormattedStartDateTime());
			addParameter(currentNode, "DATE_END", lastModifiedRange.getFormattedEndDateTime());
		}

		addBooleanParameter(methodElement, "ADD_TO_STORED_FILES", options.isAddToStoredFiles());
		addBooleanParameter(methodElement, "INCLUDE_LEAD_SOURCE", options.isIncludeLeadSource());
		addBooleanParameter(methodElement, "INCLUDE_LIST_ID_IN_FILE", options.isIncludeListId());
		addBooleanParameter(methodElement, "INCLUDE_RECIPIENT_ID", options.isIncludeRecipientId());

		if (options.getExportColumns() != null && options.getExportColumns().size() > 0) {
			Element exportColumns = doc.createElement("EXPORT_COLUMNS");
			addChildNode(exportColumns, currentNode);
			Element columnElement;
			for (String column : options.getExportColumns()) {
				columnElement = doc.createElement("COLUMN");
				columnElement.setTextContent(column);
				addChildNode(columnElement, exportColumns);
			}
		}
*/
		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		List<SentMailing> sentMailings = new ArrayList<SentMailing>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
		try {

			if (options.isMailingCountOnly()) {
				getSentMailingsForOrgResponse.setSentMailingsCount(Long.parseLong(
							((Node) xpath.evaluate("SentMailingsCount", resultNode, XPathConstants.NODE)).getTextContent()));
			} else {
				NodeList mailingsNode = (NodeList) xpath.evaluate("Mailing", resultNode, XPathConstants.NODESET);
				Node mailingNode;
	
				for (int i = 0; i < mailingsNode.getLength(); i++) {
					SentMailing mailing = new SentMailing();
					mailingNode = mailingsNode.item(i);
	
					mailing.setListId(Long.parseLong(
							((Node) xpath.evaluate("ListId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setListName(((Node) xpath.evaluate("ListName", mailingNode, XPathConstants.NODE)).getTextContent());
					mailing.setMailingId(Long.parseLong(
							((Node) xpath.evaluate("MailingId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setMailingName(((Node) xpath.evaluate("MailingName", mailingNode, XPathConstants.NODE)).getTextContent());
					mailing.setNumSent(Long.parseLong(
							((Node) xpath.evaluate("NumSent", mailingNode, XPathConstants.NODE)).getTextContent()));
					Node parentListIdNode = (Node) xpath.evaluate("ParentListId", mailingNode, XPathConstants.NODE);
					if (parentListIdNode != null) {
						mailing.setParentListId(Long.parseLong(parentListIdNode.getTextContent()));
					}
					
					mailing.setParentTemplateId(Long.parseLong(
							((Node) xpath.evaluate("ParentTemplateId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setReportId(Long.parseLong(
							((Node) xpath.evaluate("ReportId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setScheduledDateTime(LocalDateTime.parse(
							((Node) xpath.evaluate("ScheduledTS", mailingNode, XPathConstants.NODE)).getTextContent(),
							formatter));
					mailing.setSentDateTime(LocalDateTime.parse(
							((Node) xpath.evaluate("SentTS", mailingNode, XPathConstants.NODE)).getTextContent(),
							formatter));
	//mailing.setSentMailingsCount(Long.parseLong(
							//((Node) xpath.evaluate("ListId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailing.setSubject(((Node) xpath.evaluate("Subject", mailingNode, XPathConstants.NODE)).getTextContent());
					//mailing.setTags(tags);
					mailing.setUserName(((Node) xpath.evaluate("UserName", mailingNode, XPathConstants.NODE)).getTextContent());
					mailing.setVisibility(Visibility.getVisibilityByAlias(((Node) xpath.evaluate("Visibility", mailingNode, XPathConstants.NODE)).getTextContent()));
					/*
					mailingResponse.setReportId(Long.parseLong(
							((Node) xpath.evaluate("ReportId", mailingNode, XPathConstants.NODE)).getTextContent()));
					mailingResponse.setSentDateTime(LocalDateTime.parse(
							((Node) xpath.evaluate("SentTS", mailingNode, XPathConstants.NODE)).getTextContent(),
							responseFormatter));
	*/
					sentMailings.add(mailing);
				}
				
				getSentMailingsForOrgResponse.setSentMailings(sentMailings);
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}
		
		

		/*
		exportListResponse.setRemoteFileName(filePath);
		exportListResponse.setDescription(description);
		exportListResponse.setFileEncoding(fileEncodingValue);
		exportListResponse.setKeepInStoredFiles(keepInStoredFiles);
		exportListResponse.setListName(listName);
		exportListResponse.setKeepInFtpDownloadDirectory(keepInFtpDownloadDirectory);
*/
		ResponseContainer<GetSentMailingsForOrgResponse> response = new ResponseContainer<GetSentMailingsForOrgResponse>(getSentMailingsForOrgResponse);

		return response;
	}
}