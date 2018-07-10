package com.github.ka4ok85.wca.command;

import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.constants.RelationalTableColumnType;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.CreateTableOptions;
import com.github.ka4ok85.wca.options.containers.RelationalTableColumn;
import com.github.ka4ok85.wca.response.CreateTableResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA CreateTable API.</strong> It builds
 * XML request for CreateTable API using
 * {@link com.github.ka4ok85.wca.options.CreateTableOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.CreateTableResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 * <p>
 * <strong>ATTN: </strong>No way to specify Visibility. RTs is created in Shared
 * Folder
 * </p>
 * 
 * @author Evgeny Makovetsky
 * @since 0.0.2
 */
@Service
@Scope("prototype")
public class CreateTableCommand extends AbstractInstantCommand<CreateTableResponse, CreateTableOptions> {

	private static final String apiMethodName = "CreateTable";
	private static final Logger log = LoggerFactory.getLogger(CreateTableCommand.class);

	@Autowired
	private CreateTableResponse createTableResponse;

	/**
	 * Builds XML request for CreateTable API using
	 * {@link com.github.ka4ok85.wca.options.CreateTableOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(CreateTableOptions options) {
		Objects.requireNonNull(options, "CreateTableOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element tableName = doc.createElement("TABLE_NAME");
		tableName.setTextContent(options.getTableName());
		addChildNode(tableName, currentNode);

		if (options.getColumns().size() > 0) {
			Element columns = doc.createElement("COLUMNS");
			addChildNode(columns, currentNode);
			for (RelationalTableColumn tableColumn : options.getColumns()) {
				Element column = doc.createElement("COLUMN");
				addChildNode(column, columns);

				Element columnName = doc.createElement("NAME");
				CDATASection cdata = doc.createCDATASection(tableColumn.getName());
				columnName.appendChild(cdata);
				addChildNode(columnName, column);

				Element columnType = doc.createElement("TYPE");
				cdata = doc.createCDATASection(tableColumn.getType().value());
				columnType.appendChild(cdata);
				addChildNode(columnType, column);

				if (tableColumn.getIsRequired()) {
					Element columnIsRequired = doc.createElement("IS_REQUIRED");
					columnIsRequired.setTextContent("true");
					addChildNode(columnIsRequired, column);
				}

				if (tableColumn.getIsKeyColumn()) {
					Element columnIsKeyColumn = doc.createElement("KEY_COLUMN");
					columnIsKeyColumn.setTextContent("true");
					addChildNode(columnIsKeyColumn, column);
				}

				if (tableColumn.getDefaultValue() != null) {
					Element defaultValueColumn = doc.createElement("DEFAULT_VALUE");
					cdata = doc.createCDATASection(tableColumn.getDefaultValue());
					defaultValueColumn.appendChild(cdata);
					addChildNode(defaultValueColumn, column);
				}

				if (tableColumn.getType().equals(RelationalTableColumnType.SELECTION)) {
					Element selectionValues = doc.createElement("SELECTION_VALUES");
					addChildNode(selectionValues, column);
					for (String selection : tableColumn.getSelectionValues()) {
						Element selectionValue = doc.createElement("VALUE");
						cdata = doc.createCDATASection(selection);
						selectionValue.appendChild(cdata);
						addChildNode(selectionValue, selectionValues);
					}
				}
			}
		}
	}

	/**
	 * Reads CreateTable API response into
	 * {@link com.github.ka4ok85.wca.response.CreateTableResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO CreateTable Response
	 */
	@Override
	public ResponseContainer<CreateTableResponse> readResponse(Node resultNode, CreateTableOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Long tableId;
		try {
			Node tableIdNode = (Node) xpath.evaluate("TABLE_ID", resultNode, XPathConstants.NODE);
			tableId = Long.parseLong(tableIdNode.getTextContent());
			log.debug("Created Relational Table ID is {}", tableId);
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		createTableResponse.setTableId(tableId);
		ResponseContainer<CreateTableResponse> response = new ResponseContainer<CreateTableResponse>(
				createTableResponse);

		return response;
	}
}
