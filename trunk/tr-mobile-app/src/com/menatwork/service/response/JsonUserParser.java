package com.menatwork.service.response;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.DataObjectPrivacySettings;
import com.menatwork.model.UserBuilder;

public class JsonUserParser {
	private static final String NICKNAME_KEY = "username";
	private static final String ID_KEY = "id";
	private static final String SURNAME_KEY = "surname";
	private static final String NAME_KEY = "name";
	private static final String PICTURE_KEY = "picture";
	private static final String HEADLINE_KEY = "headline";

	private final JSONObject userJsonObject;

	public JsonUserParser(final JSONObject userJsonObject) {
		this.userJsonObject = userJsonObject;
	}

	/**
	 * This method returns the UserBuilder as a lazy way to chain new user
	 * aspects that are not contemplated in the main user data parsed here.
	 * 
	 * @return UserBuilder
	 * @throws JSONException
	 */
	public UserBuilder parse() throws JSONException {
		final UserBuilder userBuilder = UserBuilder.newInstance();
		parsePrivacyOptions(userBuilder);
		parseUserData(userBuilder);
		inferRealPrivacy(userBuilder);
		return userBuilder;
	}

	private void inferRealPrivacy(final UserBuilder userBuilder) {
		final DataObjectPrivacySettings privacySettings = new DataObjectPrivacySettings();
		privacySettings.setHeadlinePublic(responseHasHeadline());
		privacySettings.setNamePublic(responseHasRealName());
		privacySettings.setPicturePublic(responseHasPicture());
		
		userBuilder.setRealPrivacySettings(privacySettings);
	}

	private void parseUserData(final UserBuilder userBuilder)
			throws JSONException {
		// get always-present user data
		userBuilder.setId(userJsonObject.getString(ID_KEY));
		userBuilder.setNickname(userJsonObject.getString(NICKNAME_KEY));

		// get privacy-conditional user data
		if (responseHasRealName()) {
			userBuilder.setUserName(userJsonObject.getString(NAME_KEY));
			userBuilder.setUserSurname(userJsonObject.getString(SURNAME_KEY));
		}

		if (responseHasHeadline())
			userBuilder.setHeadline(userJsonObject.getString(HEADLINE_KEY));

		if (responseHasPicture())
			userBuilder.setProfilePictureUrl(userJsonObject
					.getString(PICTURE_KEY));
		else
			userBuilder.setProfilePictureUrl("non-parseable-url");

	}

	private void parsePrivacyOptions(final UserBuilder userBuilder)
			throws JSONException {
		final String showInSearches = userJsonObject
				.getString("show_in_searches");
		final String showHeadline = userJsonObject.getString("show_headline");
		final String showSkills = userJsonObject.getString("show_skills");
		final String showName = userJsonObject.getString("show_name");
		final String showPicture = userJsonObject.getString("show_picture");
		final String showJobPositions = userJsonObject.getString("show_jobs");

		userBuilder.setStealthy(showInSearches);
		userBuilder.setHeadlinePublic(showHeadline);
		userBuilder.setSkillsPublic(showSkills);
		userBuilder.setNamePublic(showName);
		userBuilder.setProfilePicturePublic(showPicture);
		userBuilder.setJobPositionsPublic(showJobPositions);
	}

	// ************************************************ //
	// ====== Property existence predicates ======
	// ************************************************ //

	private boolean responseHasRealName() {
		return userJsonObject.has(NAME_KEY) && userJsonObject.has(SURNAME_KEY);
	}

	private boolean responseHasPicture() {
		return userJsonObject.has(PICTURE_KEY);
	}

	private boolean responseHasHeadline() {
		return userJsonObject.has(HEADLINE_KEY);
	}
}
