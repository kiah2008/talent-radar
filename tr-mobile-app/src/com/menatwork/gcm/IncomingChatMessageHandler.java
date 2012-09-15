package com.menatwork.gcm;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.menatwork.ChatActivity;
import com.menatwork.GCMIntentService;
import com.menatwork.TalentRadarApplication;
import com.menatwork.chat.ChatSession;
import com.menatwork.model.ChatMessage;

public class IncomingChatMessageHandler implements GcmMessageHandler {

	private IncomingChatMessageHandler() {
	}

	@Override
	public void handle(GCMIntentService context, Intent intent) {
		try {
			Bundle extras = intent.getExtras();
			String message = extras.getString("message");
			Intent notificationIntent = new Intent(context, ChatActivity.class);

			String data = extras.getString("data");
			JSONObject jsonData;
			jsonData = new JSONObject(data);
			JSONObject jsonUser = jsonData.getJSONObject("UserFrom");
			JSONObject jsonMessage = jsonData.getJSONObject("UsersMessage");

			this.setUserId(notificationIntent, jsonUser);
			this.setUsername(notificationIntent, jsonUser);
			this.setHeadline(notificationIntent, jsonUser);
			this.setPicture(notificationIntent, jsonUser);

			ChatMessage chatMessage = this
					.createChatMessageFromJson(jsonMessage);

			this.storeMessageInChatSession(context, chatMessage);
			
			// TODO - generate an id so to update (and not accumulate)
			// notifications of chat from each user
			GCMIntentService.generateNotification(context, 0, message,
					notificationIntent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setPicture(Intent notificationIntent, JSONObject jsonUser)
			throws JSONException {
		String profilePicUrl = jsonUser.getString("picture");
		notificationIntent.putExtra(ChatActivity.EXTRAS_PROFILE_PIC_URL,
				profilePicUrl);
	}

	private void storeMessageInChatSession(GCMIntentService context,
			ChatMessage chatMessage) {
		TalentRadarApplication talentRadarApplication = (TalentRadarApplication) context
				.getApplication();
		ChatSession chatSession = talentRadarApplication
				.getChatSessionManager().getChatSessionByUserId(
						chatMessage.getFromId());
		chatSession.addMessage(chatMessage);
	}

	private ChatMessage createChatMessageFromJson(JSONObject jsonMessage)
			throws JSONException {
		String messageId = jsonMessage.getString("id");
		String fromId = jsonMessage.getString("user_from_id");
		String toId = jsonMessage.getString("user_to_id");
		String content = jsonMessage.getString("content");
		return ChatMessage.newInstance(messageId, fromId, toId, content);
	}

	private void setHeadline(Intent notificationIntent, JSONObject jsonUser) {
		// TODO - actually this should be retrieved along with the profile
		// picture (not so important), the chatactivity shouldn't require this
		// piece of data
		notificationIntent.putExtra(ChatActivity.EXTRAS_HEADLINE, "");
	}

	private void setUsername(Intent notificationIntent, JSONObject jsonUser)
			throws JSONException {
		StringBuilder stringBuilder = new StringBuilder(
				jsonUser.getString("name"));
		stringBuilder.append(" ");
		stringBuilder.append(jsonUser.getString("surname"));
		notificationIntent.putExtra(ChatActivity.EXTRAS_USERNAME,
				stringBuilder.toString());
	}

	private void setUserId(Intent notificationIntent, JSONObject jsonUser)
			throws JSONException {
		String userid = jsonUser.getString("id");
		notificationIntent.putExtra(ChatActivity.EXTRAS_USER_ID, userid);
	}

	public static GcmMessageHandler instance() {
		return new IncomingChatMessageHandler();
	}
}
