package io.github.mgora.pushnotifier.client.http;

import java.time.OffsetDateTime;

public class PushnotifierAccessToken {

	private String token;
	private OffsetDateTime expiryTime;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public OffsetDateTime getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(OffsetDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}
	
}
