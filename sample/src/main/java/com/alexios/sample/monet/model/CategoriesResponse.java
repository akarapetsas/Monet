package com.alexios.sample.monet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriesResponse {
	@JsonProperty
	public List<Categories> data;

	public List<Categories> getData() {
		return data;
	}
}
