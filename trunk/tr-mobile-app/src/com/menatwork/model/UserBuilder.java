package com.menatwork.model;

public class UserBuilder {

	private String id;
	private String name;
	private String surname;
	private String email;
	private String headline;
	private String profilePictureUrl;

	public User build() {
		final BaseUser user = new BaseUser();
		user.setId(id);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setHeadline(headline);
		user.setProfilePictureUrl(profilePictureUrl);
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

	public String getProfilePictureUrl() {
		return profilePictureUrl;
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
