package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.options.AddListColumnOptions;
import com.github.ka4ok85.wca.response.AddListColumnResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA AddListColumn API.</strong> It
 * builds XML request for AddListColumn API using
 * {@link com.github.ka4ok85.wca.options.AddListColumnOptions} and reads
 * response into
 * {@link com.github.ka4ok85.wca.response.AddListColumnResponse}.
 * <p>
 * It relies on Spring's {@link org.springframework.web.client.RestTemplate} for
 * synchronous client-side HTTP access.
 * </p>
 *
 * @author Evgeny Makovetsky
 * @since 0.0.4
 */
@Service
@Scope("prototype")
public class AddListColumnCommand
		extends AbstractInstantCommand<AddListColumnResponse, AddListColumnOptions> {

	private static final String apiMethodName = "AddListColumn";

	@Autowired
	private AddListColumnResponse addListColumnResponse;

	/**
	 * Builds XML request for AddListColumn API using
	 * {@link com.github.ka4ok85.wca.options.AddListColumnOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(AddListColumnOptions options) {
		Objects.requireNonNull(options, "AddListColumnOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element columnName = doc.createElement("COLUMN_NAME");
		columnName.setTextContent(options.getColumnName());
		addChildNode(columnName, currentNode);
		
		Element columnType = doc.createElement("COLUMN_TYPE");
		columnType.setTextContent(options.getColumnType().value().toString());
		addChildNode(columnType, currentNode);

		Element defaultValue = doc.createElement("DEFAULT");
		if (options.getDefaultValue() != null && options.getDefaultValue().isEmpty() == false) {
			defaultValue.setTextContent(options.getDefaultValue());
		}
		
		addChildNode(defaultValue, currentNode);
		
		if (options.getSelectionValues().size() > 0) {
			Element selectionValues = doc.createElement("SELECTION_VALUES");
			addChildNode(selectionValues, currentNode);
			for (String selectionValue : options.getSelectionValues()) {
				Element selectionValueElement = doc.createElement("VALUE");
				selectionValueElement.setTextContent(selectionValue);
				addChildNode(selectionValueElement, selectionValues);
			}
		} 
	}

	/**
	 * Reads AddListColumn API response into
	 * {@link com.github.ka4ok85.wca.response.AddListColumnResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO AddListColumn Response
	 */
	@Override
	public ResponseContainer<AddListColumnResponse> readResponse(Node resultNode,
			AddListColumnOptions options) {
		ResponseContainer<AddListColumnResponse> response = new ResponseContainer<AddListColumnResponse>(
				addListColumnResponse);

		return response;
	}
}