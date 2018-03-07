package com.github.ka4ok85.wca.command;

import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.utils.DateTimeRange;
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

		Element methodElement = doc.createElement(apiMethodName);
		currentNode = addChildNode(methodElement, null);

		Element listID = doc.createElement("LIST_ID");
		listID.setTextContent(options.getListId().toString());
		addChildNode(listID, currentNode);

		Element exportType = doc.createElement("EXPORT_TYPE");
		exportType.setTextContent(options.getExportType().name());
		addChildNode(exportType, currentNode);

		Element exportFormat = doc.createElement("EXPORT_FORMAT");
		exportFormat.setTextContent(options.getExportFormat().name());
		addChildNode(exportFormat, currentNode);

		Element fileEncoding = doc.createElement("FILE_ENCODING");
		fileEncoding.setTextContent(options.getFileEncoding().name());
		addChildNode(fileEncoding, currentNode);

		DateTimeRange lastModifiedRange = options.getLastModifiedRange();
		if (lastModifiedRange != null) {
			addParameter(currentNode, "DATE_START", lastModifiedRange.getFormattedStartDateTime());
			addParameter(currentNode, "DATE_END", lastModifiedRange.getFormattedEndDateTime());
		}

		// options.getExportColumns()

		String xml = getXML();
		System.out.println(xml);
		ExportListResponse exportListResponse = new ExportListResponse(0, "");
		ResponseContainer<ExportListResponse> response = new ResponseContainer<ExportListResponse>(exportListResponse);

		System.out.println("End ExportListCommand");

		return response;
	}

}
