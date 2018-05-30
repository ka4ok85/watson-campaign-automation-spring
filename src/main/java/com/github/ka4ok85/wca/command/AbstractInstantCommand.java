package com.github.ka4ok85.wca.command;

import org.w3c.dom.Node;

import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

abstract public class AbstractInstantCommand<T extends AbstractResponse, V extends AbstractOptions>
		extends AbstractCommand<T, V> {

	public abstract ResponseContainer<T> readResponse(Node resultNode, V options);

	public ResponseContainer<T> executeCommand(V options) {
		buildXmlRequest(options);
		String xml = getXML();
		log.debug("XML Request is {}", xml);
		Node resultNode = runApi(xml);

		return readResponse(resultNode, options);
	}
}
