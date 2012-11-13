package com.menatwork.service;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.GetMessagesResponse;

public class GetMessages extends StandardServiceCall<GetMessagesResponse> {

	/**
	 * 
	 * @param context
	 * @param userId1
	 * @param userId2
	 * @param startingId
	 * @param limit
	 *            (2, 5, 10, 20, 30, Todos)
	 * @return
	 */
	public static GetMessages newInstance(final Context context,
			final String userId1, final String userId2, final String startingId, final String limit) {
		return new GetMessages(context, userId1, userId2, startingId, limit);
	}

	/*
	 * Get Messages from chat
	 * 
	 * http://talent-radar.com/talent-radar/users...app_getMessages
	 * 
	 * data[UsersMessage][user1_id]
	 * 
	 * data[UsersMessage][user2_id]
	 * 
	 * data[UsersMessage][limit]
	 * 
	 * data[UsersMessage][first_message_id]
	 */

	private GetMessages(final Context context, final String userId1,
			final String userId2, final String startingId, final String limit) {
		super(context, GetMessagesResponse.class);
		setParameter(R.string.post_key_get_chat_messages_id_1, userId1);
		setParameter(R.string.post_key_get_chat_messages_id_2, userId2);
		setParameter(R.string.post_key_get_chat_messages_limit, limit);
		setParameter(R.string.post_key_get_chat_messages_starting_id, startingId);
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_get_chat_messages);
	}

}