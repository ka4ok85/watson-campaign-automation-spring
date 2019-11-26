package com.github.ka4ok85.wca.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.SelectRecipientDataOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.SelectRecipientDataResponse;

/**
 * <strong>Class for interacting with WCA SelectRecipientData API.</strong> It
 * builds XML request for SelectRecipientData API using
 * {@link com.github.ka4ok85.wca.options.SelectRecipientDataOptions} and reads
 * response into
 * {@link com.github.ka4ok85.wca.response.SelectRecipientDataResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * <strong>ATTN: </strong>SelectRecipientData API does not work for Restricted
 * Databases with Compound non-String key columns
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class SelectRecipientDataCommand
		extends AbstractInstantCommand<SelectRecipientDataResponse, SelectRecipientDataOptions> {

	private static final String apiMethodName = "SelectRecipientData";

	@Autowired
	private SelectRecipientDataResponse selectRecipientDataResponse;

	/**
	 * Builds XML request for SelectRecipientData API using
	 * {@link com.github.ka4ok85.wca.options.SelectRecipientDataOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(SelectRecipientDataOptions options) {
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
		} else if (options.getRecipientId() != null) {
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
		} else if (!options.getKeyColumns().isEmpty()) {
			for (Entry<String, String> entry : options.getKeyColumns().entrySet()) {
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
			throw new RuntimeException(
					"Unique key columns must be part of the submission with column names and values");
		}

		addBooleanParameter(methodElement, "RETURN_CONTACT_LISTS", options.isReturnContactLists());

	}

	/**
	 * Reads SelectRecipientData API response into
	 * {@link com.github.ka4ok85.wca.response.SelectRecipientDataResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO SelectRecipientData Response
	 */
	@Override
	public ResponseContainer<SelectRecipientDataResponse> readResponse(Node resultNode,
			SelectRecipientDataOptions options) {
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
		List<Long> contactLists = new ArrayList<Long>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		try {
			email = ((Node) xpath.evaluate("Email", resultNode, XPathConstants.NODE)).getTextContent();
			recipientId = Long.parseLong(
					((Node) xpath.evaluate("RecipientId", resultNode, XPathConstants.NODE)).getTextContent());
			emailType = Integer
					.parseInt(((Node) xpath.evaluate("EmailType", resultNode, XPathConstants.NODE)).getTextContent());
			String lastModifiedText = ((Node) xpath.evaluate("LastModified", resultNode, XPathConstants.NODE))
					.getTextContent();
			if (lastModifiedText != "") {
				lastModified = LocalDateTime.parse(lastModifiedText, formatter);
			}

			createdFrom = Integer
					.parseInt(((Node) xpath.evaluate("CreatedFrom", resultNode, XPathConstants.NODE)).getTextContent());
			String optedInText = ((Node) xpath.evaluate("OptedIn", resultNode, XPathConstants.NODE)).getTextContent();
			if (optedInText != "") {
				optedIn = LocalDateTime.parse(optedInText, formatter);
			}

			String optedOutText = ((Node) xpath.evaluate("OptedOut", resultNode, XPathConstants.NODE)).getTextContent();
			if (optedOutText != "") {
				optedOut = LocalDateTime.parse(optedOutText, formatter);
			}

			String resumeSendDateText = ((Node) xpath.evaluate("ResumeSendDate", resultNode, XPathConstants.NODE))
					.getTextContent();
			if (resumeSendDateText != "") {
				resumeSendDate = LocalDateTime.parse(resumeSendDateText, formatter);
			}

			organiztionId = ((Node) xpath.evaluate("ORGANIZATION_ID", resultNode, XPathConstants.NODE))
					.getTextContent();
			crmLeadSource = ((Node) xpath.evaluate("CRMLeadSource", resultNode, XPathConstants.NODE)).getTextContent();

			NodeList columnsNode = (NodeList) xpath.evaluate("COLUMNS/COLUMN", resultNode, XPathConstants.NODESET);
			Node column;
			String name;
			String value;
			for (int i = 0; i < columnsNode.getLength(); i++) {
				column = columnsNode.item(i);
				name = ((Node) xpath.evaluate("NAME", column, XPathConstants.NODE)).getTextContent();
				value = ((Node) xpath.evaluate("VALUE", column, XPathConstants.NODE)).getTextContent();
				columns.put(name, value);
			}

			NodeList contactListsNode = (NodeList) xpath.evaluate("CONTACT_LISTS/CONTACT_LIST_ID", resultNode,
					XPathConstants.NODESET);
			String contactListId;
			for (int i = 0; i < contactListsNode.getLength(); i++) {
				contactListId = contactListsNode.item(i).getTextContent();
				contactLists.add(Long.parseLong(contactListId));
			}
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

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
		selectRecipientDataResponse.setContactLists(contactLists);

		ResponseContainer<SelectRecipientDataResponse> response = new ResponseContainer<SelectRecipientDataResponse>(
				selectRecipientDataResponse);

		return response;
	}
}
