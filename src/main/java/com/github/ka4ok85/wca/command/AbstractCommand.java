package com.github.ka4ok85.wca.command;

import com.github.ka4ok85.wca.oauth.OAuthClient;
import com.github.ka4ok85.wca.options.AbstractOptions;
import com.github.ka4ok85.wca.response.AbstractResponse;
import com.github.ka4ok85.wca.response.ResponseContainer;

public abstract class AbstractCommand<T extends AbstractResponse, V extends AbstractOptions> {
	protected OAuthClient oAuthClient;

	public ResponseContainer<T> executeCommand(V options) {
		System.out.println("Running Command with options " + options.getClass());

		return new ResponseContainer<T>(null);
	}

	public void setoAuthClient(OAuthClient oAuthClient) {
		this.oAuthClient = oAuthClient;
	}

}