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
				+ ", email=" + email + ", headline=" + headline + "]";
	}

	@Override
	public String getFullName() {
		return name + " " + surname;
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
	public String getUsername() {
		// TODO - this must return the username rather than the name - boris -
		// 16/08/2012
		return name;
	}

	public void setProfilePictureUrl(final String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

}
