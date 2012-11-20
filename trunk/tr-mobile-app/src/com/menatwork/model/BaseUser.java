package com.menatwork.model;

import java.util.Collections;
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
	private DataObjectPrivacySettings declaredPrivacySettings;
	private DataObjectPrivacySettings realPrivacySettings;
	private String nickname;
	private List<JobPosition> jobPositions;

	@Override
	public DataObjectPrivacySettings getPrivacySettings() {
		return declaredPrivacySettings;
	}

	@Override
	public String getProfilePictureUrl() {
		if (declaredPrivacySettings.isPicturePublic()
				|| realPrivacySettings.isPicturePublic())
			return profilePictureUrl;
		else
			return "non-parseable-url";
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	/**
	 * Gets the 'long way' to identify this user (either name+surname if visible, or just the nickname)
	 */
	public String getDisplayableLongName() {
		if (declaredPrivacySettings.isNamePublic()
				|| realPrivacySettings.isNamePublic())
			return name + " " + surname;
		else
			return nickname;
	}

	@Override
	/**
	 * Gets the 'long way' to identify this user (either name+surname if visible, or just the nickname)
	 */
	public String getDisplayableShortName() {
		if (declaredPrivacySettings.isNamePublic()
				|| realPrivacySettings.isNamePublic())
			return name;
		else
			return nickname;
	}

	@Override
	public String getHeadline() {
		if (declaredPrivacySettings.isHeadlinePublic()
				|| realPrivacySettings.isHeadlinePublic())
			return headline;
		else
			return null;
	}

	@Override
	public List<String> getSkills() {
		if (declaredPrivacySettings.isSkillsPublic()
				|| realPrivacySettings.isSkillsPublic())
			return skills;
		else
			return Collections.emptyList();
	}

	@Override
	public String getName() {
		if (declaredPrivacySettings.isNamePublic()
				|| realPrivacySettings.isNamePublic())
			return name;
		else
			return null;
	}

	@Override
	public String getSurname() {
		if (declaredPrivacySettings.isNamePublic()
				|| realPrivacySettings.isNamePublic())
			return surname;
		else
			return null;
	}

	@Override
	public String getNickname() {
		return nickname;
	}

	@Override
	public List<JobPosition> getJobPositions() {
		if (declaredPrivacySettings.isJobPositionsPublic()
				|| realPrivacySettings.isJobPositionsPublic())
			return jobPositions;
		else
			return Collections.emptyList();
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
				+ ", declaredPrivacySettings=" + declaredPrivacySettings
				+ ", realPrivacySettings=" + realPrivacySettings
				+ ", nickname=" + nickname + ", jobPositions=" + jobPositions
				+ "]";
	}

	@Override
	public void setSkills(final List<String> skills) {
		// alme says:
		// this is a consequence of a fucked-up design. Skills are not fetched
		// and wrote in the same way as any other attribute, so here we must
		// infer that if skills were wrote, we could see them, and we have real
		// privacy visibility over them
		if (!skills.isEmpty())
			realPrivacySettings.setSkillsPublic(true);
		this.skills = skills;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	public void setProfilePictureUrl(final String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public void setDeclaredPrivacySettings(
			final DataObjectPrivacySettings privacySettings) {
		this.declaredPrivacySettings = privacySettings;
	}

	public void setRealPrivacySettings(
			final DataObjectPrivacySettings privacySettings) {
		realPrivacySettings = privacySettings;
	}

	@Override
	public boolean hasSkill(final String skill) {
		return skills.contains(skill);
	}

	public void setJobPositions(final List<JobPosition> jobPositions) {
		this.jobPositions = jobPositions;
	}

}
