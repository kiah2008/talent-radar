package com.menatwork.service.response;

import org.json.JSONObject;

import com.menatwork.model.User;

public class LoginResponse extends BaseResponse {

	private GetUserResponse getUserResponse;

	public LoginResponse(JSONObject response) {
		// this response delegates its behavior into the getUserResponse since
		// both service calls get the same answer, just leaving the different
		// responses for clarity's sake
		super(response);
		this.getUserResponse = new GetUserResponse(response);
	}

	public User getUser() {
		return getUserResponse.getUser();
	}

}
