package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.service.ResponseException;

public class GetPingResponse extends BaseResponse {

	public GetPingResponse(JSONObject response) {
		super(response);
	}

	public String getMessage() {
		try {
			return getResult().getJSONObject("UsersPing").getString("content");
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

	public String getUserId() {
		try {
			return getResult().getJSONObject("UsersPing").getString("id");
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

	public String getUserFullName() {
		try {
			String name = getResult().getJSONObject("UserFrom").getString(
					"name");
			String surname = getResult().getJSONObject("UserFrom").getString(
					"surname");
			return new StringBuilder(name).append(" ").append(surname)
					.toString();
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

}