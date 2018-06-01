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

@Service
@Scope("prototype")
public class DeleteTableCommand extends AbstractJobCommand<DeleteTableResponse, DeleteTableOptions> {

	private static final String apiMethodName = "DeleteTable";

	@Autowired
	private DeleteTableResponse deleteTableResponse;

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

	@Override
	public ResponseContainer<DeleteTableResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, DeleteTableOptions options) {
		deleteTableResponse.setJobId(jobPollingContainer.getJobId());
		ResponseContainer<DeleteTableResponse> response = new ResponseContainer<DeleteTableResponse>(
				deleteTableResponse);

		return response;
	}

}
