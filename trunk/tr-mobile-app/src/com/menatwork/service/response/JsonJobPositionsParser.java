package com.menatwork.service.response;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.JobPosition;
import com.menatwork.service.ResponseException;

public class JsonJobPositionsParser {

	private final JSONObject jobsPositionsJsonObject;

	public JsonJobPositionsParser(final JSONObject jobsPositionsJsonObject) {
		this.jobsPositionsJsonObject = jobsPositionsJsonObject;
	}

	public List<JobPosition> parse() {
		try {
			final List<JobPosition> jobPositions = new LinkedList<JobPosition>();
			final int jobsArrayLength = jobsPositionsJsonObject.length();

			JSONObject jobObject;
			for (int i = 0; i < jobsArrayLength; i++) {
				final String userIndex = String.valueOf(i);
				jobObject = jobsPositionsJsonObject.getJSONObject(userIndex);

				final JobPosition jobPosition = parseJobPosition(jobObject);
				jobPositions.add(jobPosition);
			}

			return jobPositions;

		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

	private JobPosition parseJobPosition(final JSONObject jobObject)
			throws JSONException {
		final String id = jobObject.getString("id");
		final String title = jobObject.getString("title");
		final boolean isCurrent = jobObject.getInt("is_current") == 1;
		final String userId = jobObject.getString("user_id");

		return new JobPosition(id, title, isCurrent, userId);
	}

}
