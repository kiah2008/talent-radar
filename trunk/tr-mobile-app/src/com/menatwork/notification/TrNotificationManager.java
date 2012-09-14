package com.menatwork.notification;

import java.util.LinkedList;
import java.util.List;

public class TrNotificationManager {

	private final List<TrNotification> notifications;
	private final List<TrNotificationListener> listeners;

	public TrNotificationManager() {
		this.notifications = new LinkedList<TrNotification>();
		this.listeners = new LinkedList<TrNotificationListener>();
	}

	public void newNotification(final TrNotification notification) {
		notifications.add(notification);
		notifyAdded(notification);
	}

	public List<TrNotification> getNotifications() {
		return notifications;
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
