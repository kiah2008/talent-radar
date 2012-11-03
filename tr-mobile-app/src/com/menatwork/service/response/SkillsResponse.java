package com.menatwork.service.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.service.ResponseException;

public class SkillsResponse extends BaseResponse {

	public SkillsResponse(final JSONObject response) {
		super(response);
	}

	public List<String> getSkills() {
		JSONObject result;
		try {
			result = getResult();
			final JSONObject skillsJsonObject = result.getJSONObject("skills");

			final List<String> skills = new ArrayList<String>();

			@SuppressWarnings("unchecked")
			final Iterator<String> keysIterator = skillsJsonObject.keys();
			while (keysIterator.hasNext()) {
				final String key = keysIterator.next();
				skills.add(skillsJsonObject.getString(key));
			}
			return skills;
		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

}