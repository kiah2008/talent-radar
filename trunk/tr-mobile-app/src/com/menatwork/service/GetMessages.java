package com.menatwork.service;



import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.GetMessagesResponse;

public class GetMessages extends StandardServiceCall<GetMessagesResponse> {

	public static GetMessages newInstance(Context context, String userId1,
			String userId2) {
		return new GetMessages(context, userId1, userId2);
	}

	/*
	 * Get Messages from chat
	 * 
	 * http://talent-radar.com/talent-radar/users...app_getMessages
	 * 
	 * data[UsersMessage][user1_id]
	 * 
	 * data[UsersMessage][user2_id]
	 */

	private GetMessages(Context context, String userId1, String userId2) {
		super(context, GetMessagesResponse.class);
		setParameter(R.string.post_key_get_chat_messages_id_1, userId1);
		setParameter(R.string.post_key_get_chat_messages_id_2, userId2);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_get_chat_messages);
	}

}