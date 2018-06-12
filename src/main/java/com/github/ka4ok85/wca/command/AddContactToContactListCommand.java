package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.options.AddContactToContactListOptions;
import com.github.ka4ok85.wca.response.AddContactToContactListResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA AddContactToContactList API.</strong> It builds XML
 * request for AddContactToContactList API using
 * {@link com.github.ka4ok85.wca.options.AddContactToContactListOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.AddContactToContactListResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class AddContactToContactListCommand
		extends AbstractInstantCommand<AddContactToContactListResponse, AddContactToContactListOptions> {

	private static final String apiMethodName = "AddContactToContactList";

	@Autowired
	private AddContactToContactListResponse addContactToContactListResponse;

	/**
	 * Builds XML request for AddContactToContactList API using
	 * {@link com.github.ka4ok85.wca.options.AddContactToContactListOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(AddContactToContactListOptions options) {
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
	}

	/**
	 * Reads AddContactToContactList API response into
	 * {@link com.github.ka4ok85.wca.response.AddContactToContactListResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO AddContactToContactList Response
	 */
	@Override
	public ResponseContainer<AddContactToContactListResponse> readResponse(Node resultNode,
			AddContactToContactListOptions options) {
		ResponseContainer<AddContactToContactListResponse> response = new ResponseContainer<AddContactToContactListResponse>(
				addContactToContactListResponse);

		return response;
	}
}
