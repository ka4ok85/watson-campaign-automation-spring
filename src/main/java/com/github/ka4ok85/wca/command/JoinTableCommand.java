package com.github.ka4ok85.wca.command;

import java.util.Map.Entry;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.JoinTableOptions;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.JoinTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

public class JoinTableCommand extends AbstractCommand<JoinTableResponse, JoinTableOptions> {

	private static final String apiMethodName = "JoinTable";
	private static final Logger log = LoggerFactory.getLogger(JoinTableCommand.class);

	@Override
	public ResponseContainer<JoinTableResponse> executeCommand(JoinTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
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
			Element listName = doc.createElement("List_NAME");
			listName.setTextContent(options.getListName());
			addChildNode(listName, currentNode);
		} else {
			throw new RuntimeException("You must specify either List ID or List Name");
		}

		if (options.getListVisibility() != null) {
			Element listVisibility = doc.createElement("List_VISIBILITY");
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

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		try {
			Node jobIdNode = (Node) xpath.evaluate("JOB_ID", resultNode, XPathConstants.NODE);

			final Long jobId = Long.parseLong(jobIdNode.getTextContent());
			log.debug("Job ID {} is being excuted", jobId);

			final JobResponse jobResponse = waitUntilJobIsCompleted(jobId);
			log.debug("Job Response is {}", jobResponse);
			if (jobResponse.isComplete()) {
				log.debug("Job is completed");
			} else {
				log.error("State inconsistency for Job ID {}", jobId);
				throw new JobBadStateException("Job ID " + jobId + " was reported as Completed, but actual State is "
						+ jobResponse.getJobStatus());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		JoinTableResponse joinTableResponse = new JoinTableResponse();
		ResponseContainer<JoinTableResponse> response = new ResponseContainer<JoinTableResponse>(joinTableResponse);

		return response;
	}
}
