package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.options.OptOutRecipientOptions;
import com.github.ka4ok85.wca.response.OptOutRecipientResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA OptOutRecipient API.</strong> It
 * builds XML request for OptOutRecipient API using
 * {@link com.github.ka4ok85.wca.options.OptOutRecipientOptions} and reads
 * response into {@link com.github.ka4ok85.wca.response.OptOutRecipientResponse}
 * .
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
public class OptOutRecipientCommand extends AbstractInstantCommand<OptOutRecipientResponse, OptOutRecipientOptions> {

	private static final String apiMethodName = "OptOutRecipient";

	@Autowired
	private OptOutRecipientResponse optOutRecipientResponse;

	/**
	 * Builds XML request for OptOutRecipient API using
	 * {@link com.github.ka4ok85.wca.options.OptOutRecipientOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(OptOutRecipientOptions options) {
		Objects.requireNonNull(options, "OptOutRecipientOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		if (options.getEmail() != null) {
			Element email = doc.createElement("EMAIL");
			email.setTextContent(options.getEmail());
			addChildNode(email, currentNode);
		} else if (options.getRecipientId() != null && options.getJobId() != null && options.getMailingId() != null) {
			Element recipientId = doc.createElement("RECIPIENT_ID");
			recipientId.setTextContent(options.getRecipientId());
			addChildNode(recipientId, currentNode);

			Element jobId = doc.createElement("JOB_ID");
			jobId.setTextContent(options.getJobId());
			addChildNode(jobId, currentNode);

			Element mailingId = doc.createElement("MAILING_ID");
			mailingId.setTextContent(options.getMailingId().toString());
			addChildNode(mailingId, currentNode);
		} else {
			throw new RuntimeException(
					"You must provide either Email or Combination of RecipietId, JobId and MailingId");
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

	/**
	 * Reads OptOutRecipient API response into
	 * {@link com.github.ka4ok85.wca.response.OptOutRecipientResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO OptOutRecipient Response
	 */
	@Override
	public ResponseContainer<OptOutRecipientResponse> readResponse(Node resultNode, OptOutRecipientOptions options) {
		ResponseContainer<OptOutRecipientResponse> response = new ResponseContainer<OptOutRecipientResponse>(
				optOutRecipientResponse);

		return response;
	}
}
