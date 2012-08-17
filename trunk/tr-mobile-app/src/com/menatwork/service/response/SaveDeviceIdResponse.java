package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.service.ResponseException;

public class SaveDeviceIdResponse extends BaseResponse {

	public SaveDeviceIdResponse(JSONObject response) {
		super(response);
	}

	public String getDeviceId() {
		try {
			return getResult().getJSONObject("User").getString(
					"android_device_id");
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

	public String getUserId() {
		try {
			return getResult().getJSONObject("User").getString("id");
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

}