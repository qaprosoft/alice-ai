package com.qaprosoft.module.controllers.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class URLResult {

	private String input_image;
	private String output_image;
	private String metadata;


	public String getInput_image() {
		return input_image;
	}

	public void setInput_image(String input_image) {
		this.input_image = input_image;
	}

	public String getOutput_image() {
		return output_image;
	}

	public void setOutput_image(String output_image) {
		this.output_image = output_image;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
}
