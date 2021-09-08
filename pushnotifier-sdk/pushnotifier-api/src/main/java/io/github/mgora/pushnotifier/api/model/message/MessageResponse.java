package io.github.mgora.pushnotifier.api.model.message;

import java.util.List;

public class MessageResponse {

	private List<String> success;
	private List<String> error;
	
	public List<String> getError() {
		return error;
	}
	
	public List<String> getSuccess() {
		return success;
	}
	
	public void setError(List<String> error) {
		this.error = error;
	}
	
	public void setSuccess(List<String> success) {
		this.success = success;
	}
	
}
