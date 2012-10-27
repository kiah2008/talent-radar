package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;

public class JsonUserParser {
	private final JSONObject userJsonObject;

	public JsonUserParser(final JSONObject userJsonObject) {
		this.userJsonObject = userJsonObject;
	}

	public User parse() throws JSONException {
		final UserBuilder userBuilder = UserBuilder.newInstance();
		// get privacy options
		final String showInSearches = userJsonObject
				.getString("show_in_searches");
		final String showHeadline = userJsonObject.getString("show_headline");
		final String showSkills = userJsonObject.getString("show_skills");
		final String showName = userJsonObject.getString("show_name");
		final String showPicture = userJsonObject.getString("show_picture");
		userBuilder.setStealty(showInSearches);
		userBuilder.setHeadlinePublic(showHeadline);
		userBuilder.setSkillsPublic(showSkills);
		userBuilder.setNamePublic(showName);
		userBuilder.setProfilePicturePublic(showPicture);

		// get always-present user data
		userBuilder.setId(userJsonObject.getString("id"));
		userBuilder.setNickname(userJsonObject.getString("username"));

		// get privacy-conditional user data
		if (responseHasRealName()) {
			userBuilder.setUserName(userJsonObject.getString("name"));
			userBuilder.setUserSurname(userJsonObject.getString("surname"));
		}
		if (responseHasHeadline())
			userBuilder.setHeadline(userJsonObject.getString("headline"));
		if (responseHasPicture())
			userBuilder.setProfilePictureUrl(userJsonObject
					.getString("picture"));
		else
			userBuilder.setProfilePictureUrl("non-parseable-url");

		// get non-existant user data :P
		// userBuilder.setEmail(userJsonObject.getString("email"));

		final User user = userBuilder.build();
		return user;
	}

	private boolean responseHasRealName() {
		return userJsonObject.has("name") && userJsonObject.has("surname");
	}

	private boolean responseHasPicture() {
		return userJsonObject.has("picture");
	}

	private boolean responseHasHeadline() {
		return userJsonObject.has("headline");
	}
}
