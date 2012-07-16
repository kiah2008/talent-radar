package com.menatwork.service;

import org.json.JSONObject;

import android.util.Log;

import com.menatwork.model.User;

public class GetUserResponse extends BaseResponse {

	public GetUserResponse(JSONObject response) {
		super(response);
	}

	public User getUser() {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("GetUserResponse.getUser()");
		Log.d("GetUserResponse", "getUser()");
		return null;
	}

}
