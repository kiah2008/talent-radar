package com.menatwork.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.menatwork.R;

public class Register extends ServiceCall<RegisterResponse> {

	private String email;
	private String nickname;
	private String password;
	private String name;
	private String surname;
	private String headline;

	public static Register newInstance(Context context, String email,
			String nickname, String password, String name, String surname,
			String headline) {
		return new Register(context, email, nickname, password, name, surname,
				headline);
	}

	private Register(Context context, String email, String nickname,
			String password, String name, String surname, String headline) {
		super(context);
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.headline = headline;
	}

	@Override
	protected String getMethodUri() {
		return context.getString(R.string.post_uri_registration);
	}

	@Override
	protected List<NameValuePair> buildPostParametersList() {
		List<NameValuePair> params = new ArrayList<NameValuePair>(6);

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
				getString(R.string.post_key_register_headline), headline));
		return params;
	}

	@Override
	protected RegisterResponse wrap(JSONObject response) {
		return new RegisterResponse(response);
	}
}