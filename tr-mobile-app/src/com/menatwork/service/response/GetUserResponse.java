package com.menatwork.service.response;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.JobPosition;
import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;
import com.menatwork.service.ResponseException;

public class GetUserResponse extends BaseResponse {

	private static final String USER_KEY = "User";
	private static final String JOB_POSITIONS_KEY = "UsersJob";

	public GetUserResponse(final JSONObject response) {
		super(response);
	}

	// show_in_searches / username / show_headline / show_skills / show_name
	public User getUser() {
		try {
			final JSONObject userResultObject = getResponse().getJSONObject("result").getJSONObject("user");

			final JSONObject userJsonObject = userResultObject.getJSONObject(USER_KEY);
			final UserBuilder userBuilder = new JsonUserParser(userJsonObject).parse();

			if (responseHasJobPositions(userResultObject)) {
				final JSONObject jobsPositionsJsonObject = userResultObject.getJSONObject(JOB_POSITIONS_KEY);
				final List<JobPosition> jobPositions = new JsonJobPositionsParser(jobsPositionsJsonObject)
						.parse();

				userBuilder.setJobPositions(jobPositions);
			}

			// TODO - include UsersStudy, UsersSkill - miguel - 19/11/2012

			return userBuilder.build();

		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

	private boolean responseHasJobPositions(final JSONObject userJsonObject) {
		return userJsonObject.has(JOB_POSITIONS_KEY);
	}

}
