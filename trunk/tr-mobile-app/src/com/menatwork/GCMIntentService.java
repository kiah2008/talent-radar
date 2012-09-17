/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.menatwork;

import java.util.HashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.menatwork.gcm.GcmMessageHandler;
import com.menatwork.gcm.IncomingChatMessageHandler;
import com.menatwork.gcm.PingHandler;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TYPE_PING = "1";
	private static final String TYPE_NEW_CHAT_MESSAGE = "3";

	private static final String TAG = "GCMIntentService";

	private final HashMap<String, GcmMessageHandler> handlers;

	public static String SENDER_ID;
	static {
		// hackity hack to keep sender id unversioned
		SENDER_ID = TalentRadarApplication.getContext().getString(
				R.string.gcm_sender_id);
	}

	public GCMIntentService() {
		super(SENDER_ID);
		handlers = new HashMap<String, GcmMessageHandler>();
		handlers.put(TYPE_PING, PingHandler.instance());
		handlers.put(TYPE_NEW_CHAT_MESSAGE,
				IncomingChatMessageHandler.instance());
	}

	@Override
	protected void onRegistered(final Context context,
			final String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		((TalentRadarApplication) getApplication())
				.onDeviceRegistered(registrationId);
	}

	@Override
	protected void onUnregistered(final Context context,
			final String registrationId) {
		Log.i(TAG, "Device unregistered");
	}

	@Override
	protected void onMessage(final Context context, final Intent intent) {
		final Bundle extras = intent.getExtras();
		final String targetUserid = extras.getString("userId");
		final String messageType = extras.getString("type");

		if (this.nullSafeCheckTargetUserId(targetUserid)) {
			// dispatch the message to the right handler
			final GcmMessageHandler messageHandler = this.handlers
					.get(messageType);
			if (messageHandler == null)
				Log.w(TAG, "There is no handler registered for message type: "
						+ messageType);
			else
				messageHandler.handle(this, intent);
		}
	}

	private boolean nullSafeCheckTargetUserId(final String targetUserid) {
		final TalentRadarApplication talentRadarApp = TalentRadarApplication
				.getContext();
		if (targetUserid == null)
			return false;
		return talentRadarApp.isUserLoggedIn(targetUserid);
	}

	@Override
	protected void onDeletedMessages(final Context context, final int total) {
		Log.i(TAG, "Received deleted messages notification");
	}

	@Override
	public void onError(final Context context, final String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(final Context context,
			final String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	public static void generateNotification(final Context context,
			final int id, final String message, final Intent notificationIntent) {
		final int icon = R.drawable.ic_launcher;
		final long when = System.currentTimeMillis();
		final NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		final Notification notification = new Notification(icon, message, when);
		final String title = context.getString(R.string.app_name);
		final PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(id, notification);
	}

}
