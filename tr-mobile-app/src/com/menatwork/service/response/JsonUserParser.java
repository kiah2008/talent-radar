package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;

public class JsonUserParser {
	private JSONObject userJsonObject;

	public JsonUserParser(JSONObject userJsonObject) {
		this.userJsonObject = userJsonObject;
	}

	public User parse() throws JSONException {
		final UserBuilder userBuilder = UserBuilder.newInstance();
		// get privacy options
		String showInSearches = userJsonObject.getString("show_in_searches");
		String showHeadline = userJsonObject.getString("show_headline");
		String showSkills = userJsonObject.getString("show_skills");
		String showName = userJsonObject.getString("show_name");
		String showPicture = userJsonObject.getString("show_picture");
		userBuilder.setStealty(showInSearches);
		userBuilder.setHeadlinePublic(showHeadline);
		userBuilder.setSkillsPublic(showSkills);
		userBuilder.setNamePublic(showName);
		userBuilder.setProfilePicturePublic(showPicture);

		// get always-present user data
		userBuilder.setId(userJsonObject.getString("id"));
		userBuilder.setNickname(userJsonObject.getString("username"));

		// get privacy-conditional user data
		if ("true".equals(showName)) {
			userBuilder.setUserName(userJsonObject.getString("name"));
			userBuilder.setUserSurname(userJsonObject.getString("surname"));
		}
		if ("true".equals(showHeadline))
			userBuilder.setHeadline(userJsonObject.getString("headline"));
		if ("true".equals(showPicture))
			userBuilder.setProfilePictureUrl(userJsonObject
					.getString("picture"));
		else
			userBuilder.setProfilePictureUrl("non-parseable-url");

		// get non-existant user data :P
		userBuilder.setEmail(userJsonObject.getString("email"));

		User user = userBuilder.build();
		return user;
	}
}
