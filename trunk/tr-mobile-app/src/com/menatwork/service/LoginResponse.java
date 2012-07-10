package com.menatwork.service;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginResponse extends Response {

	public LoginResponse(JSONObject response) {
		super(response);
	}

	@Override
	public boolean isSuccessful() {
		try {
			return !"error".equals(getResponse().getJSONObject("result")
					.getString("status"));
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}
}
