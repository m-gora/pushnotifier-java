package io.github.mgora.pushnotifier.client.http;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mgora.pushnotifier.AsyncPushnotifierClient;
import io.github.mgora.pushnotifier.PushnotifierClient;
import io.github.mgora.pushnotifier.ReactivePushnotifierClient;
import io.github.mgora.pushnotifier.api.model.message.MessageResponse;
import io.github.mgora.pushnotifier.api.model.message.PushMessage;
import io.github.mgora.pushnotifier.client.authentication.AuthenticationCommands;
import io.github.mgora.pushnotifier.client.authentication.PushnotifierAuthenticationManager;
import io.github.mgora.pushnotifier.client.authentication.model.Login;
import io.github.mgora.pushnotifier.client.authentication.model.LoginResponse;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClient;

public class PushnotifierHttpClient implements PushnotifierClient, ReactivePushnotifierClient, AsyncPushnotifierClient, AuthenticationCommands {

	private static final Logger LOG = LoggerFactory.getLogger(PushnotifierHttpClient.class);
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	private PushnotifierAuthenticationManager auth;
	
	public PushnotifierHttpClient(final HttpClient httpClient, final ObjectMapper objectMapper, String username, String password) {
		this(httpClient, objectMapper);
		auth = new PushnotifierAuthenticationManager(this, username, password);
	}

	PushnotifierHttpClient(final HttpClient httpClient, final ObjectMapper objectMapper) {
		assert httpClient != null;
		assert objectMapper != null;

		this.httpClient = httpClient;
		this.objectMapper = objectMapper;
	}
	

	@Override
	public MessageResponse send(PushMessage message) {
		return this.send(Mono.just(message)).block();
	}
	
	@Override
	public Mono<MessageResponse> send(Mono<PushMessage> message) {
		// @formatter:off
		return buildWithAuth()
				.put()
				.uri(message.map(PushMessage::getRequestUrl))
				.send(ByteBufMono.fromString(message.map(this::writeJson)))
				.responseSingle((response, content) -> content.asByteArray()
					.map(bytes -> this.readJson(bytes, MessageResponse.class)));
		// @formatter:on
	}
	
	@Override
	public CompletableFuture<MessageResponse> send(CompletableFuture<PushMessage> message) {
		return send(Mono.fromFuture(message)).toFuture();
	}
	
	@Override
	public Mono<LoginResponse> login(Mono<Login> login) {
		// @formatter:off
		return this.httpClient
				.post()
				.uri(Mono.just("/user/login"))
				.send(ByteBufMono.fromString(login.map(this::writeJson)))
				.responseSingle((response, content) -> content.asByteArray()
					.map(bytes -> this.readJson(bytes, LoginResponse.class)));
		// @formatter:on
	}
	
    @Override
	public Mono<LoginResponse> refresh() {
		return buildWithAuth()
				.get()
				.uri(Mono.just("/user/refresh"))
				.responseSingle((response, content) -> content.asByteArray()
					.map(bytes -> this.readJson(bytes, LoginResponse.class)));
	}
	
	private HttpClient buildWithAuth() {
		return this.httpClient
				.headers(header -> header.add("Authorization", "Bearer " + auth.getAccessToken()));
	}
	
	private String writeJson(Object object) {
		try {
			return this.objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	private <T> T readJson(byte[] bytes, Class<T> clazz) {
		try {
			return this.objectMapper.readValue(bytes, clazz);
		} catch (IOException e) {
			return null;
		}
	}

}
