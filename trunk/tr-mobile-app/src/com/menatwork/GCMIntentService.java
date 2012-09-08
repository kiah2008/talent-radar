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
import com.menatwork.gcm.PingHandler;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TYPE_PING = "1";

	private static final String TAG = "GCMIntentService";
	private HashMap<String, GcmMessageHandler> handlers;

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
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		((TalentRadarApplication) getApplication())
				.onDeviceRegistered(registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		String messageType = extras.getString("type");

		// dispatch the message to the right handler
		GcmMessageHandler messageHandler = this.handlers.get(messageType);
		if (messageHandler == null) {
			Log.w(TAG, "There is no handler registered for message type: "
					+ messageType);
		} else
			messageHandler.handle(this, intent);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		return super.onRecoverableError(context, errorId);
	}

	public static void generateNotification(Context context, int id,
			String message, Intent notificationIntent) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		String title = context.getString(R.string.app_name);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(id, notification);
	}

}
