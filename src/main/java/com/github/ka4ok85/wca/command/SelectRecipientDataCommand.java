package com.github.ka4ok85.wca.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;

public class SelectRecipientDataCommand
		extends AbstractCommand<SelectRecipientDataResponse, SelectRecipientDataOptions> {

	private static final String apiMethodName = "SelectRecipientData";
	private static final Logger log = LoggerFactory.getLogger(SelectRecipientDataCommand.class);

	@Override
	public ResponseContainer<SelectRecipientDataResponse> executeCommand(final SelectRecipientDataOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "SelectRecipientDataOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);
		if (options.getEmail() != null) {
			Element email = doc.createElement("EMAIL");
			email.setTextContent(options.getEmail());
			addChildNode(email, currentNode);
		} else if (options.getRecipientId() != 0) {
			Element recipientId = doc.createElement("RECIPIENT_ID");
			recipientId.setTextContent(options.getRecipientId().toString());
			addChildNode(recipientId, currentNode);
		} else if (options.getEncodedRecipientId() != null) {
			Element encodedRecipientId = doc.createElement("ENCODED_RECIPIENT_ID");
			encodedRecipientId.setTextContent(options.getEncodedRecipientId());
			addChildNode(encodedRecipientId, currentNode);
		} else if (options.getVisitorKey() != null) {
			Element visitorKey = doc.createElement("VISITOR_KEY");
			visitorKey.setTextContent(options.getVisitorKey());
			addChildNode(visitorKey, currentNode);
		}

		addBooleanParameter(methodElement, "RETURN_CONTACT_LISTS", options.isReturnContactLists());

		for (Entry<String, String> entry : options.getKeyColumns().entrySet()) {
			Element column = doc.createElement("COLUMN");
			addChildNode(column, currentNode);
			Element columnName = doc.createElement("NAME");
			columnName.setTextContent(entry.getKey());
			addChildNode(columnName, column);
			Element columnValue = doc.createElement("VALUE");
			columnValue.setTextContent(entry.getValue());
			addChildNode(columnValue, column);
		}

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String email;
		Long recipientId;
		int emailType;
		LocalDateTime lastModified = null;
		int createdFrom;
		LocalDateTime optedIn = null;
		LocalDateTime optedOut = null;
		LocalDateTime resumeSendDate = null;
		String organiztionId;
		String crmLeadSource;
		Map<String, String> columns = new HashMap<String, String>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy K:mm a");
		try {
			email = ((Node) xpath.evaluate("Email", resultNode, XPathConstants.NODE)).getTextContent();
			recipientId = Long.parseLong(((Node) xpath.evaluate("RecipientId", resultNode, XPathConstants.NODE)).getTextContent());
			emailType = Integer.parseInt(((Node) xpath.evaluate("EmailType", resultNode, XPathConstants.NODE)).getTextContent());
			String lastModifiedText = ((Node) xpath.evaluate("LastModified", resultNode, XPathConstants.NODE)).getTextContent();
			if (lastModifiedText != "") {
				lastModified = LocalDateTime.parse(lastModifiedText, formatter);
			}

			createdFrom = Integer.parseInt(((Node) xpath.evaluate("CreatedFrom", resultNode, XPathConstants.NODE)).getTextContent());
			String optedInText = ((Node) xpath.evaluate("OptedIn", resultNode, XPathConstants.NODE)).getTextContent();
			if (optedInText != "") {
				optedIn = LocalDateTime.parse(optedInText, formatter);
			}

			String optedOutText = ((Node) xpath.evaluate("OptedOut", resultNode, XPathConstants.NODE)).getTextContent();
			if (optedOutText != "") {
				optedOut = LocalDateTime.parse(optedOutText, formatter);
			}

			String resumeSendDateText = ((Node) xpath.evaluate("ResumeSendDate", resultNode, XPathConstants.NODE)).getTextContent();
			if (resumeSendDateText != "") {
				resumeSendDate = LocalDateTime.parse(resumeSendDateText, formatter);
			}

			organiztionId = ((Node) xpath.evaluate("ORGANIZATION_ID", resultNode, XPathConstants.NODE)).getTextContent();
			crmLeadSource = ((Node) xpath.evaluate("CRMLeadSource", resultNode, XPathConstants.NODE)).getTextContent();

			NodeList columnsNode = (NodeList) xpath.evaluate("COLUMNS/COLUMN", resultNode, XPathConstants.NODESET);
			Node column;
			String name;
			String value;
			for (int i = 0; i < columnsNode.getLength(); i++) {
				column = columnsNode.item(i);
				name = crmLeadSource = ((Node) xpath.evaluate("NAME", column, XPathConstants.NODE)).getTextContent();
				value = crmLeadSource = ((Node) xpath.evaluate("VALUE", column, XPathConstants.NODE)).getTextContent();
				columns.put(name, value);
			}
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		SelectRecipientDataResponse selectRecipientDataResponse = new SelectRecipientDataResponse();
		selectRecipientDataResponse.setEmail(email);
		selectRecipientDataResponse.setRecipientId(recipientId);
		selectRecipientDataResponse.setEmailType(emailType);
		selectRecipientDataResponse.setLastModified(lastModified);
		selectRecipientDataResponse.setCreatedFrom(createdFrom);
		selectRecipientDataResponse.setOptedIn(optedIn);
		selectRecipientDataResponse.setOptedOut(optedOut);
		selectRecipientDataResponse.setResumeSendDate(resumeSendDate);
		selectRecipientDataResponse.setOrganiztionId(organiztionId);
		selectRecipientDataResponse.setCrmLeadSource(crmLeadSource);
		selectRecipientDataResponse.setColumns(columns);

		ResponseContainer<SelectRecipientDataResponse> response = new ResponseContainer<SelectRecipientDataResponse>(selectRecipientDataResponse);

		return response;
	}
}
