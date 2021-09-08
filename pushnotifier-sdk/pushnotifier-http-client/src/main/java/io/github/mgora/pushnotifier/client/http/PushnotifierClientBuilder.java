package io.github.mgora.pushnotifier.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.netty.http.client.HttpClient;

public class PushnotifierClientBuilder {
	
	private ObjectMapper objectMapper;
	private HttpClient httpClient;
	
	public PushnotifierClientBuilder() {
		this.objectMapper = new ObjectMapper();
		this.httpClient = HttpClient.create();
		this.httpClient.baseUrl("https://api.pushnotifier.de/v2");
	}
	
	public PushnotifierClientBuilder baseUrl(String baseUrl) {
		this.httpClient.baseUrl(baseUrl);
		return this;
	}

	public PushnotifierHttpClient build() {
		return new PushnotifierHttpClient(this.httpClient, this.objectMapper);
	}
	
}
