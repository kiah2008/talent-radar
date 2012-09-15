package com.menatwork.gcm;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

import com.menatwork.ChatActivity;
import com.menatwork.GCMIntentService;
import com.menatwork.PingAlertActivity;
import com.menatwork.utils.LogUtils;

public class PingHandler implements GcmMessageHandler {

	@Override
	public void handle(GCMIntentService context, Intent intent) {
		try {
			// TODO - validate that this notif is for myself (user id)
			Bundle extras = intent.getExtras();
			LogUtils.d(this, "Received ping", extras);

			Intent newIntent = new Intent(context, PingAlertActivity.class);

			String data = extras.getString("data");
			JSONObject jsonData = new JSONObject(data);
			JSONObject jsonUser = jsonData.getJSONObject("UserFrom");

			String pingId = this.setPingId(newIntent, jsonData);

			this.setUserId(newIntent, jsonUser);
			this.setFullName(newIntent, jsonUser);
			this.setMessage(newIntent, jsonData);
			this.setPicture(newIntent, jsonUser);

			GCMIntentService.generateNotification(context,
					Integer.valueOf(pingId), extras.getString("message"),
					newIntent);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	private void setMessage(Intent newIntent, JSONObject jsonData)
			throws JSONException {
		String message = jsonData.getJSONObject("UsersPing").getString(
				"content");
		newIntent.putExtra(PingAlertActivity.EXTRA_MESSAGE, message);
	}

	private void setFullName(Intent newIntent, JSONObject jsonUser)
			throws JSONException {
		StringBuilder fullnameBuilder = new StringBuilder(
				jsonUser.getString("name"));
		fullnameBuilder.append(" ");
		fullnameBuilder.append(jsonUser.getString("surname"));
		String fullName = fullnameBuilder.toString();
		newIntent.putExtra(PingAlertActivity.EXTRA_USER_FULLNAME, fullName);
	}

	private void setUserId(Intent newIntent, JSONObject jsonUser)
			throws JSONException {
		String userId = jsonUser.getString("id");
		newIntent.putExtra(PingAlertActivity.EXTRA_USER_ID, userId);
	}

	private String setPingId(Intent newIntent, JSONObject jsonData)
			throws JSONException {
		String pingId = jsonData.getJSONObject("UsersPing").getString("id");
		newIntent.putExtra(PingAlertActivity.EXTRA_PING_ID, pingId);
		return pingId;
	}

	private void setPicture(Intent notificationIntent, JSONObject jsonUser)
			throws JSONException {
		String profilePicUrl = jsonUser.getString("picture");
		notificationIntent.putExtra(ChatActivity.EXTRAS_PROFILE_PIC_URL,
				profilePicUrl);
	}

	public static GcmMessageHandler instance() {
		return new PingHandler();
	}
}
