package com.github.ka4ok85.wca.oauth;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.github.ka4ok85.wca.Engage;
import com.github.ka4ok85.wca.exceptions.FailedGetAccessTokenException;
import com.github.ka4ok85.wca.pod.Pod;
import com.github.ka4ok85.wca.response.AccessTokenResponse;

public class OAuthClientImplementation implements OAuthClient {

	final String grantType = "refresh_token";

	private int podNumber;
	private String clientId;
	private String clientSecret;
	private String refreshToken;
	private String accessUrl;
	private String accessToken = "";
	private LocalDateTime accessTokenExpirationTime = LocalDateTime.MIN;

	private static final Logger log = LoggerFactory.getLogger(Engage.class);

	public OAuthClientImplementation(int podNumber, String clientId, String clientSecret, String refreshToken) {
		this.podNumber = podNumber;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
		this.accessUrl = Pod.getOAuthEndpoint(podNumber);
	}

	@Override
	public String getAccessToken() throws FailedGetAccessTokenException {
		if (LocalDateTime.now().compareTo(accessTokenExpirationTime) > 0) {
			log.info("Calling Refresh Access Token API. Current Access Token Expiration Time is {}", accessTokenExpirationTime);
			refreshAccessToken();
		}

		return accessToken;
	}

	private void refreshAccessToken() throws FailedGetAccessTokenException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", grantType);
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("refresh_token", refreshToken);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.postForEntity(accessUrl, request, String.class);

			log.debug("Refresh Access Token API Call Result: Status Code={}, Body={}", result.getStatusCodeValue(), result.getBody());

			AccessTokenResponse accessTokenResponse = new AccessTokenResponse(result.getStatusCodeValue(), result.getBody());
			accessToken = accessTokenResponse.getAccessToken();
			accessTokenExpirationTime = LocalDateTime.now().plusSeconds(Integer.parseInt(accessTokenResponse.getAccessTokenExpirationTime())); 
		} catch (HttpStatusCodeException | FailedGetAccessTokenException e) {
			log.error("Refresh Access Token API Call Error: {}" + e.getMessage());
			throw new FailedGetAccessTokenException("Can not get Access Token");
		}
	}

	public int getPodNumber() {
		return podNumber;
	}

}
