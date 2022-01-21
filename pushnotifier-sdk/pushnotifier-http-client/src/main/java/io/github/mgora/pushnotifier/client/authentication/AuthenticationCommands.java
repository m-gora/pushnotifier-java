package io.github.mgora.pushnotifier.client.authentication;

import io.github.mgora.pushnotifier.client.authentication.model.Login;
import io.github.mgora.pushnotifier.client.authentication.model.LoginResponse;
import reactor.core.publisher.Mono;

public interface AuthenticationCommands {

	Mono<LoginResponse> refresh();

	Mono<LoginResponse> login(Mono<Login> login);

}
