package io.github.mgora.pushnotifier.api.model.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ImageMessage extends AbstractPushMessage {

	private static final String URL = "/notifications/image";
	
	private byte[] content;
	private String filename;
	
	@Override
	@JsonIgnore
	public String getRequestUrl() {
		return URL;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
