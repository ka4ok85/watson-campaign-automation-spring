package com.github.ka4ok85.wca.response;

public class ResponseContainer<T> {
	private T response;

	public ResponseContainer(T response) {
		this.response = response;
	}

	public T getResposne() {
		return response;
	}

	public void setResposne(T resposne) {
		this.response = resposne;
	}
}
