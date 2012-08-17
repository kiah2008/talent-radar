package com.menatwork.service;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.ShareLocationAndGetUsersResponse;

/**
 * data[UsersOnline][user_id] <br />
 * data[UsersOnline][latitude]<br />
 * data[UsersOnline][longitude] <br />
 * data[UsersOnline][duration]<br />
 *
 * @author miguel
 *
 */
public class ShareLocationAndGetUsers extends
		StandardServiceCall<ShareLocationAndGetUsersResponse> {

	private ShareLocationAndGetUsers(final Context context,
			final String userId, final double latitude, final double longitude,
			final int durationSeconds) {
		super(context, ShareLocationAndGetUsersResponse.class);
		setParameter( //
				getString(R.string.post_key_share_location_and_get_users_user_id), //
				userId);
		setParameter( //
				getString(R.string.post_key_share_location_and_get_users_latitude), //
				latitude);
		setParameter( //
				getString(R.string.post_key_share_location_and_get_users_longitude), //
				longitude);
		setParameter( //
				getString(R.string.post_key_share_location_and_get_users_duration), //
				durationSeconds);
	}

	public static ShareLocationAndGetUsers newInstance(final Context context,
			final String userId, final double latitude, final double longitude,
			final int durationSeconds) {

		return new ShareLocationAndGetUsers(context, userId, latitude,
				longitude, durationSeconds);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_share_location_and_get_users);
	}
}