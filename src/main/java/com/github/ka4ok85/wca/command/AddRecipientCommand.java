package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
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
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.options.AddRecipientOptions;
import com.github.ka4ok85.wca.response.AddRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class AddRecipientCommand extends AbstractCommand<AddRecipientResponse, AddRecipientOptions> {

	private static final String apiMethodName = "AddRecipient";
	private static final Logger log = LoggerFactory.getLogger(AddRecipientCommand.class);
	
	@Autowired
	private AddRecipientResponse addRecipientResponse;

	@Override
	public ResponseContainer<AddRecipientResponse> executeCommand(final AddRecipientOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "AddRecipientOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element createdFrom = doc.createElement("CREATED_FROM");
		createdFrom.setTextContent(options.getCreatedFrom().value().toString());
		addChildNode(createdFrom, currentNode);

		addBooleanParameter(methodElement, "UPDATE_IF_FOUND", options.isUpdateIfFound());
		addBooleanParameter(methodElement, "ALLOW_HTML", options.isAllowHtml());
		addBooleanParameter(methodElement, "SEND_AUTOREPLY", options.isSendAutoReply());

		if (options.getVisitorKey() != null) {
			Element visitorKey = doc.createElement("VISITOR_KEY");
			visitorKey.setTextContent(options.getVisitorKey());
			addChildNode(visitorKey, currentNode);
		}

		if (!options.getSyncFields().isEmpty()) {
			Element syncFields = doc.createElement("SYNC_FIELDS");
			addChildNode(syncFields, currentNode);
			for (Entry<String, String> entry : options.getSyncFields().entrySet()) {
				Element syncField = doc.createElement("SYNC_FIELD");
				addChildNode(syncField, syncFields);

				Element syncFieldName = doc.createElement("NAME");
				CDATASection cdata = doc.createCDATASection(entry.getKey());
				syncFieldName.appendChild(cdata);
				addChildNode(syncFieldName, syncField);

				Element syncFieldValue = doc.createElement("VALUE");
				cdata = doc.createCDATASection(entry.getValue());
				syncFieldValue.appendChild(cdata);
				addChildNode(syncFieldValue, syncField);
			}
		}

		if (!options.getColumns().isEmpty()) {
			for (Entry<String, String> entry : options.getColumns().entrySet()) {
				Element column = doc.createElement("COLUMN");
				addChildNode(column, currentNode);

				Element columnName = doc.createElement("NAME");
				CDATASection cdata = doc.createCDATASection(entry.getKey());
				columnName.appendChild(cdata);
				addChildNode(columnName, column);

				Element columnValue = doc.createElement("VALUE");
				cdata = doc.createCDATASection(entry.getValue());
				columnValue.appendChild(cdata);
				addChildNode(columnValue, column);
			}
		}

		if (options.getContactLists().size() > 0) {
			Element contactLists = doc.createElement("CONTACT_LISTS");
			addChildNode(contactLists, currentNode);
			for (Long contactListId : options.getContactLists()) {
				Element contactListIdElement = doc.createElement("CONTACT_LIST_ID");
				contactListIdElement.setTextContent(contactListId.toString());
				addChildNode(contactListIdElement, contactLists);
			}
		}

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		boolean visitorAssociation = false;
		Long recipientId;
		try {
			recipientId = Long.parseLong(((Node) xpath.evaluate("RecipientId", resultNode, XPathConstants.NODE)).getTextContent());
			Node visitorAssociationNode = (Node) xpath.evaluate("VISITOR_ASSOCIATION", resultNode, XPathConstants.NODE);
			if (visitorAssociationNode != null && visitorAssociationNode.getTextContent().equals("TRUE")) {
				visitorAssociation = true;
			}
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		addRecipientResponse.setRecipientId(recipientId);
		addRecipientResponse.setVisitorAssociation(visitorAssociation);

		ResponseContainer<AddRecipientResponse> response = new ResponseContainer<AddRecipientResponse>(
				addRecipientResponse);

		return response;
	}
}

