package com.menatwork.service;


import android.content.Context;

import com.menatwork.R;
import com.menatwork.R.string;
import com.menatwork.service.response.GetPingResponse;

public class GetPing extends StandardServiceCall<GetPingResponse> {

	public static GetPing newInstance(Context context, String pingId,
			String userToId) {
		return new GetPing(context, pingId, userToId);
	}

	private GetPing(Context context, String pingId, String userToId) {
		super(context, GetPingResponse.class);
		this.setParameter(R.string.post_key_get_ping_id, pingId);
		this.setParameter(R.string.post_key_get_ping_to_id, userToId);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_get_ping);
	}

}