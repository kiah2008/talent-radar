package com.menatwork.service;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseResponse implements Response {

	private final JSONObject response;

	public BaseResponse(JSONObject response) {
		this.response = response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.menatwork.service.Response#isSuccessful()
	 */
	@Override
	public boolean isSuccessful() {
		try {
			return "ok".equals(getResponse().getJSONObject("result").getString(
					"status"));
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

	protected JSONObject getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return "Response [response=" + response + "]";
	}

}
