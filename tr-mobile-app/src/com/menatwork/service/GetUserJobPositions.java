package com.menatwork.service;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.GetUserJobPositionsResponse;

public class GetUserJobPositions extends StandardServiceCall<GetUserJobPositionsResponse> {

	private GetUserJobPositions(final Context context, final String userId) {
		super(context, GetUserJobPositionsResponse.class);
		setParameter( //
				getString(R.string.post_key_get_user_job_positions_user_id), //
				userId);
	}

	public static GetUserJobPositions newInstance(final Context context, final String userId) {
		return new GetUserJobPositions(context, userId);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_get_user_job_positions);
	}

}
