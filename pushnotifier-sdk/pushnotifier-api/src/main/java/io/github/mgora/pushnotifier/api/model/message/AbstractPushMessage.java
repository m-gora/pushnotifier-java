package io.github.mgora.pushnotifier.api.model.message;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class AbstractPushMessage implements PushMessage {

	private List<String> devices;
	private boolean silent;
	
	public abstract String getRequestUrl();
	
	@Override
	public void setDevices(List<String> devices) {
		this.devices = devices;
	}
	
	@Override
	public void setDevices(String... devices) {
		this.devices = Arrays.asList(devices);
	}
	
	public Collection<String> getDevices() {
		return devices;
	}
	
	public void setSilent(boolean silent) {
		this.silent = silent;
	}
	
	public boolean isSilent() {
		return silent;
	}
	
}
