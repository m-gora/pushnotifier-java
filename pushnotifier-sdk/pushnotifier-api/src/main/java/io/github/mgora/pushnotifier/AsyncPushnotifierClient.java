package io.github.mgora.pushnotifier;

import java.util.concurrent.CompletableFuture;

import io.github.mgora.pushnotifier.api.model.message.MessageResponse;
import io.github.mgora.pushnotifier.api.model.message.PushMessage;

public interface AsyncPushnotifierClient {

	CompletableFuture<MessageResponse> send(CompletableFuture<PushMessage> message);

}
