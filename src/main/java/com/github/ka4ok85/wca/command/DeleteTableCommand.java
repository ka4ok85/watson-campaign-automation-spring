package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.DeleteTableOptions;
import com.github.ka4ok85.wca.response.DeleteTableResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA DeleteTable API.</strong> It builds
 * XML request for DeleteTable API using
 * {@link com.github.ka4ok85.wca.options.DeleteTableOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.DeleteTableResponse}.
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
public class DeleteTableCommand extends AbstractJobCommand<DeleteTableResponse, DeleteTableOptions> {

	private static final String apiMethodName = "DeleteTable";

	@Autowired
	private DeleteTableResponse deleteTableResponse;

	/**
	 * Builds XML request for DeleteTable API using
	 * {@link com.github.ka4ok85.wca.options.DeleteTableOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(DeleteTableOptions options) {
		Objects.requireNonNull(options, "DeleteTableOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		if (options.getTableId() != null) {
			Element tableId = doc.createElement("TABLE_ID");
			tableId.setTextContent(options.getTableId().toString());
			addChildNode(tableId, currentNode);
		} else if (options.getTableName() != null) {
			Element tableName = doc.createElement("TABLE_NAME");
			tableName.setTextContent(options.getTableName());
			addChildNode(tableName, currentNode);
		} else {
			throw new RuntimeException("You must specify either Table ID or Table Name");
		}

		if (options.getTableVisibility() != null) {
			Element tableVisibility = doc.createElement("TABLE_VISIBILITY");
			tableVisibility.setTextContent(options.getTableVisibility().value().toString());
			addChildNode(tableVisibility, currentNode);
		}
	}

	/**
	 * Reads DeleteTable API response into
	 * {@link com.github.ka4ok85.wca.response.DeleteTableResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for DeleteTable API
	 * @param options
	 *            - settings for API call
	 * @return POJO Delete Table Response
	 */
	@Override
	public ResponseContainer<DeleteTableResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, DeleteTableOptions options) {

		String description = jobResponse.getJobDescription();
		boolean ignoreDependentMailings = Boolean.valueOf(jobResponse.getParameters().get("IGNORE_DEPENDENT_MAILINGS"));
		boolean isKeepListDetails = Boolean.valueOf(jobResponse.getParameters().get("IS_KEEP_LIST_DETAILS"));
		Long listId = Long.parseLong(jobResponse.getParameters().get("SOURCE_LIST_ID"));
		Long numberRemoved = Long.parseLong(jobResponse.getParameters().get("NUM_REMOVED"));
		String listName = jobResponse.getParameters().get("LIST_NAME");

		deleteTableResponse.setJobId(jobPollingContainer.getJobId());
		deleteTableResponse.setDescription(description);
		deleteTableResponse.setIgnoreDependentMailings(ignoreDependentMailings);
		deleteTableResponse.setKeepListDetails(isKeepListDetails);
		deleteTableResponse.setListId(listId);
		deleteTableResponse.setListName(listName);
		deleteTableResponse.setNumberRemoved(numberRemoved);

		ResponseContainer<DeleteTableResponse> response = new ResponseContainer<DeleteTableResponse>(
				deleteTableResponse);

		return response;
	}

}
