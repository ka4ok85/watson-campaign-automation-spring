package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.options.AddContactToContactListOptions;
import com.github.ka4ok85.wca.response.AddContactToContactListResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class AddContactToContactListCommand
		extends AbstractCommand<AddContactToContactListResponse, AddContactToContactListOptions> {

	private static final String apiMethodName = "AddContactToContactList";
	private static final Logger log = LoggerFactory.getLogger(AddContactToContactListCommand.class);

	@Autowired
	private AddContactToContactListResponse addContactToContactListResponse;

	@Override
	public ResponseContainer<AddContactToContactListResponse> executeCommand(AddContactToContactListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "AddContactToContactListOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element contactListID = doc.createElement("CONTACT_LIST_ID");
		contactListID.setTextContent(options.getContactListId().toString());
		addChildNode(contactListID, currentNode);

		if (options.getContactId() != null) {
			Element contactID = doc.createElement("CONTACT_ID");
			contactID.setTextContent(options.getContactId().toString());
			addChildNode(contactID, currentNode);
		} else if (options.getColumns().size() > 0) {
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
			throw new RuntimeException("Please provide ContactId or Columns");
		}

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		runApi(xml);

		ResponseContainer<AddContactToContactListResponse> response = new ResponseContainer<AddContactToContactListResponse>(
				addContactToContactListResponse);

		return response;
	}
}
