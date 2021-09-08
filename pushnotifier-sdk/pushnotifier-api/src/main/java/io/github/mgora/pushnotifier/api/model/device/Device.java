package io.github.mgora.pushnotifier.api.model.device;

import java.net.URL;

public class Device {

	private String id;
	private String title;
	private String model;
	private URL image;
	
	public String getId() {
		return id;
	}
	
	public URL getImage() {
		return image;
	}
	
	public String getModel() {
		return model;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setImage(URL image) {
		this.image = image;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
}
