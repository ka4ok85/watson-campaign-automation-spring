package com.github.ka4ok85.wca.response;

public class AbstractResponse {
	private int responseCode;
	private String output;

	public AbstractResponse(int responseCode, String output) {
		this.responseCode = responseCode;
		this.output = output;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getOutput() {
		return output;
	}

	@Override
	public String toString() {
		return "AbstractResponse [responseCode=" + responseCode + ", output=" + output + "]";
	}

}
