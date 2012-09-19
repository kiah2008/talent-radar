package com.menatwork.notification;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TrNotificationManager {

	private final Map<String, TrNotification> notifications;
	private final List<TrNotificationListener> listeners;

	public static TrNotificationManager newInstance() {
		return new TrNotificationManager();
	}

	protected TrNotificationManager() {
		this.notifications = new HashMap<String, TrNotification>();
		this.listeners = new LinkedList<TrNotificationListener>();
	}

	public void newNotification(final TrNotification notification) {
		notifications.put(notification.getNotificationId(), notification);
		notifyAdded(notification);
	}

	public Collection<TrNotification> getNotifications() {
		return notifications.values();
	}

	// ************************************************ //
	// ====== Listeners ======
	// ************************************************ //

	private void notifyAdded(final TrNotification notification) {
		for (final TrNotificationListener listener : listeners)
			listener.onNewNotification(this, notification);
	}

	public void addNotificationListener(final TrNotificationListener listener) {
		listeners.add(listener);
	}

	public void removeNotificationListener(final TrNotificationListener listener) {
		listeners.remove(listener);
	}

}
