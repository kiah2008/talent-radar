package com.menatwork.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.GetUserResponse;

public class GetUser extends ServiceCall<GetUserResponse> {

	private final String userId;
	private final String localUserId;

	public static GetUser newInstance(final Context context, final String userId, final String localUserId) {
		return new GetUser(context, userId, localUserId);
	}

	private GetUser(final Context context, final String userId, final String localUserId) {
		super(context);
		this.userId = userId;
		this.localUserId = localUserId;
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_getUser);
	}

	@Override
	protected List<NameValuePair> buildPostParametersList() {
		final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair(getString(R.string.post_key_userId),
				userId));
		params.add(new BasicNameValuePair(getString(R.string.post_key_user_request_id), localUserId));
		return params;
	}

	@Override
	protected GetUserResponse wrap(final JSONObject response) {
		return new GetUserResponse(response);
	}
}