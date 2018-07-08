package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.constants.ColumnValueAction;
import com.github.ka4ok85.wca.options.SetColumnValueOptions;
import com.github.ka4ok85.wca.response.SetColumnValueResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

/**
 * <strong>Class for interacting with WCA ExportTable API.</strong> It builds
 * XML request for ExportTable API using
 * {@link com.github.ka4ok85.wca.options.SetColumnValueOptions} and reads
 * response into {@link com.github.ka4ok85.wca.response.SetColumnValueResponse}.
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
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class SetColumnValueCommand extends AbstractJobCommand<SetColumnValueResponse, SetColumnValueOptions> {

	private static final String apiMethodName = "SetColumnValue";

	@Autowired
	private SetColumnValueResponse setColumnValueResponse;

	/**
	 * Builds XML request for ExportTable API using
	 * {@link com.github.ka4ok85.wca.options.SetColumnValueOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(SetColumnValueOptions options) {
		Objects.requireNonNull(options, "SetColumnValueOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element columnName = doc.createElement("COLUMN_NAME");
		columnName.setTextContent(options.getColumnName());
		addChildNode(columnName, currentNode);

		Element action = doc.createElement("ACTION");
		action.setTextContent(options.getColumnValueAction().value().toString());
		addChildNode(action, currentNode);

		if (options.getColumnValue() != null) {
			if (options.getColumnValueAction() != ColumnValueAction.UPDATE) {
				throw new RuntimeException("Column Value Action must be Update");
			}

			Element columnValue = doc.createElement("COLUMN_VALUE");
			columnValue.setTextContent(options.getColumnValue());
			addChildNode(columnValue, currentNode);
		}

	}

	/**
	 * Reads ExportTable API response into
	 * {@link com.github.ka4ok85.wca.response.SetColumnValueResponse}
	 * 
	 * @param jobPollingContainer
	 *            - raw POJO response for Schedule a Job API
	 * @param jobResponse
	 *            - raw POJO response for ExportTable API
	 * @param options
	 *            - settings for API call
	 * @return POJO Export Table Response
	 */
	@Override
	public ResponseContainer<SetColumnValueResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, SetColumnValueOptions options) {
		String description = jobResponse.getJobDescription();
		String listName = jobResponse.getParameters().get("LIST_NAME");

		setColumnValueResponse.setDescription(description);
		setColumnValueResponse.setListName(listName);
		setColumnValueResponse.setJobId(jobPollingContainer.getJobId());
		ResponseContainer<SetColumnValueResponse> response = new ResponseContainer<SetColumnValueResponse>(
				setColumnValueResponse);

		return response;
	}

}
