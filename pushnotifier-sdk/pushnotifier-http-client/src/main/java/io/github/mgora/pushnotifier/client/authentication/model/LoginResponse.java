package io.github.mgora.pushnotifier.client.authentication.model;

import java.net.URL;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;

public class LoginResponse {

	private String username;
	private URL avatar;
	
	@JsonAlias("app_token")
	private String appToken;
	
	@JsonAlias("expires_at")
	private OffsetDateTime expiresAt;
	
	public String getAppToken() {
		return appToken;
	}
	
	public URL getAvatar() {
		return avatar;
	}
	
	public OffsetDateTime getExpiresAt() {
		return expiresAt;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}
	
	public void setAvatar(URL avatar) {
		this.avatar = avatar;
	}
	
	public void setExpiresAt(OffsetDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
}
