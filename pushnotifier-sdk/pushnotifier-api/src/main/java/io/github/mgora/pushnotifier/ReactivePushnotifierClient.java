package io.github.mgora.pushnotifier;

import io.github.mgora.pushnotifier.api.model.login.Login;
import io.github.mgora.pushnotifier.api.model.login.LoginResponse;
import io.github.mgora.pushnotifier.api.model.message.MessageResponse;
import io.github.mgora.pushnotifier.api.model.message.PushMessage;
import reactor.core.publisher.Mono;

public interface ReactivePushnotifierClient {

	Mono<LoginResponse> login(Mono<Login> login);
	
	Mono<MessageResponse> send(Mono<PushMessage> messages);
	
}
