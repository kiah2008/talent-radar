package com.menatwork.register;

import java.util.ArrayList;
import java.util.List;

public class Registration {

	private String realname, nickname, email, password, headline;
	private List<Skill> skills;

	public Registration() {
		this.skills = new ArrayList<Skill>();
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public void addSkill(Skill skill) {
		throw new UnsupportedOperationException();
	}

	public List<Skill> getSkills() {
		return skills;
	}

}
