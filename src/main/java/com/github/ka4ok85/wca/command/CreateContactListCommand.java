package com.github.ka4ok85.wca.command;

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

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.CreateContactListOptions;
import com.github.ka4ok85.wca.response.CreateContactListResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA CreateContactList API.</strong> It
 * builds XML request for CreateContactList API using
 * {@link com.github.ka4ok85.wca.options.CreateContactListOptions} and reads
 * response into
 * {@link com.github.ka4ok85.wca.response.CreateContactListResponse}.
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
public class CreateContactListCommand
		extends AbstractInstantCommand<CreateContactListResponse, CreateContactListOptions> {

	private static final String apiMethodName = "CreateContactList";
	private static final Logger log = LoggerFactory.getLogger(CreateContactListCommand.class);

	@Autowired
	private CreateContactListResponse createContactListResponse;

	/**
	 * Builds XML request for CreateContactList API using
	 * {@link com.github.ka4ok85.wca.options.CreateContactListOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(CreateContactListOptions options) {
		Objects.requireNonNull(options, "CreateContactListOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element databaseID = doc.createElement("DATABASE_ID");
		databaseID.setTextContent(options.getDatabaseId().toString());
		addChildNode(databaseID, currentNode);

		Element contactListName = doc.createElement("CONTACT_LIST_NAME");
		CDATASection cdata = doc.createCDATASection(options.getContactListName());
		contactListName.appendChild(cdata);
		addChildNode(contactListName, currentNode);

		Element visibility = doc.createElement("VISIBILITY");
		visibility.setTextContent(options.getVisibility().value().toString());
		addChildNode(visibility, currentNode);

		if (options.getParentFolderId() != null) {
			Element parentFolderId = doc.createElement("PARENT_FOLDER_ID");
			parentFolderId.setTextContent(options.getParentFolderId().toString());
			addChildNode(parentFolderId, currentNode);
		} else if (options.getParentFolderPath() != null) {
			Element parentFolderPath = doc.createElement("PARENT_FOLDER_PATH");
			parentFolderPath.setTextContent(options.getParentFolderPath());
			addChildNode(parentFolderPath, currentNode);
		}
	}

	/**
	 * Reads CreateContactList API response into
	 * {@link com.github.ka4ok85.wca.response.CreateContactListResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO CreateContactList Response
	 */
	@Override
	public ResponseContainer<CreateContactListResponse> readResponse(Node resultNode,
			CreateContactListOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Long contactListId;
		try {
			Node contactListIdNode = (Node) xpath.evaluate("CONTACT_LIST_ID", resultNode, XPathConstants.NODE);
			contactListId = Long.parseLong(contactListIdNode.getTextContent());
			log.debug("Created Contact List ID is {}", contactListId);
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		createContactListResponse.setContactListId(contactListId);
		ResponseContainer<CreateContactListResponse> response = new ResponseContainer<CreateContactListResponse>(
				createContactListResponse);

		return response;
	}
}
