package com.github.ka4ok85.wca.command;

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

import com.github.ka4ok85.wca.constants.GetFolderPathObjectSubType;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.GetFolderPathOptions;
import com.github.ka4ok85.wca.response.GetFolderPathResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

/**
 * <strong>Class for interacting with WCA GetFolderPath API.</strong> It builds
 * XML request for GetFolderPath API using
 * {@link com.github.ka4ok85.wca.options.GetFolderPathOptions} and reads
 * response into {@link com.github.ka4ok85.wca.response.GetFolderPathResponse}.
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
public class GetFolderPathCommand extends AbstractInstantCommand<GetFolderPathResponse, GetFolderPathOptions> {

	private static final String apiMethodName = "GetFolderPath";

	@Autowired
	private GetFolderPathResponse getFolderPathResponse;

	/**
	 * Builds XML request for GetFolderPath API using
	 * {@link com.github.ka4ok85.wca.options.GetFolderPathOptions}
	 * 
	 * @param options
	 *            - settings for API call
	 */
	@Override
	public void buildXmlRequest(GetFolderPathOptions options) {
		Objects.requireNonNull(options, "GetFolderPathOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element objectType = doc.createElement("OBJECT_TYPE");
		objectType.setTextContent(options.getObjectType().value());
		addChildNode(objectType, currentNode);

		if (options.getFolderId() != null) {
			Element folderId = doc.createElement("FOLDER_ID");
			folderId.setTextContent(options.getFolderId());
			addChildNode(folderId, currentNode);
		} else if (options.getObjectId() != null) {
			Element objectId = doc.createElement("OBJECT_ID");
			objectId.setTextContent(options.getObjectId().toString());
			addChildNode(objectId, currentNode);
		}
	}

	/**
	 * Reads GetFolderPath API response into
	 * {@link com.github.ka4ok85.wca.response.GetFolderPathResponse}
	 * 
	 * @param resultNode
	 *            - "RESULT" XML Node returned by API
	 * @param options
	 *            - settings for API call
	 * @return POJO GetFolderPath Response
	 */
	@Override
	public ResponseContainer<GetFolderPathResponse> readResponse(Node resultNode, GetFolderPathOptions options) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();

		String folderPath;
		GetFolderPathObjectSubType objectSubType;
		try {
			folderPath = ((Node) xpath.evaluate("FOLDER_PATH", resultNode, XPathConstants.NODE)).getTextContent();
			objectSubType = GetFolderPathObjectSubType.getObjectSubType(
					((Node) xpath.evaluate("OBJECT_SUB_TYPE", resultNode, XPathConstants.NODE)).getTextContent());
		} catch (XPathExpressionException e) {
			throw new EngageApiException(e.getMessage());
		}

		getFolderPathResponse.setFolderPath(folderPath);
		getFolderPathResponse.setObjectSubType(objectSubType);

		ResponseContainer<GetFolderPathResponse> response = new ResponseContainer<GetFolderPathResponse>(
				getFolderPathResponse);

		return response;
	}

}
