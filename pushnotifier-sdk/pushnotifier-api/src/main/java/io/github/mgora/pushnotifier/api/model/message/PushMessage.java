package io.github.mgora.pushnotifier.api.model.message;

import java.util.List;

public interface PushMessage {

	void setDevices(String... devices);
	
	void setDevices(List<String> devices);
	
	String getRequestUrl();
	
}
