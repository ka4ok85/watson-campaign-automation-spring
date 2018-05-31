package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.options.RemoveRecipientOptions;
import com.github.ka4ok85.wca.response.RemoveRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class RemoveRecipientCommand extends AbstractInstantCommand<RemoveRecipientResponse, RemoveRecipientOptions> {

	private static final String apiMethodName = "RemoveRecipient";

	@Autowired
	private RemoveRecipientResponse removeRecipientResponse;

	@Override
	public void buildXmlRequest(RemoveRecipientOptions options) {
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
	}

	@Override
	public ResponseContainer<RemoveRecipientResponse> readResponse(Node resultNode, RemoveRecipientOptions options) {
		ResponseContainer<RemoveRecipientResponse> response = new ResponseContainer<RemoveRecipientResponse>(
				removeRecipientResponse);

		return response;
	}
}
