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
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.exceptions.BadApiResultException;
import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.exceptions.FaultApiResultException;
import com.github.ka4ok85.wca.exceptions.JobBadStateException;
import com.github.ka4ok85.wca.options.ExportMailingTemplateOptions;
import com.github.ka4ok85.wca.response.ExportMailingTemplateResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class ExportMailingTemplateCommand
		extends AbstractCommand<ExportMailingTemplateResponse, ExportMailingTemplateOptions> {

	private static final String apiMethodName = "ExportMailingTemplate";
	private static final Logger log = LoggerFactory.getLogger(ExportMailingTemplateCommand.class);

	@Autowired
	private ExportMailingTemplateResponse exportMailingTemplateResponse;

	@Override
	public ResponseContainer<ExportMailingTemplateResponse> executeCommand(ExportMailingTemplateOptions options)
			throws FailedGetAccessTokenException, FaultApiResultException, BadApiResultException {
		Objects.requireNonNull(options, "ExportMailingTemplateOptions must not be null");

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element templateID = doc.createElement("TEMPLATE_ID");
		templateID.setTextContent(options.getTemplateId().toString());
		addChildNode(templateID, currentNode);

		addBooleanParameter(methodElement, "ADD_TO_STORED_FILES", options.isAddToStoredFiles());

		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		String filePath;
		try {
			Node filePathNode = (Node) xpath.evaluate("FILE_PATH", resultNode, XPathConstants.NODE);
			filePath = filePathNode.getTextContent();

			log.debug("Generated Export File {} on SFTP", filePath);
			if (options.getLocalAbsoluteFilePath() != null) {
				sftp.download(filePath, options.getLocalAbsoluteFilePath());
			}
		} catch (XPathExpressionException | JobBadStateException e) {
			throw new EngageApiException(e.getMessage());
		}

		exportMailingTemplateResponse.setRemoteFileName(filePath);
		ResponseContainer<ExportMailingTemplateResponse> response = new ResponseContainer<ExportMailingTemplateResponse>(
				exportMailingTemplateResponse);

		return response;
	}
}
