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
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.DeleteListOptions;
import com.github.ka4ok85.wca.response.DeleteListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class DeleteListCommand extends AbstractCommand<DeleteListResponse, DeleteListOptions> {

	private static final String apiMethodName = "DeleteList";
	private static final Logger log = LoggerFactory.getLogger(DeleteListCommand.class);

	@Autowired
	private DeleteListResponse deleteListResponse;

	@Override
	public ResponseContainer<DeleteListResponse> executeCommand(DeleteListOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "CreateContactListOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		if (options.getListId() != null && options.getListId() > 0) {
			Element listID = doc.createElement("LIST_ID");
			listID.setTextContent(options.getListId()
					.toString());
			addChildNode(listID, currentNode);
		} else if (options.getListName() != null && !options.getListName().isEmpty()) {
			Element listName = doc.createElement("LIST_NAME");
			listName.setTextContent(options.getListName());
			addChildNode(listName, currentNode);
			
			Element visibility = doc.createElement("LIST_VISIBILITY");
			visibility.setTextContent(options.getVisiblity().value().toString());
			addChildNode(visibility, currentNode);
		}

		addBooleanParameter(methodElement, "KEEP_DETAILS", options.isKeepListDetails());
		addBooleanParameter(methodElement, "RECURSIVE", options.isRecursive());

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Long jobId;
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);

			jobId = Long.parseLong(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);

			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (!jobResponse.isComplete()) {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is "
						+ jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		deleteListResponse.setJobId(jobId);
		ResponseContainer<DeleteListResponse> response = new ResponseContainer<DeleteListResponse>(deleteListResponse);

		return response;
	}
}
