package com.menatwork.model;

import java.util.LinkedList;
import java.util.List;

public class BaseUser implements User {

	private String id;
	private String name;
	private String surname;
	private String email;
	private String headline;
	private List<String> skills = new LinkedList<String>();
	private String profilePictureUrl;
	private DataObjectPrivacySettings privacySettings;
	private String nickname;

	public DataObjectPrivacySettings getPrivacySettings() {
		return privacySettings;
	}

	@Override
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setHeadline(final String headline) {
		this.headline = headline;
	}

	@Override
	public String toString() {
		return "BaseUser [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", headline=" + headline + ", skills="
				+ skills + ", profilePictureUrl=" + profilePictureUrl
				+ ", privacySettings=" + privacySettings + ", nickname="
				+ nickname + "]";
	}

	@Override
	/**
	 * Gets the 'long way' to identify this user (either name+surname if visible, or just the nickname)
	 */
	public String getDisplayableLongName() {
		if (privacySettings.isNamePublic())
			return name + " " + surname;
		else
			return nickname;
	}

	@Override
	public String getHeadline() {
		return headline;
	}

	@Override
	public List<String> getSkills() {
		return skills;
	}

	@Override
	public void setSkills(final List<String> skills) {
		this.skills = skills;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSurname() {
		return surname;
	}

	@Override
	public String getNickname() {
		return nickname;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	public void setProfilePictureUrl(final String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public void setPrivacySettings(
			final DataObjectPrivacySettings privacySettings) {
		this.privacySettings = privacySettings;
	}

}
