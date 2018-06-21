package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.options.AddContactToProgramOptions;
import com.github.ka4ok85.wca.response.AddContactToProgramResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA AddContactToProgram API.</strong> It
 * builds XML request for AddContactToProgram API using
 * {@link com.github.ka4ok85.wca.options.AddContactToProgramOptions} and reads
 * response into
 * {@link com.github.ka4ok85.wca.response.AddContactToProgramResponse}.
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
public class AddContactToProgramCommand
		extends AbstractInstantCommand<AddContactToProgramResponse, AddContactToProgramOptions> {

	private static final String apiMethodName = "AddContactToProgram";

	@Autowired
	private AddContactToProgramResponse addContactToProgramResponse;

	/**
	 * Builds XML request for AddContactToProgram API using
	 * {@link com.github.ka4ok85.wca.options.AddContactToProgramOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(AddContactToProgramOptions options) {
		Objects.requireNonNull(options, "AddContactToProgramOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element programID = doc.createElement("PROGRAM_ID");
		programID.setTextContent(options.getProgramId().toString());
		addChildNode(programID, currentNode);

		Element contactID = doc.createElement("CONTACT_ID");
		contactID.setTextContent(options.getContactId().toString());
		addChildNode(contactID, currentNode);

	}

	/**
	 * Reads AddContactToProgram API response into
	 * {@link com.github.ka4ok85.wca.response.AddContactToProgramResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO AddContactToProgram Response
	 */
	@Override
	public ResponseContainer<AddContactToProgramResponse> readResponse(Node resultNode,
			AddContactToProgramOptions options) {
		ResponseContainer<AddContactToProgramResponse> response = new ResponseContainer<AddContactToProgramResponse>(
				addContactToProgramResponse);

		return response;
	}
}