package com.github.ka4ok85.wca.command;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

public class AbstractCommand<T extends AbstractResponse> {
	protected ResponseContainer<T> response = new ResponseContainer<T>();
	protected OAuthClient oAuthClient;

	public ResponseContainer<T> executeCommand(AbstractOptions options) {
		System.out.println("Running Command with options " + options.getClass());

		return response;
	}

	public void setoAuthClient(OAuthClient oAuthClient) {
		this.oAuthClient = oAuthClient;
	}
	
	
}