package com.menatwork.service;

import org.json.JSONObject;

public abstract class Response {

	private final JSONObject response;
	
	public Response(JSONObject response) {
		this.response = response;
	}

	public abstract boolean isSuccessful();

	protected JSONObject getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return "Response [response=" + response + "]";
	}

}
