package com.alexios.sample.monet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageAssets {
	@JsonProperty
	private Thumbnail preview;

	@JsonProperty("small_thumb")
	private Thumbnail smallThumb;

	@JsonProperty("large_thumb")
	private Thumbnail largeThumb;

	public Thumbnail getPreview() {
		return preview;
	}

	public Thumbnail getSmallThumb() {
		return smallThumb;
	}

	public Thumbnail getLargeThumb() {
		return largeThumb;
	}
}
