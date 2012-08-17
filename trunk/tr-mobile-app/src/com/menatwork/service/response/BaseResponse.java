package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.service.ResponseException;

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
			return "ok".equals(getResult().getString("status"));
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

	public JSONObject getResult() throws JSONException {
		return getResponse().getJSONObject("result");
	}

	protected JSONObject getResponse() {
		return response;
	}

	@Override
	public String toString() {
		return "Response [response=" + response + "]";
	}

	@Override
	public boolean isValid() {
		try {
			return "ok".equals(getResponse().getString("status"));
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

}
