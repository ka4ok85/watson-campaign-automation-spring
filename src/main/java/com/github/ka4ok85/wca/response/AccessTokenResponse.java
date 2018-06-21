package com.github.ka4ok85.wca.response;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;

public class AccessTokenResponse extends AbstractResponse {

	private String accessToken;
	private String accessTokenExpirationTime;

	public AccessTokenResponse(String rawOutput) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
		};

		HashMap<String, String> map;
		try {
			map = mapper.readValue(rawOutput, typeRef);
			if (false == map.containsKey("access_token")) {
				throw new Exception("No 'access_token' field was returned during Get Access Token Call");
			}
			if (false == map.containsKey("expires_in")) {
				throw new Exception("No 'expires_in' field was returned during Get Access Token Call");
			}

			accessToken = map.get("access_token");
			accessTokenExpirationTime = map.get("expires_in");
		} catch (IOException e) {
			throw new FailedGetAccessTokenException(e.getMessage());
		} catch (Exception e) {
			throw new FailedGetAccessTokenException(e.getMessage());
		}
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessTokenExpirationTime() {
		return accessTokenExpirationTime;
	}

}
