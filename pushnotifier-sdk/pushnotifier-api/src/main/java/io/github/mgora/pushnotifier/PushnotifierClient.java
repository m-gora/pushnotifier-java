package io.github.mgora.pushnotifier;

import io.github.mgora.pushnotifier.api.model.login.Login;
import io.github.mgora.pushnotifier.api.model.login.LoginResponse;
import io.github.mgora.pushnotifier.api.model.message.MessageResponse;
import io.github.mgora.pushnotifier.api.model.message.PushMessage;

public interface PushnotifierClient {

	LoginResponse login(Login login);

	MessageResponse send(PushMessage message);

}
