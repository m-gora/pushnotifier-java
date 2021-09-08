package io.github.mgora.pushnotifier.api.model.message;

import java.net.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NotificationMessage extends TextMessage {

	private static final String URL = "/notifications/notification";
	
	@Override
	@JsonIgnore
	public String getRequestUrl() {
		return URL;
	}
	
	private URL url;
	
	public void setUrl(URL url) {
		this.url = url;
	}
	
	public URL getUrl() {
		return url;
	}
	
}
