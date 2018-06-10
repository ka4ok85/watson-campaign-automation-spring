package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.DeleteListOptions;
import com.github.ka4ok85.wca.response.DeleteListResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA DeleteList API.</strong> It builds XML
 * request for DeleteList API using
 * {@link com.github.ka4ok85.wca.options.DeleteListOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.DeleteListResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * It relies on parent {@link com.github.ka4ok85.wca.command.AbstractJobCommand}
 * for polling mechanism.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class DeleteListCommand extends AbstractJobCommand<DeleteListResponse, DeleteListOptions> {

	private static final String apiMethodName = "DeleteList";

	@Autowired
	private DeleteListResponse deleteListResponse;

	/**
	 * Builds XML request for DeleteList API using
	 * {@link com.github.ka4ok85.wca.options.DeleteListOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(DeleteListOptions options) {
		Objects.requireNonNull(options, "DeleteListOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		if (options.getListId() != null && options.getListId() > 0) {
			Element listID = doc.createElement("LIST_ID");
			listID.setTextContent(options.getListId().toString());
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
	}

	/**
	 * Reads DeleteList API response into
	 * {@link com.github.ka4ok85.wca.response.DeleteListResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for DeleteList API
	 * @param options
	 *            - settings for API call
	 * @return POJO Delete List Response
	 */
	@Override
	public ResponseContainer<DeleteListResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, DeleteListOptions options) {
		String description = jobResponse.getJobDescription();
		boolean ignoreDependentMailings = Boolean.valueOf(jobResponse.getParameters().get("IGNORE_DEPENDENT_MAILINGS"));
		Long listId = Long.parseLong(jobResponse.getParameters().get("SOURCE_LIST_ID"));
		Long numberRemoved = Long.parseLong(jobResponse.getParameters().get("NUM_REMOVED"));
		String listName = jobResponse.getParameters().get("LIST_NAME");

		deleteListResponse.setJobId(jobPollingContainer.getJobId());
		deleteListResponse.setDescription(description);
		deleteListResponse.setIgnoreDependentMailings(ignoreDependentMailings);
		deleteListResponse.setListId(listId);
		deleteListResponse.setListName(listName);
		deleteListResponse.setNumberRemoved(numberRemoved);
		ResponseContainer<DeleteListResponse> response = new ResponseContainer<DeleteListResponse>(deleteListResponse);

		return response;
	}

}
