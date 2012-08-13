package com.menatwork.service;

import com.menatwork.R;

import android.content.Context;

public class Ping extends StandardServiceCall<BaseResponse> {

	public static Ping newInstance(Context context, String fromId, String toId,
			String message) {
		return new Ping(context, fromId, toId, message);
	}

	private Ping(Context context, String fromId, String toId, String message) {
		super(context, BaseResponse.class);
		this.setParameter(getString(R.string.post_key_ping_from_id), fromId);
		this.setParameter(getString(R.string.post_key_ping_to_id), toId);
		this.setParameter(getString(R.string.post_key_ping_content), message);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_ping);
	}

}
