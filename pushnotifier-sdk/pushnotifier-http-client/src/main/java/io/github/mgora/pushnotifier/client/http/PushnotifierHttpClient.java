package io.github.mgora.pushnotifier.client.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mgora.pushnotifier.PushnotifierClient;
import io.github.mgora.pushnotifier.ReactivePushnotifierClient;
import io.github.mgora.pushnotifier.api.model.login.Login;
import io.github.mgora.pushnotifier.api.model.login.LoginResponse;
import io.github.mgora.pushnotifier.api.model.message.MessageResponse;
import io.github.mgora.pushnotifier.api.model.message.PushMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClient;

public class PushnotifierHttpClient implements PushnotifierClient, ReactivePushnotifierClient {

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	private OffsetDateTime refreshTime;

	PushnotifierHttpClient(final HttpClient httpClient, final ObjectMapper objectMapper) {
		assert httpClient != null;
		assert objectMapper != null;

		this.httpClient = httpClient;
		this.objectMapper = objectMapper;
	}

	@Override
	public LoginResponse login(Login login) {
		return this.login(Mono.just(login)).block();
	}
	

	@Override
	public MessageResponse send(PushMessage message) {
		return this.send(Mono.just(message)).block();
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
	public Mono<MessageResponse> send(Mono<PushMessage> messages) {
		// @formatter:off
		return this.httpClient
				.put()
				.uri(messages.map(PushMessage::getRequestUrl))
				.send(ByteBufMono.fromString(messages.map(this::writeJson)))
				.responseSingle((response, content) -> content.asByteArray()
					.map(bytes -> this.readJson(bytes, MessageResponse.class)));
		// @formatter:on
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
