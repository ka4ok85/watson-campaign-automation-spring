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

@Service
@Scope("prototype")
public class AddContactToProgramCommand
		extends AbstractCommand<AddContactToProgramResponse, AddContactToProgramOptions> {

	private static final String apiMethodName = "AddContactToProgram";

	@Autowired
	private AddContactToProgramResponse addContactToProgramResponse;

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

	@Override
	public ResponseContainer<AddContactToProgramResponse> readResponse(Node resultNode,
			AddContactToProgramOptions options) {
		ResponseContainer<AddContactToProgramResponse> response = new ResponseContainer<AddContactToProgramResponse>(
				addContactToProgramResponse);

		return response;
	}
}