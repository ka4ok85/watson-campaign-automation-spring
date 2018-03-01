package com.github.ka4ok85.wca.command;

import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

public class AbstractCommand<T extends AbstractResponse> {
	protected ResponseContainer<T> response = new ResponseContainer<T>();

	public ResponseContainer<T> executeCommand(AbstractOptions options) {
		System.out.println("Running Command with options " + options.getClass());

		return response;
	}
}