package com.github.ka4ok85.wca.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.options.GetListsOptions;
import com.github.ka4ok85.wca.response.ExportListResponse;
import com.github.ka4ok85.wca.response.GetListsResponse;
import com.github.ka4ok85.wca.response.JobResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.containers.EngageList;
import com.github.ka4ok85.wca.utils.DateTimeRange;

public class GetListsCommand extends AbstractCommand<GetListsResponse, GetListsOptions> {

	private static final String apiMethodName = "GetLists";
	private static final Logger log = LoggerFactory.getLogger(GetListsCommand.class);

	@Override
	public ResponseContainer<GetListsResponse> executeCommand(GetListsOptions options) throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
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
		
		addBooleanParameter(methodElement, "INCLUDE_TAGS", options.isIncludeTags());

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);
		
    	XPathFactory factory = XPathFactory.newInstance();
    	XPath xpath = factory.newXPath();
		List<EngageList> lists = new ArrayList<EngageList>();
		try {
			NodeList listsNode = (NodeList) xpath.evaluate("LIST", resultNode, XPathConstants.NODESET);
			Node column;
			
			for (int i = 0; i < listsNode.getLength(); i++) {
				EngageList engageList = new EngageList();
				column = listsNode.item(i);
				engageList.setId(Long.parseLong(((Node) xpath.evaluate("ID", column, XPathConstants.NODE)).getTextContent()));
				engageList.setName(((Node) xpath.evaluate("NAME", column, XPathConstants.NODE)).getTextContent());
				engageList.setType(Integer.parseInt(((Node) xpath.evaluate("TYPE", column, XPathConstants.NODE)).getTextContent()));
				engageList.setSize(Long.parseLong(((Node) xpath.evaluate("SIZE", column, XPathConstants.NODE)).getTextContent()));
				engageList.setNumberOptOuts(Long.parseLong(((Node) xpath.evaluate("NUM_OPT_OUTS", column, XPathConstants.NODE)).getTextContent()));
				engageList.setNumberUndeliverables(Long.parseLong(((Node) xpath.evaluate("NUM_UNDELIVERABLE", column, XPathConstants.NODE)).getTextContent()));
				


				lists.add(engageList);
			}

		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}
		
		GetListsResponse getListsResponse = new GetListsResponse();

		getListsResponse.setLists(lists);
		
		ResponseContainer<GetListsResponse> response = new ResponseContainer<GetListsResponse>(getListsResponse);

		return response;
	}
}
