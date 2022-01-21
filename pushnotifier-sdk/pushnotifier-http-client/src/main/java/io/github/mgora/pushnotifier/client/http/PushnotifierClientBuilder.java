package io.github.mgora.pushnotifier.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mgora.pushnotifier.client.authentication.AuthenticationCommands;
import reactor.netty.http.client.HttpClient;

public class PushnotifierClientBuilder {
	
	private ObjectMapper objectMapper;
	private HttpClient httpClient;
	private String username;
	private String password;
	
	public PushnotifierClientBuilder() {
		this.objectMapper = new ObjectMapper();
		this.httpClient = HttpClient.create();
		this.httpClient.baseUrl("https://api.pushnotifier.de/v2");
	}
	
	public PushnotifierClientBuilder baseUrl(String baseUrl) {
		this.httpClient.baseUrl(baseUrl);
		return this;
	}
	
	public PushnotifierClientBuilder username(String username) {
		this.username = username;
		return this;
	}
	
	public PushnotifierClientBuilder password(String password) {
		this.password = password;
		return this;
	}

	public AuthenticationCommands build() {
		return new PushnotifierHttpClient(this.httpClient, this.objectMapper, this.username, this.password);
	}
	
}
