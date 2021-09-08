package io.github.mgora.pushnotifier.api.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TextMessage extends AbstractPushMessage {

	private static final String URL = "/notifications/text";
	
	private String content;
	
	@Override
	@JsonIgnore
	public String getRequestUrl() {
		return URL;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
}
