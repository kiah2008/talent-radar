package com.menatwork.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.RegisterResponse;

public class Register extends ServiceCall<RegisterResponse> {

	private final String email;
	private final String nickname;
	private final String password;
	private final String name;
	private final String surname;
	private final String headline;
	private final String namePublic;
	private final String headlinePublic;
	private final String skillsPublic;
	private final List<String> skills;

	public static Register newInstance(final Context context,
			final String email, final String nickname, final String password,
			final String name, final String surname, final String namePublic,
			final String headline, final String headlinePublic,
			final List<String> skills, final String skillsPublic) {
		return new Register(context, email, nickname, password, name, surname,
				namePublic, headline, headlinePublic, skills, skillsPublic);
	}

	private Register(final Context context, final String email,
			final String nickname, final String password, final String name,
			final String surname, final String namePublic,
			final String headline, final String headlinePublic,
			final List<String> skills, final String skillsPublic) {
		super(context);
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.namePublic = namePublic;
		this.headline = headline;
		this.headlinePublic = headlinePublic;
		this.skills = skills;
		this.skillsPublic = skillsPublic;
	}

	@Override
	protected String getMethodUri() {
		return context.getString(R.string.post_uri_registration);
	}

	@Override
	protected List<NameValuePair> buildPostParametersList() {
		final List<NameValuePair> params = new ArrayList<NameValuePair>(6);

		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_email), email));
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_username), nickname));
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_password), password));
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_name), name));
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_surname), surname));
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_name_public),
				replacementFor(namePublic)));
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_headline), headline));
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_headline_public),
				replacementFor(headlinePublic)));
		this.addSkillsParam(params);
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_register_skills_public),
				replacementFor(skillsPublic)));
		return params;
	}

	private void addSkillsParam(final List<NameValuePair> params) {
		for (final String skill : skills)
			params.add(new BasicNameValuePair(
					getString(R.string.post_key_register_skills), skill));
	}

	private String replacementFor(final String booleanValue) {
		return "true".equals(booleanValue) ? "1" : "0";
	}

	@Override
	protected RegisterResponse wrap(final JSONObject response) {
		return new RegisterResponse(response);
	}
}