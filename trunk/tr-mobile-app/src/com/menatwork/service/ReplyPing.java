package com.menatwork.service;

import com.menatwork.R;
import com.menatwork.service.response.BaseResponse;

import android.content.Context;

public class ReplyPing extends StandardServiceCall<BaseResponse> {

	public enum Answer {
		ACCEPT(1), IGNORE(2), BAN(3);
		private int code;

		private Answer(int code) {
			this.code = code;
		}

	}

	public static ReplyPing newInstance(Context context, String localUserId,
			String pingId, Answer answer) {
		return new ReplyPing(context, localUserId, pingId, answer);
	}

	private ReplyPing(Context context, String localUserId, String pingId,
			Answer answer) {
		super(context, BaseResponse.class);
		this.setParameter(R.string.post_key_reply_ping_id, pingId);
		this.setParameter(R.string.post_key_reply_ping_to_id, localUserId);
		this.setParameter(R.string.post_key_reply_ping_response, answer.code);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_reply_ping);
	}

}
