package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;

import com.github.ka4ok85.wca.options.JoinTableOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.JoinTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.JobPollingContainer;

@Service
@Scope("prototype")
public class JoinTableCommand extends AbstractJobCommand<JoinTableResponse, JoinTableOptions> {

	private static final String apiMethodName = "JoinTable";

	@Autowired
	private JoinTableResponse joinTableResponse;

	@Override
	public void buildXmlRequest(JoinTableOptions options) {
		Objects.requireNonNull(options, "JoinTableOptions must not be null");

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

		if (options.getListId() != null) {
			Element listId = doc.createElement("LIST_ID");
			listId.setTextContent(options.getListId().toString());
			addChildNode(listId, currentNode);
		} else if (options.getListName() != null) {
			Element listName = doc.createElement("LIST_NAME");
			listName.setTextContent(options.getListName());
			addChildNode(listName, currentNode);
		} else {
			throw new RuntimeException("You must specify either List ID or List Name");
		}

		if (options.getListVisibility() != null) {
			Element listVisibility = doc.createElement("LIST_VISIBILITY");
			listVisibility.setTextContent(options.getListVisibility().value().toString());
			addChildNode(listVisibility, currentNode);
		}

		if (options.getIsRemoveRelationship()) {
			addBooleanParameter(methodElement, "REMOVE", options.getIsRemoveRelationship());
		}

		for (Entry<String, String> entry : options.getMapFields().entrySet()) {
			Element mapField = doc.createElement("MAP_FIELD");
			addChildNode(mapField, currentNode);

			Element listColumn = doc.createElement("LIST_FIELD");
			CDATASection cdata = doc.createCDATASection(entry.getKey());
			listColumn.appendChild(cdata);
			addChildNode(listColumn, mapField);

			Element tableColumn = doc.createElement("TABLE_FIELD");
			cdata = doc.createCDATASection(entry.getValue());
			tableColumn.appendChild(cdata);
			addChildNode(tableColumn, mapField);
		}
	}

	@Override
	public ResponseContainer<JoinTableResponse> readResponse(JobPollingContainer jobPollingContainer,
			JobResponse jobResponse, JoinTableOptions options) {

		String description = jobResponse.getJobDescription();
		joinTableResponse.setJobId(jobPollingContainer.getJobId());
		joinTableResponse.setDescription(description);
		ResponseContainer<JoinTableResponse> response = new ResponseContainer<JoinTableResponse>(joinTableResponse);

		return response;
	}

}
