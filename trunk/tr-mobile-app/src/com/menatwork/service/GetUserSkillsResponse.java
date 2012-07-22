package com.menatwork.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUserSkillsResponse extends BaseResponse implements Response {

	public GetUserSkillsResponse(JSONObject response) {
		super(response);
	}

	public List<String> getSkills() {
		try {
			// TODO: define/implement how this response should be structured
			JSONArray skillsArray = getResponse().getJSONObject("result")
					.getJSONArray("skills");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
