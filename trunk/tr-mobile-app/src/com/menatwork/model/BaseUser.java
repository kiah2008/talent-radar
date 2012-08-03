package com.menatwork.model;

import java.util.List;

public class BaseUser implements User {

	private String id;
	private String name;
	private String surname;
	private String email;
	private String headline;
//	private String username;

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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("User.getSkills()");
	}

	@Override
	public void setSkills(final List<String> skills) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("User.setSkills()");
	}

//	@Override
//	public String getUsername() {
//		return username;
//	}

}
