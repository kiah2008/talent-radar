package com.menatwork.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import com.menatwork.R;
import com.menatwork.service.response.GetUserSkillsResponse;

public class GetUserSkills extends ServiceCall<GetUserSkillsResponse> {

	private String userId;

	private GetUserSkills(Context context, String userId) {
		super(context);
		this.userId = userId;
	}

	public static GetUserSkills newInstance(Context context, String userId) {
		return new GetUserSkills(context, userId);
	}

	@Override
	protected List<NameValuePair> buildPostParametersList() {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair(
				getString(R.string.post_key_get_user_skills_user_id), userId));
		return params;
	}

	@Override
	protected GetUserSkillsResponse wrap(JSONObject response) {
		return new GetUserSkillsResponse(response);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_get_user_skills);
	}

}
