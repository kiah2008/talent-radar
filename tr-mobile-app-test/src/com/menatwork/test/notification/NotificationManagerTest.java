package com.menatwork.test.notification;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.menatwork.notification.TrNotification;
import com.menatwork.notification.TrNotificationBuilder;
import com.menatwork.notification.TrNotificationListener;
import com.menatwork.notification.TrNotificationManager;
import com.menatwork.notification.TrNotificationType;

public class NotificationManagerTest extends TestCase {

	private final TrNotificationManager notificationManager = TrNotificationManager
			.newInstance();
	private final Mockery context = new Mockery();

	private final TrNotification newNotification = TrNotificationBuilder
			.newInstance().setType(TrNotificationType.PING).build();

	public void testReceivesNotificationAndNotifiesListeners() throws Exception {
		final TrNotificationListener listener = context
				.mock(TrNotificationListener.class);

		context.checking(new Expectations() {
			{
				oneOf(listener).onNewNotification( //
						notificationManager, //
						newNotification);
			}
		});

		notificationManager.addNotificationListener(listener);

		notificationManager.newNotification(newNotification);

		context.assertIsSatisfied();
	}

	public void testReceivesNotificationAndDoesntNotifyRemovedListeners()
			throws Exception {
		final TrNotificationListener listener = context
				.mock(TrNotificationListener.class);

		context.checking(new Expectations() {
			{
				never(listener).onNewNotification( //
						notificationManager, //
						newNotification);
			}
		});

		notificationManager.addNotificationListener(listener);
		notificationManager.removeNotificationListener(listener);

		notificationManager.newNotification(newNotification);

		context.assertIsSatisfied();
	}
}
