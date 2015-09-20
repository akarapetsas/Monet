package com.alexios.sample.monet.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
	@JsonProperty
	private String id;

	@JsonProperty
	private String description;

	@JsonProperty
	private ImageAssets assets;

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	public ImageAssets getAssets() {
		return assets;
	}
}
