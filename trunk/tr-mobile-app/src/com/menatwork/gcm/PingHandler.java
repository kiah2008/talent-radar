package com.menatwork.gcm;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;

import com.menatwork.ChatActivity;
import com.menatwork.GCMIntentService;
import com.menatwork.PingAlertActivity;
import com.menatwork.TalentRadarApplication;
import com.menatwork.notification.TrNotificationBuilder;
import com.menatwork.notification.TrNotificationType;
import com.menatwork.utils.LogUtils;

public class PingHandler implements GcmMessageHandler {

	@Override
	public void handle(final GCMIntentService context, final Intent intent) {
		try {
			// TODO - validate that this notif is for myself (user id)
			final Bundle extras = intent.getExtras();
			LogUtils.d(this, "Received ping", extras);

			final Intent newIntent = new Intent(context,
					PingAlertActivity.class);

			final String data = extras.getString("data");
			final JSONObject jsonData = new JSONObject(data);
			final JSONObject jsonUser = jsonData.getJSONObject("UserFrom");

			final String pingId = this.setPingId(newIntent, jsonData);

			this.setUserId(newIntent, jsonUser);
			this.setFullName(newIntent, jsonUser);
			this.setMessage(newIntent, jsonData);
			this.setPicture(newIntent, jsonUser);

			final boolean vibrationEnabledOnPings = TalentRadarApplication
					.getContext().getPreferences().isVibrationEnabledOnPings();
			TalentRadarApplication.generateAndroidNotification(context,
					Integer.valueOf(pingId), extras.getString("message"),
					newIntent, vibrationEnabledOnPings);

			final TrNotificationBuilder builder = TrNotificationBuilder
					.newInstance();
			builder.setType(TrNotificationType.PING);
			builder.setDate(new Date());
			builder.setHeader("Ping!");
			builder.setDescription(extras.getString("message"));
			builder.setIntent(newIntent);
			context.generateTrNotification(builder.build());
		} catch (final JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private void setMessage(final Intent newIntent, final JSONObject jsonData)
			throws JSONException {
		final String message = jsonData.getJSONObject("UsersPing").getString(
				"content");
		newIntent.putExtra(PingAlertActivity.EXTRA_MESSAGE, message);
	}

	private void setFullName(final Intent newIntent, final JSONObject jsonUser)
			throws JSONException {
		final StringBuilder fullnameBuilder = new StringBuilder(
				jsonUser.getString("name"));
		fullnameBuilder.append(" ");
		fullnameBuilder.append(jsonUser.getString("surname"));
		final String fullName = fullnameBuilder.toString();
		newIntent.putExtra(PingAlertActivity.EXTRA_USER_FULLNAME, fullName);
	}

	private void setUserId(final Intent newIntent, final JSONObject jsonUser)
			throws JSONException {
		final String userId = jsonUser.getString("id");
		newIntent.putExtra(PingAlertActivity.EXTRA_USER_ID, userId);
	}

	private String setPingId(final Intent newIntent, final JSONObject jsonData)
			throws JSONException {
		final String pingId = jsonData.getJSONObject("UsersPing").getString(
				"id");
		newIntent.putExtra(PingAlertActivity.EXTRA_PING_ID, pingId);
		return pingId;
	}

	private void setPicture(final Intent notificationIntent,
			final JSONObject jsonUser) throws JSONException {
		final String profilePicUrl = jsonUser.getString("picture");
		notificationIntent.putExtra(ChatActivity.EXTRAS_PROFILE_PIC_URL,
				profilePicUrl);
	}

	public static GcmMessageHandler instance() {
		return new PingHandler();
	}
}
