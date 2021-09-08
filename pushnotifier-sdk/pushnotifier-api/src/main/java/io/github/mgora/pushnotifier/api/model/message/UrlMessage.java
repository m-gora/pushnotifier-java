package io.github.mgora.pushnotifier.api.model.message;

import java.net.URL;

public class UrlMessage extends AbstractPushMessage {

	private static final String URL = "/notifications/url";
	
	private URL url;
	
	@Override
	public String getRequestUrl() {
		return URL;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}
	
	public URL getUrl() {
		return url;
	}
}
