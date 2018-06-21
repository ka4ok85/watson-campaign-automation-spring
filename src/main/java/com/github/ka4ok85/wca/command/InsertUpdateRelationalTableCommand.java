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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions;
import com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.RelationalTableRecordFailure;

/**
 * <strong>Class for interacting with WCA InsertUpdateRelationalTable
 * API.</strong> It builds XML request for InsertUpdateRelationalTable API using
 * {@link com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions} and
 * reads response into
 * {@link com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class InsertUpdateRelationalTableCommand
		extends AbstractInstantCommand<InsertUpdateRelationalTableResponse, InsertUpdateRelationalTableOptions> {

	private static final String apiMethodName = "InsertUpdateRelationalTable";

	@Autowired
	private InsertUpdateRelationalTableResponse insertUpdateRelationalTableResponse;

	/**
	 * Builds XML request for InsertUpdateRelationalTable API using
	 * {@link com.github.ka4ok85.wca.options.InsertUpdateRelationalTableOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(InsertUpdateRelationalTableOptions options) {
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
	}

	/**
	 * Reads InsertUpdateRelationalTable API response into
	 * {@link com.github.ka4ok85.wca.response.InsertUpdateRelationalTableResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO InsertUpdateRelationalTable Response
	 */
	@Override
	public ResponseContainer<InsertUpdateRelationalTableResponse> readResponse(Node resultNode,
			InsertUpdateRelationalTableOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		List<RelationalTableRecordFailure> failures = new ArrayList<RelationalTableRecordFailure>();
		try {
			NodeList failuresNode = (NodeList) xpath.evaluate("FAILURES/FAILURE", resultNode, XPathConstants.NODESET);
			Node failureNode;

			for (int i = 0; i < failuresNode.getLength(); i++) {
				RelationalTableRecordFailure relationalTableRecordFailure = new RelationalTableRecordFailure();
				failureNode = failuresNode.item(i);

				NamedNodeMap attributes = failureNode.getAttributes();
				String failureType = attributes.getNamedItem("failure_type").getTextContent();
				String description = attributes.getNamedItem("description").getTextContent();

				relationalTableRecordFailure.setFailureType(failureType);
				relationalTableRecordFailure.setDescription(description);

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

				relationalTableRecordFailure.setColumns(failedColumns);
				failures.add(relationalTableRecordFailure);
			}
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		insertUpdateRelationalTableResponse.setFailures(failures);
		ResponseContainer<InsertUpdateRelationalTableResponse> response = new ResponseContainer<InsertUpdateRelationalTableResponse>(
				insertUpdateRelationalTableResponse);

		return response;
	}
}
