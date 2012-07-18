package com.menatwork.model;

public class UserBuilder {

	private String id;
	private String name;
	private String surname;
	private String email;
	private String headline;

	public User build() {
		BaseUser user = new BaseUser();
		user.setId(id);
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setHeadline(headline);
		return user;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserName(String name) {
		this.name = name;
	}

	public void setUserSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public static UserBuilder newInstance() {
		return new UserBuilder();
	}

	private UserBuilder() {
	}

}
