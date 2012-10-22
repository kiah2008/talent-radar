package com.menatwork.model;

public class UserBuilder {

	private String id;
	private String name;
	private String surname;
	private String email;
	private String headline;
	private String profilePictureUrl;
	private String nickname;
	private boolean stealthy;
	private boolean headlinePublic;
	private boolean skillsPublic;
	private boolean namePublic;

	public User build() {
		final BaseUser user = new BaseUser();
		user.setId(id);
		user.setName(name);
		user.setSurname(surname);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setHeadline(headline);
		user.setProfilePictureUrl(profilePictureUrl);

		final DataObjectPrivacySettings privacySettings = new DataObjectPrivacySettings();
		privacySettings.setHeadlinePublic(headlinePublic);
		privacySettings.setNamePublic(namePublic);
		privacySettings.setNickname(nickname);
		privacySettings.setSkillsPublic(skillsPublic);
		privacySettings.setStealthy(stealthy);

		user.setPrivacySettings(privacySettings);

		return user;
	}

	/* ***************************************** */
	/* ********* Settings ********************** */
	/* ***************************************** */

	public UserBuilder setId(final String id) {
		this.id = id;
		return this;
	}

	public UserBuilder setUserName(final String name) {
		this.name = name;
		return this;
	}

	public UserBuilder setUserSurname(final String surname) {
		this.surname = surname;
		return this;
	}

	public UserBuilder setEmail(final String email) {
		this.email = email;
		return this;
	}

	public UserBuilder setHeadline(final String headline) {
		this.headline = headline;
		return this;
	}

	public UserBuilder setProfilePictureUrl(final String url) {
		this.profilePictureUrl = url;
		return this;
	}

	/* ************************************************ */
	/* ********* Privacy-related ****************** */
	/* ************************************************ */

	public UserBuilder setNickname(final String nickname) {
		this.nickname = nickname;
		return this;
	}

	public UserBuilder setStealty(final String string) {
		this.stealthy = !Boolean.parseBoolean(string);
		return this;
	}

	public UserBuilder setHeadlinePublic(final String string) {
		this.headlinePublic = Boolean.parseBoolean(string);
		return this;
	}

	public UserBuilder setSkillsPublic(final String string) {
		this.skillsPublic = Boolean.parseBoolean(string);
		return this;
	}

	public UserBuilder setNamePublic(final String string) {
		this.namePublic = Boolean.parseBoolean(string);
		return this;
	}

	/* ************************************************ */
	/* ********* Constructor methods ****************** */
	/* ************************************************ */

	public static UserBuilder newInstance() {
		return new UserBuilder();
	}

	private UserBuilder() {
	}

}