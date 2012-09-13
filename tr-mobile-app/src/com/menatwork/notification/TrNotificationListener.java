package com.menatwork.notification;

import java.util.EventListener;

public interface TrNotificationListener extends EventListener {

	void onNewNotification(TrNotificationManager notificationManager, TrNotification notification);

}
