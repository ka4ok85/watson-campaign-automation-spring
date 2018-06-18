package com.github.ka4ok85.wca.command;

import java.time.format.DateTimeFormatter;
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
import com.github.ka4ok85.wca.options.UpdateRecipientOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.UpdateRecipientResponse;

/**
 * <strong>Class for interacting with WCA UpdateRecipient API.</strong> It
 * builds XML request for UpdateRecipient API using
 * {@link com.github.ka4ok85.wca.options.UpdateRecipientOptions} and reads
 * response into {@link com.github.ka4ok85.wca.response.UpdateRecipientResponse}
 * .
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * <strong>ATTN: </strong>For Opt-in opted-out recipient again put in Columns
 * "OPT_OUT" name with "false" value
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class UpdateRecipientCommand extends AbstractInstantCommand<UpdateRecipientResponse, UpdateRecipientOptions> {

	private static final String apiMethodName = "UpdateRecipient";

	@Autowired
	private UpdateRecipientResponse updateRecipientResponse;

	/**
	 * Builds XML request for UpdateRecipient API using
	 * {@link com.github.ka4ok85.wca.options.UpdateRecipientOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(UpdateRecipientOptions options) {
		Objects.requireNonNull(options, "UpdateRecipientOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		if (options.getOldEmail() != null) {
			Element oldEmail = doc.createElement("OLD_EMAIL");
			oldEmail.setTextContent(options.getOldEmail());
			addChildNode(oldEmail, currentNode);
		} else if (options.getRecipientId() != null) {
			Element recipientId = doc.createElement("RECIPIENT_ID");
			recipientId.setTextContent(options.getRecipientId().toString());
			addChildNode(recipientId, currentNode);
		} else if (options.getEncodedRecipientId() != null) {
			Element encodedRecipientId = doc.createElement("ENCODED_RECIPIENT_ID");
			encodedRecipientId.setTextContent(options.getEncodedRecipientId());
			addChildNode(encodedRecipientId, currentNode);
		} else if (!options.getSyncFields().isEmpty()) {
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
		} else {
			throw new RuntimeException(
					"Unique key columns must be part of the submission with column names and values");
		}

		addBooleanParameter(methodElement, "ALLOW_HTML", options.isAllowHtml());
		addBooleanParameter(methodElement, "SEND_AUTOREPLY", options.isSendAutoReply());

		if (options.getVisitorKey() != null) {
			Element visitorKey = doc.createElement("VISITOR_KEY");
			visitorKey.setTextContent(options.getVisitorKey());
			addChildNode(visitorKey, currentNode);
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

		if (options.isSnoozed()) {
			Element snoozeSetting = doc.createElement("SNOOZE_SETTINGS");
			addChildNode(snoozeSetting, currentNode);
			Element snoozed = doc.createElement("SNOOZED");
			snoozed.setTextContent("TRUE");
			addChildNode(snoozed, snoozeSetting);

			if (options.getSnoozeDaysToSnooze() != null) {
				Element snoozeDays = doc.createElement("DAYS_TO_SNOOZE");
				snoozeDays.setTextContent(options.getSnoozeDaysToSnooze().toString());
				addChildNode(snoozeDays, snoozeSetting);
			} else if (options.getSnoozeResumeSendDate() != null) {
				Element resumeSendDate = doc.createElement("RESUME_SEND_DATE");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/uuuu");
				resumeSendDate.setTextContent(options.getSnoozeResumeSendDate().format(formatter));
				addChildNode(resumeSendDate, snoozeSetting);
			} else {
				throw new RuntimeException(
						"Snoozed contacts must have either DaysToSnooze or ResumeSendDate option set");
			}
		}
	}

	/**
	 * Reads UpdateRecipient API response into
	 * {@link com.github.ka4ok85.wca.response.UpdateRecipientResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO UpdateRecipient Response
	 */
	@Override
	public ResponseContainer<UpdateRecipientResponse> readResponse(Node resultNode, UpdateRecipientOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		boolean visitorAssociation = false;
		Long recipientId;
		try {
			recipientId = Long.parseLong(
					((Node) xpath.evaluate("RecipientId", resultNode, XPathConstants.NODE)).getTextContent());
			Node visitorAssociationNode = (Node) xpath.evaluate("VISITOR_ASSOCIATION", resultNode, XPathConstants.NODE);
			if (visitorAssociationNode != null && visitorAssociationNode.getTextContent().equals("TRUE")) {
				visitorAssociation = true;
			}
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		updateRecipientResponse.setRecipientId(recipientId);
		updateRecipientResponse.setVisitorAssociation(visitorAssociation);

		ResponseContainer<UpdateRecipientResponse> response = new ResponseContainer<UpdateRecipientResponse>(
				updateRecipientResponse);

		return response;
	}
}
