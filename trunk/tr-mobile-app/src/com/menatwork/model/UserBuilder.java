package com.menatwork.model;

import java.util.LinkedList;
import java.util.List;

import com.menatwork.service.Defect;

public class UserBuilder {

	private String id;
	private String name;
	private String surname;
	private String email;
	private String headline;
	private String profilePictureUrl;
	private String nickname;
	private List<JobPosition> jobPositions;

	private boolean stealthy;
	private boolean headlinePublic;
	private boolean skillsPublic;
	private boolean namePublic;
	private boolean picturePublic;
	private boolean jobPositionsPublic;

	public User build() {
		validateUserHasId();
		validateJobPositionsAreItsOwn();

		final BaseUser user = new BaseUser();
		user.setId(id);
		user.setName(name);
		user.setSurname(surname);
		user.setNickname(nickname);
		user.setEmail(email);
		user.setHeadline(headline);
		user.setProfilePictureUrl(profilePictureUrl);
		user.setJobPositions(jobPositions);

		final DataObjectPrivacySettings privacySettings = new DataObjectPrivacySettings();
		privacySettings.setHeadlinePublic(headlinePublic);
		privacySettings.setNamePublic(namePublic);
		privacySettings.setNickname(nickname);
		privacySettings.setSkillsPublic(skillsPublic);
		privacySettings.setStealthy(stealthy);
		privacySettings.setPicturePublic(picturePublic);
		privacySettings.setJobPositionsPublic(jobPositionsPublic);

		user.setPrivacySettings(privacySettings);

		return user;
	}

	// ************************************************ //
	// ====== Validations ======
	// ************************************************ //

	private void validateJobPositionsAreItsOwn() {
		for (final JobPosition jobPosition : jobPositions)
			if (!jobPosition.getUserId().equals(id))
				throw new Defect(
						"user should only have job positions of his/her own and not job position = "
								+ jobPosition);
	}

	private void validateUserHasId() {
		if (id == null)
			throw new Defect("user ain't have id");
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

	public UserBuilder setJobPositions(final List<JobPosition> jobPositions) {
		this.jobPositions = jobPositions;
		return this;
	}

	/* ************************************************ */
	/* ********* Privacy-related ****************** */
	/* ************************************************ */

	public UserBuilder setNickname(final String nickname) {
		this.nickname = nickname;
		return this;
	}

	public UserBuilder setStealthy(final String string) {
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

	public UserBuilder setProfilePicturePublic(final String picturePublic) {
		this.picturePublic = Boolean.parseBoolean(picturePublic);
		return this;
	}

	public UserBuilder setJobPositionsPublic(final String showJobPositions) {
		this.jobPositionsPublic = Boolean.parseBoolean(showJobPositions);
		return this;
	}

	/* ************************************************ */
	/* ********* Constructor methods ****************** */
	/* ************************************************ */

	public static UserBuilder newInstance() {
		return new UserBuilder();
	}

	private UserBuilder() {
		this.jobPositions = new LinkedList<JobPosition>();
	}
}