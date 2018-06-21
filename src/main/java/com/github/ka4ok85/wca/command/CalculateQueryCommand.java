package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.CalculateQueryOptions;
import com.github.ka4ok85.wca.response.CalculateQueryResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA CalculateQuery API.</strong> It builds XML
 * request for CalculateQuery API using
 * {@link com.github.ka4ok85.wca.options.CalculateQueryOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.CalculateQueryResponse}.
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
public class CalculateQueryCommand extends AbstractJobCommand<CalculateQueryResponse, CalculateQueryOptions> {

	private static final String apiMethodName = "CalculateQuery";

	@Autowired
	private CalculateQueryResponse calculateQueryResponse;

	/**
	 * Builds XML request for CalculateQuery API using
	 * {@link com.github.ka4ok85.wca.options.CalculateQueryOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(CalculateQueryOptions options) {
		Objects.requireNonNull(options, "CalculateQueryOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element queryId = doc.createElement("QUERY_ID");
		queryId.setTextContent(options.getQueryId().toString());
		addChildNode(queryId, currentNode);
	}

	/**
	 * Reads CalculateQuery API response into
	 * {@link com.github.ka4ok85.wca.response.CalculateQueryResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for CalculateQuery API
	 * @param options
	 *            - settings for API call
	 * @return POJO Calculate Query Response
	 */
	@Override
	public ResponseContainer<CalculateQueryResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, CalculateQueryOptions options) {
		String description = jobResponse.getJobDescription();
		String listName = jobResponse.getParameters().get("LIST_NAME");
		Long listSize = Long.parseLong(jobResponse.getParameters().get("LIST_SIZE"));
		Long parentListId = Long.parseLong(jobResponse.getParameters().get("PARENT_LIST_ID"));
		String parentListName = jobResponse.getParameters().get("PARENT_LIST_NAME");
		String parentListType = jobResponse.getParameters().get("PARENT_LIST_TYPE");
		Long queryExpressionId = Long.parseLong(jobResponse.getParameters().get("QUERY_EXPRESSION_ID"));

		calculateQueryResponse.setJobId(jobPollingContainer.getJobId());
		calculateQueryResponse.setDescription(description);
		calculateQueryResponse.setListName(listName);
		calculateQueryResponse.setListSize(listSize);
		calculateQueryResponse.setParentListId(parentListId);
		calculateQueryResponse.setParentListName(parentListName);
		calculateQueryResponse.setParentListType(parentListType);
		calculateQueryResponse.setQueryExpressionId(queryExpressionId);
		ResponseContainer<CalculateQueryResponse> response = new ResponseContainer<CalculateQueryResponse>(
				calculateQueryResponse);

		return response;
	}
}
