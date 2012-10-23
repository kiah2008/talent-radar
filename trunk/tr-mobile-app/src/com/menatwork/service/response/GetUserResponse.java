package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;
import com.menatwork.service.ResponseException;

public class GetUserResponse extends BaseResponse {

	public GetUserResponse(final JSONObject response) {
		super(response);
	}

	// show_in_searches / username / show_headline / show_skills / show_name
	public User getUser() {
		try {
			final JSONObject userJsonObject = getResponse()
					.getJSONObject("result").getJSONObject("user")
					.getJSONObject("User");

			User user = new JsonUserParser(userJsonObject).parse();
			return user;
		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

}
