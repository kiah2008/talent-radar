package com.menatwork.test.notification;

import android.test.AndroidTestCase;

import com.menatwork.notification.TrNotification;
import com.menatwork.notification.TrNotificationBuilder;
import com.menatwork.notification.TrNotificationListener;
import com.menatwork.notification.TrNotificationManager;
import com.menatwork.notification.TrNotificationType;

public class NotificationManagerTest extends AndroidTestCase {

	private final TrNotificationManager notificationManager = new TrNotificationManager();

	public void testReceivesNotificationAndNotifiesListeners() throws Exception {
		notificationManager.addNotificationListener(new TrNotificationListener() {

			@Override
			public void onNewNotification(final TrNotificationManager notificationManager,
					final TrNotification notification) {
				// TODO - hacer con jmock - boris - 11/09/2012
			}
		});

		notificationManager.newNotification( //
				new TrNotificationBuilder().setType(TrNotificationType.PING).build());
	}
}
