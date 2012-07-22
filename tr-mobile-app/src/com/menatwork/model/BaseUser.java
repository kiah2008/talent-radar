package com.menatwork.model;

import java.util.List;

public class BaseUser implements User {

	private String id;
	private String name;
	private String surname;
	private String email;
	private String headline;

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setHeadline(String headline) {
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
	public void setSkills(List<String> skills) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("User.setSkills()");
	}

}
