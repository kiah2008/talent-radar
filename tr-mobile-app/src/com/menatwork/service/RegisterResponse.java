package com.menatwork.service;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterResponse extends Response {

	public RegisterResponse(JSONObject response) {
		super(response);
	}

	@Override
	public boolean isSuccessful() {
		String resultStatus;
		try {
			resultStatus = getResponse().getJSONObject("result").getString(
					"status");
			return "ok".equals(resultStatus);
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

}
