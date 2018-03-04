package com.github.ka4ok85.wca.command;

import com.github.ka4ok85.wca.options.ExportListOptions;
import com.github.ka4ok85.wca.response.ResponseContainer;
import com.github.ka4ok85.wca.response.ExportListResponse;

public class ExportListCommand extends AbstractCommand<ExportListResponse, ExportListOptions> {

	@Override
	public ResponseContainer<ExportListResponse> executeCommand(ExportListOptions options) {
		System.out.println("Running ExportListCommand with options " + options.getClass());
		ExportListResponse exportListResponse = new ExportListResponse(0, "");
		ResponseContainer<ExportListResponse> response = new ResponseContainer<ExportListResponse>(exportListResponse);

		return response;
	}
}
