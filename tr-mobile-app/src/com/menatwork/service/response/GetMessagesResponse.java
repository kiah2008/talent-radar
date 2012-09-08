package com.menatwork.service.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.menatwork.model.ChatMessage;
import com.menatwork.service.ResponseException;

public class GetMessagesResponse extends BaseResponse {

	public GetMessagesResponse(JSONObject response) {
		super(response);
	}

	public List<ChatMessage> getMessages() {
		try {
			List<ChatMessage> messagesList = new ArrayList<ChatMessage>();
			JSONObject messages = getResult().getJSONObject("messages");
			Iterator<String> keys = getKeys(messages);
			while (keys.hasNext()) {
				JSONObject message = messages.getJSONObject(keys.next())
						.getJSONObject("UsersMessage");
				String messageId = message.getString("id");
				String fromId = message.getString("user_from_id");
				String toId = message.getString("user_to_id");
				String content = message.getString("content");
				messagesList.add(ChatMessage.newInstance(messageId, toId,
						fromId, content));
			}
			return messagesList;
		} catch (JSONException e) {
			throw new ResponseException(e);
		}
	}

	private Iterator<String> getKeys(JSONObject messages) {
		@SuppressWarnings("unchecked")
		Iterator<String> keys = messages.keys();
		return keys;
	}

}