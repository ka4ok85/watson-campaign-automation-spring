package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.ExportListResponse;

public class ExportListCommand extends AbstractCommand<ExportListResponse, ExportListOptions> {

	public ExportListCommand(OAuthClient oAuthClient) {
		super(oAuthClient);
	}

	private static String apiMethodName = "ExportList";

	@Override
	public ResponseContainer<ExportListResponse> executeCommand(ExportListOptions options) {
		System.out.println("Running ExportListCommand with options " + options.getClass());

		Objects.requireNonNull(options, "ExportListOptions must not be null");

		Node bodyElement = doc.getElementsByTagName("Body").item(0);
		Element methodElement = doc.createElement(apiMethodName);
		bodyElement.appendChild(methodElement);

		String xml = getXML();
		System.out.println(xml);
		ExportListResponse exportListResponse = new ExportListResponse(0, "");
		ResponseContainer<ExportListResponse> response = new ResponseContainer<ExportListResponse>(exportListResponse);

		System.out.println("End ExportListCommand");

		return response;
	}

}
