package io.github.mgora.pushnotifier;

import io.github.mgora.pushnotifier.api.model.message.MessageResponse;
import io.github.mgora.pushnotifier.api.model.message.PushMessage;
import reactor.core.publisher.Mono;

public interface ReactivePushnotifierClient {

	Mono<MessageResponse> send(Mono<PushMessage> messages);
	
}
