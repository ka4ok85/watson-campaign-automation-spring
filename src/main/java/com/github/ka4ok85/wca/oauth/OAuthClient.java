package com.github.ka4ok85.wca.oauth;

import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;

public interface OAuthClient {
	public String getAccessToken() throws FailedGetAccessTokenException;
}
