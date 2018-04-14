package com.github.ka4ok85.wca.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.InsertUpdateRelationalTableFailure;

public class InsertUpdateRelationalTableCommand
		extends AbstractCommand<InsertUpdateRelationalTableResponse, InsertUpdateRelationalTableOptions> {

	private static final String apiMethodName = "InsertUpdateRelationalTable";
	private static final Logger log = LoggerFactory.getLogger(InsertUpdateRelationalTableCommand.class);

	@Override
	public ResponseContainer<InsertUpdateRelationalTableResponse> executeCommand(
			InsertUpdateRelationalTableOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "InsertUpdateRelationalTableOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element tableId = doc.createElement("TABLE_ID");
		tableId.setTextContent(options.getTableId().toString());
		addChildNode(tableId, currentNode);

		if (options.getRows().size() > 0) {
			if (options.getRows().size() > 100) {
				throw new RuntimeException(
						"Only one hundred rows may be passed in a single InsertUpdateRelationalTable call. Provided rows count: "
								+ options.getRows().size());
			}

			Element rows = doc.createElement("ROWS");
			addChildNode(rows, currentNode);
			for (Map<String, String> tableRow : options.getRows()) {
				if (tableRow == null || tableRow.size() == 0) {
					throw new RuntimeException("Row can not be empty");
				}

				Element row = doc.createElement("ROW");
				addChildNode(row, rows);

				for (Entry<String, String> entry : tableRow.entrySet()) {
					Element column = doc.createElement("COLUMN");
					column.setAttribute("name", entry.getKey());
					CDATASection cdata = doc.createCDATASection(entry.getValue());
					column.appendChild(cdata);
					addChildNode(column, row);
				}
			}
		} else {
			throw new RuntimeException("You must provide Rows");
		}

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		List<InsertUpdateRelationalTableFailure> failures = new ArrayList<InsertUpdateRelationalTableFailure>();
		try {
			NodeList failuresNode = (NodeList) xpath.evaluate("FAILURES/FAILURE", resultNode, XPathConstants.NODESET);
			Node failureNode;

			for (int i = 0; i < failuresNode.getLength(); i++) {
				InsertUpdateRelationalTableFailure insertUpdateRelationalTableFailure = new InsertUpdateRelationalTableFailure();
				failureNode = failuresNode.item(i);

				NamedNodeMap attributes = failureNode.getAttributes();
				String failureType = attributes.getNamedItem("failure_type").getTextContent();
				String description = attributes.getNamedItem("description").getTextContent();

				insertUpdateRelationalTableFailure.setFailureType(failureType);
				insertUpdateRelationalTableFailure.setDescription(description);

				NodeList columnsNode = (NodeList) xpath.evaluate("COLUMN", failureNode, XPathConstants.NODESET);
				List<Map<String, String>> failedColumns = new ArrayList<Map<String, String>>();
				for (int j = 0; j < columnsNode.getLength(); j++) {
					Node columnNode = columnsNode.item(j);
					Map<String, String> columnValues = new HashMap<String, String>();
					NamedNodeMap columnNodeAttributes = columnNode.getAttributes();
					columnValues.put(columnNodeAttributes.getNamedItem("name").getTextContent(),
							columnNode.getTextContent());
					failedColumns.add(columnValues);
				}

				insertUpdateRelationalTableFailure.setColumns(failedColumns);
				failures.add(insertUpdateRelationalTableFailure);
			}
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		InsertUpdateRelationalTableResponse insertUpdateRelationalTableResponse = new InsertUpdateRelationalTableResponse();
		insertUpdateRelationalTableResponse.setFailures(failures);
		ResponseContainer<InsertUpdateRelationalTableResponse> response = new ResponseContainer<InsertUpdateRelationalTableResponse>(
				insertUpdateRelationalTableResponse);

		return response;
	}
}
