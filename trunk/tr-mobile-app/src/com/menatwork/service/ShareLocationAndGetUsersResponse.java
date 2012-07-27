package com.menatwork.service;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;

public class ShareLocationAndGetUsersResponse extends BaseResponse {

	public ShareLocationAndGetUsersResponse(final JSONObject response) {
		super(response);
	}

	public Collection<? extends User> getUsers() {
		final UserBuilder userBuilder = UserBuilder.newInstance();
		try {
			// TODO - this won't work - miguel - 27/07/2012
			final JSONObject userJsonObject = getResponse()
					.getJSONObject("result").getJSONObject("user")
					.getJSONObject("User");
			Log.i("users obtained from sharing location", getResponse()
					.toString());

			userBuilder.setId(userJsonObject.getString("id"));
			userBuilder.setUserName(userJsonObject.getString("name"));
			userBuilder.setUserSurname(userJsonObject.getString("surname"));
			userBuilder.setEmail(userJsonObject.getString("email"));
			userBuilder.setHeadline(userJsonObject.getString("headline"));
			// userBuilder.setExtract(userJsonObject.getString("extract"));

			// TODO - Ponele que por ahora es esto - miguel - 26/07/2012
			return Arrays.asList(userBuilder.build());
		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

}
