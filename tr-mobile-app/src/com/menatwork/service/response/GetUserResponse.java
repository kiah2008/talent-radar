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

	public User getUser() {
		final UserBuilder userBuilder = UserBuilder.newInstance();
		try {
			final JSONObject userJsonObject = getResponse()
					.getJSONObject("result").getJSONObject("user")
					.getJSONObject("User");
			userBuilder.setId(userJsonObject.getString("id"));
			userBuilder.setUserName(userJsonObject.getString("name"));
			userBuilder.setUserSurname(userJsonObject.getString("surname"));
			userBuilder.setEmail(userJsonObject.getString("email"));
			userBuilder.setHeadline(userJsonObject.getString("headline"));

			try{
			// TODO - throws an error for non LI users (picture is not included
			// in the JSON) - miguel - 19/09/2012
			userBuilder.setProfilePictureUrl(userJsonObject
					.getString("picture"));
			} catch (final JSONException e) {
				// as of today (19/09/2012) this doesn't concern us
			}

			// userBuilder.setExtract(userJsonObject.getString("extract"));
			return userBuilder.build();
		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

}
