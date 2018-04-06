package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

public class RemoveRecipientCommand extends AbstractCommand<RemoveRecipientResponse, RemoveRecipientOptions> {

	private static final String apiMethodName = "RemoveRecipient";
	private static final Logger log = LoggerFactory.getLogger(RemoveRecipientCommand.class);

	@Override
	public ResponseContainer<RemoveRecipientResponse> executeCommand(final RemoveRecipientOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "RemoveRecipientResponse must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		if (options.getEmail() != null) {
			Element email = doc.createElement("EMAIL");
			email.setTextContent(options.getEmail());
			addChildNode(email, currentNode);
		} else {
			throw new RuntimeException("You must provide Email");
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

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		runApi(xml);

		ResponseContainer<RemoveRecipientResponse> response = new ResponseContainer<RemoveRecipientResponse>(
				new RemoveRecipientResponse());

		return response;
	}
}
