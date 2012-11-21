package com.menatwork.gcm;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.menatwork.ChatActivity;
import com.menatwork.GCMIntentService;
import com.menatwork.TalentRadarApplication;
import com.menatwork.chat.ChatSession;
import com.menatwork.model.ChatMessage;
import com.menatwork.notification.TrNotificationBuilder;
import com.menatwork.notification.TrNotificationType;

public class IncomingChatMessageHandler implements GcmMessageHandler {

	private IncomingChatMessageHandler() {
	}

	@Override
	public void handle(final GCMIntentService context, final Intent intent) {
		try {
			final Bundle extras = intent.getExtras();
			final String message = extras.getString("message");
			final Intent notificationIntent = new Intent(context,
					ChatActivity.class);

			final String data = extras.getString("data");
			JSONObject jsonData;
			jsonData = new JSONObject(data);
			final JSONObject jsonUser = jsonData.getJSONObject("UserFrom");
			final JSONObject jsonMessage = jsonData
					.getJSONObject("UsersMessage");

			this.setUserId(notificationIntent, jsonUser);
			this.setUsername(notificationIntent, jsonUser);
			this.setHeadline(notificationIntent, jsonUser);
			this.setPicture(notificationIntent, jsonUser);

			final ChatMessage chatMessage = this
					.createChatMessageFromJson(jsonMessage);

			this.storeMessageInChatSession(context, chatMessage);

			// TODO - generate an id so to update (and not accumulate)
			// notifications of chat from each user
			final String notificationId = createNotificationId(jsonMessage,
					jsonUser);
			final boolean vibrationEnabledOnMessages = TalentRadarApplication
					.getContext().getPreferences()
					.isVibrationEnabledOnMessages();
			TalentRadarApplication.generateAndroidNotification(context, 0,
					message, notificationIntent, vibrationEnabledOnMessages);

			final TrNotificationBuilder builder = TrNotificationBuilder
					.newInstance();
			builder.setType(TrNotificationType.CHAT);
			builder.setDate(new Date());
			builder.setHeader(message);
			builder.setDescription(getMessageForTrNotification(jsonMessage));
			builder.setIntent(notificationIntent);
			builder.setNotificationId(notificationId);
			context.generateTrNotification(builder.build());
		} catch (final JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getMessageForTrNotification(final JSONObject jsonMessage)
			throws JSONException {
		final String message = jsonMessage.getString("content");
		final int messageLimit = 16;
		if (message.length() <= messageLimit)
			return message;
		else {
			final String shortMessage = message.substring(0, messageLimit);
			return shortMessage + "...";
		}
	}

	private String createNotificationId(final JSONObject jsonMessage,
			final JSONObject jsonUser) throws JSONException {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(String.valueOf(TrNotificationType.CHAT.getTypeId()));
		stringBuilder.append(jsonUser.getString("id"));
		return stringBuilder.toString();
	}

	private void setPicture(final Intent notificationIntent,
			final JSONObject jsonUser) throws JSONException {
		final String profilePicUrl = jsonUser.getString("picture");
		notificationIntent.putExtra(ChatActivity.EXTRAS_PROFILE_PIC_URL,
				profilePicUrl);
	}

	private void storeMessageInChatSession(final GCMIntentService context,
			final ChatMessage chatMessage) {
		final TalentRadarApplication talentRadarApplication = (TalentRadarApplication) context
				.getApplication();
		final ChatSession chatSession = talentRadarApplication
				.getChatSessionManager().getChatSessionByUserId(
						chatMessage.getFromId());
		chatSession.addMessage(chatMessage);
	}

	private ChatMessage createChatMessageFromJson(final JSONObject jsonMessage)
			throws JSONException {
		final String messageId = jsonMessage.getString("id");
		final String fromId = jsonMessage.getString("user_from_id");
		final String toId = jsonMessage.getString("user_to_id");
		final String content = jsonMessage.getString("content");
		return ChatMessage.newInstance(messageId, fromId, toId, content);
	}

	private void setHeadline(final Intent notificationIntent,
			final JSONObject jsonUser) {
		// TODO - actually this should be retrieved along with the profile
		// picture (not so important), the chatactivity shouldn't require this
		// piece of data
		notificationIntent.putExtra(ChatActivity.EXTRAS_HEADLINE, "");
	}

	private void setUsername(final Intent notificationIntent,
			final JSONObject jsonUser) throws JSONException {
		final String name = getFullUsername(jsonUser);
		notificationIntent.putExtra(ChatActivity.EXTRAS_USERNAME, name);
	}

	private String getFullUsername(final JSONObject jsonUser)
			throws JSONException {
		final StringBuilder stringBuilder = new StringBuilder(
				jsonUser.getString("name"));
		stringBuilder.append(" ");
		stringBuilder.append(jsonUser.getString("surname"));
		final String name = stringBuilder.toString();
		return name;
	}

	private void setUserId(final Intent notificationIntent,
			final JSONObject jsonUser) throws JSONException {
		final String userid = jsonUser.getString("id");
		notificationIntent.putExtra(ChatActivity.EXTRAS_USER_ID, userid);
	}

	public static GcmMessageHandler instance() {
		return new IncomingChatMessageHandler();
	}

}
