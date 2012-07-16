package com.menatwork.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.menatwork.R;

import android.content.Context;

public class GetUser extends ServiceCall<GetUserResponse> {

	private String userId;

	public static GetUser newInstance(Context context, String userId) {
		return new GetUser(context, userId);
	}

	private GetUser(Context context, String userId) {
		super(context);
		this.userId = userId;
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_getUser);
	}

	@Override
	protected List<NameValuePair> buildPostParametersList() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair(getString(R.string.post_key_userId),
				userId));
		return params;
	}

	@Override
	protected GetUserResponse wrap(JSONObject response) {
		return new GetUserResponse(response);
	}
}