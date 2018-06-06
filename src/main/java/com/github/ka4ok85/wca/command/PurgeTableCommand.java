package com.github.ka4ok85.wca.command;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.PurgeTableOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.PurgeTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@Service
@Scope("prototype")
public class PurgeTableCommand extends AbstractJobCommand<PurgeTableResponse, PurgeTableOptions> {

	private static final String apiMethodName = "PurgeTable";

	@Autowired
	private PurgeTableResponse purgeTableResponse;

	@Override
	public void buildXmlRequest(PurgeTableOptions options) {
		Objects.requireNonNull(options, "PurgeTableOptions must not be null");

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

		if (options.getDeleteBefore() != null) {
			Element deleteBefore = doc.createElement("DELETE_BEFORE");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
			deleteBefore.setTextContent(options.getDeleteBefore().format(formatter));
			addChildNode(deleteBefore, currentNode);
		}
	}

	@Override
	public ResponseContainer<PurgeTableResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, PurgeTableOptions options) {
		Long jobId = jobPollingContainer.getJobId();
		String description = jobResponse.getJobDescription();

		purgeTableResponse.setJobId(jobId);
		purgeTableResponse.setDescription(description);
		ResponseContainer<PurgeTableResponse> response = new ResponseContainer<PurgeTableResponse>(purgeTableResponse);

		return response;
	}

}
