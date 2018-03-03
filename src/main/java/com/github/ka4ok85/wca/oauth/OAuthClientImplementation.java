package com.github.ka4ok85.wca.oauth;

import java.time.LocalDateTime;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

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
System.out.println(" call refreshAccessToken");
			refreshAccessToken();
		}

		return accessToken;
	}

	private void refreshAccessToken() throws FailedGetAccessTokenException {
		System.out.println("running refreshAccessToken");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("grant_type", grantType);
		map.add("client_id", clientId);
		map.add("client_secret", clientSecret);
		map.add("refresh_token", refreshToken);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.postForEntity(accessUrl, request , String.class );
			
	        System.out.println(result.getStatusCodeValue());
	        System.out.println(result.getBody());
	        
	        AccessTokenResponse accessTokenResponse = new AccessTokenResponse(result.getStatusCodeValue(), result.getBody());
	        accessTokenResponse.getAccessToken();
	        accessTokenResponse.getAccessTokenExpirationTime();
		} catch (HttpStatusCodeException e) {
			throw new FailedGetAccessTokenException("Can not get Access Token");
		} catch (FailedGetAccessTokenException e) {
			throw new FailedGetAccessTokenException("Can not get Access Token");
		}


	}
}
