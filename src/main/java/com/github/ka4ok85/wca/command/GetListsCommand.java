package com.github.ka4ok85.wca.command;

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

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.response.GetListsResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.EngageList;

/**
 * <strong>Class for interacting with WCA GetLists API.</strong> It builds XML
 * request for GetLists API using
 * {@link com.github.ka4ok85.wca.options.GetListsOptions} and reads response
 * into {@link com.github.ka4ok85.wca.response.GetListsResponse}.
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
public class GetListsCommand extends AbstractInstantCommand<GetListsResponse, GetListsOptions> {

	private static final String apiMethodName = "GetLists";

	@Autowired
	private GetListsResponse getListsResponse;

	/**
	 * Builds XML request for GetLists API using
	 * {@link com.github.ka4ok85.wca.options.GetListsOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 * @return void
	 */
	@Override
	public void buildXmlRequest(GetListsOptions options) {
		Objects.requireNonNull(options, "GetListsOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element visibility = doc.createElement("VISIBILITY");
		visibility.setTextContent(options.getVisibility().value().toString());
		addChildNode(visibility, currentNode);

		Element listType = doc.createElement("LIST_TYPE");
		listType.setTextContent(options.getListType().value().toString());
		addChildNode(listType, currentNode);

		if (options.isIncludeAllLists() == true) {
			addBooleanParameter(methodElement, "INCLUDE_ALL_LISTS", options.isIncludeAllLists());
		} else if (options.getFolderId() != null) {
			Element folderId = doc.createElement("FOLDER_ID");
			folderId.setTextContent(options.getFolderId().toString());
			addChildNode(folderId, currentNode);
		}

		if (options.isIncludeTags() == true) {
			addBooleanParameter(methodElement, "INCLUDE_TAGS", options.isIncludeTags());
		}
	}

	/**
	 * Reads GetLists API response into
	 * {@link com.github.ka4ok85.wca.response.GetListsResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO GetLists Response
	 */
	@Override
	public ResponseContainer<GetListsResponse> readResponse(Node resultNode, GetListsOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		List<EngageList> lists = new ArrayList<EngageList>();
		try {
			NodeList listsNode = (NodeList) xpath.evaluate("LIST", resultNode, XPathConstants.NODESET);
			Node listNode;

			for (int i = 0; i < listsNode.getLength(); i++) {
				EngageList engageList = new EngageList();
				listNode = listsNode.item(i);
				engageList.setId(
						Long.parseLong(((Node) xpath.evaluate("ID", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setName(((Node) xpath.evaluate("NAME", listNode, XPathConstants.NODE)).getTextContent());
				engageList.setType(Integer
						.parseInt(((Node) xpath.evaluate("TYPE", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setSize(Long
						.parseLong(((Node) xpath.evaluate("SIZE", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setNumberOptOuts(Long.parseLong(
						((Node) xpath.evaluate("NUM_OPT_OUTS", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setNumberUndeliverables(Long.parseLong(
						((Node) xpath.evaluate("NUM_UNDELIVERABLE", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setLastModifiedDate(
						((Node) xpath.evaluate("LAST_MODIFIED", listNode, XPathConstants.NODE)).getTextContent());
				engageList.setVisibility(Integer.parseInt(
						((Node) xpath.evaluate("VISIBILITY", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setParentName(
						((Node) xpath.evaluate("PARENT_NAME", listNode, XPathConstants.NODE)).getTextContent());
				engageList
						.setUserId(((Node) xpath.evaluate("USER_ID", listNode, XPathConstants.NODE)).getTextContent());
				engageList.setFolderId(Long.parseLong(
						((Node) xpath.evaluate("PARENT_FOLDER_ID", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setIsFolder(Boolean.parseBoolean(
						((Node) xpath.evaluate("IS_FOLDER", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setFlaggedForBackup(Boolean.parseBoolean(
						((Node) xpath.evaluate("FLAGGED_FOR_BACKUP", listNode, XPathConstants.NODE)).getTextContent()));
				engageList.setSuppressionList(
						Long.parseLong(((Node) xpath.evaluate("SUPPRESSION_LIST_ID", listNode, XPathConstants.NODE))
								.getTextContent()));
				engageList.setIsDatabaseTemplate(Boolean
						.parseBoolean(((Node) xpath.evaluate("IS_DATABASE_TEMPLATE", listNode, XPathConstants.NODE))
								.getTextContent()));

				NodeList tagsNode = (NodeList) xpath.evaluate("TAGS/TAG", listNode, XPathConstants.NODESET);
				List<String> tagsList = new ArrayList<String>();
				for (int j = 0; j < tagsNode.getLength(); j++) {
					tagsList.add(tagsNode.item(j).getTextContent());
				}

				engageList.setTags(tagsList);
				lists.add(engageList);
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		getListsResponse.setLists(lists);
		ResponseContainer<GetListsResponse> response = new ResponseContainer<GetListsResponse>(getListsResponse);

		return response;
	}
}
