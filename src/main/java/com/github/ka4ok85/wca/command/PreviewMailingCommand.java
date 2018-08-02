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

import com.github.ka4ok85.wca.exceptions.EngageApiException;
import com.github.ka4ok85.wca.options.PreviewMailingOptions;
import com.github.ka4ok85.wca.response.PreviewMailingResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

@Service
@Scope("prototype")
public class PreviewMailingCommand extends AbstractInstantCommand<PreviewMailingResponse, PreviewMailingOptions> {

	private static final String apiMethodName = "PreviewMailing";

	@Autowired
	private PreviewMailingResponse previewMailingResponse;

	@Override
	public void buildXmlRequest(PreviewMailingOptions options) {
		Objects.requireNonNull(options, "PreviewMailingOptions must not be null");


	}

	@Override
	public ResponseContainer<PreviewMailingResponse> readResponse(Node resultNode, PreviewMailingOptions options) {
		return null;
	}
}
