package com.menatwork.service.response;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.JobPosition;
import com.menatwork.service.ResponseException;

public class GetUserJobPositionsResponse extends BaseResponse {

	public GetUserJobPositionsResponse(final JSONObject response) {
		super(response);
	}

	public List<JobPosition> getJobPositions() {
		try {
			return new JsonJobPositionsParser(getJobsJsonObject()).parse();
		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

	private JSONObject getJobsJsonObject() throws JSONException {
		return getResult().getJSONObject("jobs");
	}

}
