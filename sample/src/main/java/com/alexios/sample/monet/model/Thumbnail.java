package com.alexios.sample.monet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumbnail {
	@JsonProperty
	private String url;

	public String getUrl() {
		return url;
	}
}
