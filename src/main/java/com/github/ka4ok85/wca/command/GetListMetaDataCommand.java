package com.github.ka4ok85.wca.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.constants.ListColumnType;
import com.github.ka4ok85.wca.constants.Visibility;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.GetListMetaDataOptions;
import com.github.ka4ok85.wca.response.GetListMetaDataResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.ListColumnLimited;

/**
 * <strong>Class for interacting with WCA GetListMetaData API.</strong> It
 * builds XML request for GetListMetaData API using
 * {@link com.github.ka4ok85.wca.options.GetListMetaDataOptions} and reads
 * response into {@link com.github.ka4ok85.wca.response.GetListMetaDataResponse}
 * .
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
public class GetListMetaDataCommand extends AbstractInstantCommand<GetListMetaDataResponse, GetListMetaDataOptions> {

	private static final String apiMethodName = "GetListMetaData";

	@Autowired
	private GetListMetaDataResponse getListMetaDataResponse;

	/**
	 * Builds XML request for GetListMetaData API using
	 * {@link com.github.ka4ok85.wca.options.GetListMetaDataOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(GetListMetaDataOptions options) {
		Objects.requireNonNull(options, "GetListMetaDataOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listId = doc.createElement("LIST_ID");
		listId.setTextContent(options.getListId().toString());
		addChildNode(listId, currentNode);
	}

	/**
	 * Reads GetListMetaData API response into
	 * {@link com.github.ka4ok85.wca.response.GetListMetaDataResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO GetListMetaData Response
	 */
	@Override
	public ResponseContainer<GetListMetaDataResponse> readResponse(Node resultNode, GetListMetaDataOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		Long id;
		String name;
		Integer type;
		Long size;
		Long numOptOuts;
		Long numUndeliverables;
		LocalDateTime lastModified = null;
		LocalDateTime lastConfigured = null;
		LocalDateTime created = null;
		Visibility visibility = null;
		String userId;
		String organizationId;
		Long parentDatabaseId = null;
		boolean optInFormDefined;
		boolean optOutFormDefined;
		boolean profileFormDefined;
		boolean optInAutoreplyDefined;
		boolean profileAutoreplyDefined;
		List<ListColumnLimited> columns = new ArrayList<ListColumnLimited>();
		List<String> keyColumns = new ArrayList<String>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
		try {
			id = Long.parseLong(((Node) xpath.evaluate("ID", resultNode, XPathConstants.NODE)).getTextContent());
			name = ((Node) xpath.evaluate("NAME", resultNode, XPathConstants.NODE)).getTextContent();
			type = Integer.parseInt(((Node) xpath.evaluate("TYPE", resultNode, XPathConstants.NODE)).getTextContent());
			size = Long.parseLong(((Node) xpath.evaluate("SIZE", resultNode, XPathConstants.NODE)).getTextContent());
			numOptOuts = Long.parseLong(
					((Node) xpath.evaluate("NUM_OPT_OUTS", resultNode, XPathConstants.NODE)).getTextContent());
			numUndeliverables = Long.parseLong(
					((Node) xpath.evaluate("NUM_UNDELIVERABLE", resultNode, XPathConstants.NODE)).getTextContent());
			String lastModifiedText = ((Node) xpath.evaluate("LAST_MODIFIED", resultNode, XPathConstants.NODE))
					.getTextContent();
			if (lastModifiedText != "") {
				lastModified = LocalDateTime.parse(lastModifiedText, formatter);
			}

			String lastConfiguredText = ((Node) xpath.evaluate("LAST_CONFIGURED", resultNode, XPathConstants.NODE))
					.getTextContent();
			if (lastConfiguredText != "") {
				lastConfigured = LocalDateTime.parse(lastConfiguredText, formatter);
			}

			String createdText = ((Node) xpath.evaluate("CREATED", resultNode, XPathConstants.NODE)).getTextContent();
			if (createdText != "") {
				created = LocalDateTime.parse(createdText, formatter);
			}

			visibility = Visibility.getVisibility(Integer
					.parseInt(((Node) xpath.evaluate("VISIBILITY", resultNode, XPathConstants.NODE)).getTextContent()));
			userId = ((Node) xpath.evaluate("USER_ID", resultNode, XPathConstants.NODE)).getTextContent();
			organizationId = ((Node) xpath.evaluate("ORGANIZATION_ID", resultNode, XPathConstants.NODE))
					.getTextContent();

			String parentDatabaseIdText = ((Node) xpath.evaluate("PARENT_DATABASE_ID", resultNode, XPathConstants.NODE))
					.getTextContent();
			if (false == parentDatabaseIdText.isEmpty()) {
				parentDatabaseId = Long.parseLong(parentDatabaseIdText);
			}

			optInFormDefined = Boolean.parseBoolean(
					((Node) xpath.evaluate("OPT_IN_FORM_DEFINED", resultNode, XPathConstants.NODE)).getTextContent());
			optOutFormDefined = Boolean.parseBoolean(
					((Node) xpath.evaluate("OPT_OUT_FORM_DEFINED", resultNode, XPathConstants.NODE)).getTextContent());
			profileFormDefined = Boolean.parseBoolean(
					((Node) xpath.evaluate("PROFILE_FORM_DEFINED", resultNode, XPathConstants.NODE)).getTextContent());
			optInAutoreplyDefined = Boolean
					.parseBoolean(((Node) xpath.evaluate("OPT_IN_AUTOREPLY_DEFINED", resultNode, XPathConstants.NODE))
							.getTextContent());
			profileAutoreplyDefined = Boolean
					.parseBoolean(((Node) xpath.evaluate("PROFILE_AUTOREPLY_DEFINED", resultNode, XPathConstants.NODE))
							.getTextContent());

			NodeList columnsNode = (NodeList) xpath.evaluate("COLUMNS/COLUMN", resultNode, XPathConstants.NODESET);
			Node columnNode;

			for (int i = 0; i < columnsNode.getLength(); i++) {
				ListColumnLimited listColumnLimited = new ListColumnLimited();
				columnNode = columnsNode.item(i);
				listColumnLimited
						.setName(((Node) xpath.evaluate("NAME", columnNode, XPathConstants.NODE)).getTextContent());

				Node columnTypeNode = (Node) xpath.evaluate("TYPE", columnNode, XPathConstants.NODE);
				if (columnTypeNode != null) {
					listColumnLimited.setType(
							ListColumnType.getListColumnType(Integer.parseInt(columnTypeNode.getTextContent())));
				}

				Node columnDefaultNode = (Node) xpath.evaluate("DEFAULT_VALUE", columnNode, XPathConstants.NODE);
				if (columnDefaultNode != null) {
					listColumnLimited.setDefaultValue(columnDefaultNode.getTextContent());
				}

				Node selectionValuesNode = (Node) xpath.evaluate("SELECTION_VALUES", columnNode, XPathConstants.NODE);
				if (selectionValuesNode != null) {
					List<String> selectionValues = new ArrayList<String>();
					NodeList selectionsNode = (NodeList) xpath.evaluate("SELECTION_VALUES/VALUE", columnNode,
							XPathConstants.NODESET);
					Node selectionNode;
					for (int j = 0; j < selectionsNode.getLength(); j++) {
						selectionNode = selectionsNode.item(j);
						selectionValues.add(selectionNode.getTextContent());
					}

					listColumnLimited.setSelectionValues(selectionValues);
				}

				columns.add(listColumnLimited);
			}

			NodeList keyColumnsNode = (NodeList) xpath.evaluate("KEY_COLUMNS/COLUMN", resultNode,
					XPathConstants.NODESET);
			Node keyColumnNode;

			for (int i = 0; i < keyColumnsNode.getLength(); i++) {
				keyColumnNode = keyColumnsNode.item(i);
				keyColumns.add(keyColumnNode.getTextContent());
			}

		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		getListMetaDataResponse.setId(id);
		getListMetaDataResponse.setName(name);
		getListMetaDataResponse.setType(type);
		getListMetaDataResponse.setSize(size);
		getListMetaDataResponse.setNumOptOuts(numOptOuts);
		getListMetaDataResponse.setNumUndeliverable(numUndeliverables);
		getListMetaDataResponse.setLastModified(lastModified);
		getListMetaDataResponse.setLastConfigured(lastConfigured);
		getListMetaDataResponse.setCreated(created);
		getListMetaDataResponse.setVisibility(visibility);
		getListMetaDataResponse.setUserId(userId);
		getListMetaDataResponse.setOrganizationId(organizationId);
		getListMetaDataResponse.setParentDatabaseId(parentDatabaseId);
		getListMetaDataResponse.setOptInFormDefined(optInFormDefined);
		getListMetaDataResponse.setOptOutFormDefined(optOutFormDefined);
		getListMetaDataResponse.setProfileFormDefined(profileFormDefined);
		getListMetaDataResponse.setOptInAutoreplyDefined(optInAutoreplyDefined);
		getListMetaDataResponse.setProfileAutoreplyDefined(profileAutoreplyDefined);
		getListMetaDataResponse.setColumns(columns);
		getListMetaDataResponse.setKeyColumns(keyColumns);

		ResponseContainer<GetListMetaDataResponse> response = new ResponseContainer<GetListMetaDataResponse>(
				getListMetaDataResponse);

		return response;
	}
}
