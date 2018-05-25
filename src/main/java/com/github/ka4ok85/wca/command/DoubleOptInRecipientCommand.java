package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.DoubleOptInRecipientOptions;
import com.github.ka4ok85.wca.response.DoubleOptInRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/*
 * Flexible Databases are not supported 
 */
@Service
@Scope("prototype")
public class DoubleOptInRecipientCommand
		extends AbstractCommand<DoubleOptInRecipientResponse, DoubleOptInRecipientOptions> {

	private static final String apiMethodName = "DoubleOptInRecipient";

	@Autowired
	private DoubleOptInRecipientResponse doubleOptInRecipientResponse;

	@Override
	public void buildXmlRequest(DoubleOptInRecipientOptions options) {
		Objects.requireNonNull(options, "DoubleOptInRecipientOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		addBooleanParameter(methodElement, "SEND_AUTOREPLY", options.isSendAutoReply());
		addBooleanParameter(methodElement, "ALLOW_HTML", options.isAllowHtml());

		if (!options.getColumns().isEmpty()) {
			if (!options.getColumns().containsKey("EMAIL")) {
				throw new RuntimeException("You must provide EMAIL Column");
			}

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
		} else {
			throw new RuntimeException("You must provide Columns");
		}
	}

	@Override
	public ResponseContainer<DoubleOptInRecipientResponse> readResponse(Node resultNode,
			DoubleOptInRecipientOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		Long recipientId;
		try {
			recipientId = Long.parseLong(
					((Node) xpath.evaluate("RecipientId", resultNode, XPathConstants.NODE)).getTextContent());
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		doubleOptInRecipientResponse.setRecipientId(recipientId);

		ResponseContainer<DoubleOptInRecipientResponse> response = new ResponseContainer<DoubleOptInRecipientResponse>(
				doubleOptInRecipientResponse);

		return response;
	}
}
