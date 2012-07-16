package com.menatwork.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.menatwork.R;

public class Login extends ServiceCall<LoginResponse> {
	private String email;
	private String password;

	private Login(Context context, String email, String password) {
		super(context);
		this.email = email;
		this.password = password;
	}

	public static Login newInstance(Context context, String email,
			String password) {
		return new Login(context, email, password);
	}

	@Override
	protected List<NameValuePair> buildPostParametersList() {
		List<NameValuePair> postParams = new ArrayList<NameValuePair>(2);

		postParams.add(new BasicNameValuePair(context
				.getString(R.string.post_key_login_email), email));
		postParams.add(new BasicNameValuePair(context
				.getString(R.string.post_key_login_password), password));
		return postParams;
	}

	@Override
	protected String getMethodUri() {
		return context.getString(R.string.post_uri_login);
	}

	@Override
	protected LoginResponse wrap(JSONObject response) {
		return new LoginResponse(response);
	}

}