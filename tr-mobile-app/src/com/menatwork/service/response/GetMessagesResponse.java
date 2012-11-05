package com.menatwork.service.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.ChatMessage;
import com.menatwork.service.ResponseException;

public class GetMessagesResponse extends BaseResponse {

	public GetMessagesResponse(final JSONObject response) {
		super(response);
	}

	public List<ChatMessage> getMessages() {
		try {
			final List<ChatMessage> messagesList = new ArrayList<ChatMessage>();
			final JSONObject messages = getResult().getJSONObject("messages");
			final Iterator<String> keys = getKeys(messages);
			while (keys.hasNext()) {
				final JSONObject message = messages.getJSONObject(keys.next())
						.getJSONObject("UsersMessage");
				final String messageId = message.getString("id");
				final String fromId = message.getString("user_from_id");
				final String toId = message.getString("user_to_id");
				final String content = message.getString("content");
				// TODO - see what wave this codifcation - boris - 20/09/2012
				// content = Arrays.toString(Base64.decode(content,
				// Base64.DEFAULT));
				messagesList.add(ChatMessage.newInstance(messageId, fromId,
						toId, content));
			}
			Collections.sort(messagesList, new MessageIdComparator());
			return messagesList;
		} catch (final JSONException e) {
			throw new ResponseException(e);
		}
	}

	private Iterator<String> getKeys(final JSONObject messages) {
		@SuppressWarnings("unchecked")
		final Iterator<String> keys = messages.keys();
		return keys;
	}

	private class MessageIdComparator implements Comparator<ChatMessage> {

		@Override
		public int compare(final ChatMessage lhs, final ChatMessage rhs) {
			final int firstId = Integer.parseInt(lhs.getMessageId());
			final int secondId = Integer.parseInt(rhs.getMessageId());
			if (firstId == secondId)
				throw new RuntimeException("what the fuck?");
			return firstId < secondId ? -1 : 1;
		}

	}
}