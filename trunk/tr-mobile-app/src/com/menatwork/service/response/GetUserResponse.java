package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;
import com.menatwork.service.ResponseException;

public class GetUserResponse extends BaseResponse {

	public GetUserResponse(JSONObject response) {
		super(response);
	}

	public User getUser() {
		UserBuilder userBuilder = UserBuilder.newInstance();
		try {
			JSONObject userJsonObject = getResponse().getJSONObject("result")
					.getJSONObject("user").getJSONObject("User");
			userBuilder.setId(userJsonObject.getString("id"));
			userBuilder.setUserName(userJsonObject.getString("name"));
			userBuilder.setUserSurname(userJsonObject.getString("surname"));
			userBuilder.setEmail(userJsonObject.getString("email"));
			userBuilder.setHeadline(userJsonObject.getString("headline"));
			// userBuilder.setExtract(userJsonObject.getString("extract"));
			return userBuilder.build();
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

}
