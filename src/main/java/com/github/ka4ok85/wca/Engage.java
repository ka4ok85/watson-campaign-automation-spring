package com.github.ka4ok85.wca;

import com.github.ka4ok85.wca.pod.Pod;

public class Engage {
	final String grantType = "refresh_token";

	private int podNumber;
	private String clientId;
	private String clientSecret;
	private String refreshToken;
	private String accessUrl;
	private String accessToken;

	public Engage(int podNumber, String clientId, String clientSecret, String refreshToken) {
		super();
		this.podNumber = podNumber;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
		this.accessUrl = Pod.getOAuthEndpoint(podNumber);
	}

}
