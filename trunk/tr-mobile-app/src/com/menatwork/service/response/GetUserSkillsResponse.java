package com.menatwork.service.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.service.ResponseException;

public class GetUserSkillsResponse extends BaseResponse implements Response {

	public GetUserSkillsResponse(JSONObject response) {
		super(response);
	}

	public List<String> getSkills() {
		try {
			if ("[]".equals(getResult().get("skills").toString())) {
				return Collections.<String> emptyList();
			} else {
				return handleNonEmptyResponse();
			}
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

	private List<String> handleNonEmptyResponse() throws JSONException {
		JSONObject skillsObject = getResult().getJSONObject("skills");
		List<String> skills = new ArrayList<String>(skillsObject.length());
		@SuppressWarnings("unchecked")
		Iterator<String> keys = skillsObject.keys();
		while (keys.hasNext()) {
			skills.add(skillsObject.getString(keys.next()));
		}
		return skills;
	}
}
