package com.alexios.sample.monet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImagesResponse {
    @JsonProperty
    public List<Image> data;

    public List<Image> getData() {
        return data;
    }
}
