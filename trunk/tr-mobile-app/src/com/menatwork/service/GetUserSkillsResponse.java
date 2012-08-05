package com.menatwork.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUserSkillsResponse extends BaseResponse implements Response {

	public GetUserSkillsResponse(JSONObject response) {
		super(response);
	}

	@SuppressWarnings("unchecked")
	public List<String> getSkills() {
		try {
			JSONObject skillsObject = getResponse().getJSONObject("result")
					.getJSONObject("skills");
			List<String> skills = new ArrayList<String>(skillsObject.length());
			Iterator<String> keys = skillsObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				skills.add(skillsObject.getString(key));
			}
			return skills;
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}
}
