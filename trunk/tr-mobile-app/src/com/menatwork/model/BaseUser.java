package com.menatwork.model;

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

}
