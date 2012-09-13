package com.menatwork.test.notification;

import org.jmock.Expectations;
import org.jmock.Mockery;

import android.test.AndroidTestCase;

import com.menatwork.notification.TrNotification;
import com.menatwork.notification.TrNotificationBuilder;
import com.menatwork.notification.TrNotificationListener;
import com.menatwork.notification.TrNotificationManager;
import com.menatwork.notification.TrNotificationType;

// TODO - test not working, find out why - miguel - 13/09/2012
public class NotificationManagerTest extends AndroidTestCase {

	private final TrNotificationManager notificationManager = new TrNotificationManager();
	private final Mockery context = new Mockery();

	public void testReceivesNotificationAndNotifiesListeners() throws Exception {
		final TrNotificationListener listener = context
				.mock(TrNotificationListener.class);

		final TrNotification newNotification = new TrNotificationBuilder()
				.setType(TrNotificationType.PING).build();

		context.checking(new Expectations() {
			{
				oneOf(listener).onNewNotification( //
						with(notificationManager), //
						with(any(TrNotification.class)));
			}
		});

		notificationManager.addNotificationListener(listener);

		notificationManager.newNotification( //
				newNotification);
	}
}
