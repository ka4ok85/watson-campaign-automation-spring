package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.options.AddContactToProgramOptions;
import com.github.ka4ok85.wca.response.AddContactToProgramResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class AddContactToProgramCommand
		extends AbstractCommand<AddContactToProgramResponse, AddContactToProgramOptions> {

	private static final String apiMethodName = "AddContactToProgram";
	private static final Logger log = LoggerFactory.getLogger(AddContactToProgramCommand.class);

	@Autowired
	private AddContactToProgramResponse addContactToProgramResponse;

	@Override
	public ResponseContainer<AddContactToProgramResponse> executeCommand(AddContactToProgramOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "AddContactToProgramOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element programID = doc.createElement("PROGRAM_ID");
		programID.setTextContent(options.getProgramId().toString());
		addChildNode(programID, currentNode);

		Element contactID = doc.createElement("CONTACT_ID");
		contactID.setTextContent(options.getContactId().toString());
		addChildNode(contactID, currentNode);

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		runApi(xml);

		ResponseContainer<AddContactToProgramResponse> response = new ResponseContainer<AddContactToProgramResponse>(
				addContactToProgramResponse);

		return response;
	}
}