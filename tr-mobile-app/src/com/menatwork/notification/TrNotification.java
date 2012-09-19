package com.menatwork.notification;

import java.util.Date;

import android.content.Intent;

public class TrNotification {

	public static final String ID_NOT_SET = "ID_NOT_SET";

	private final TrNotificationType type;

	private final String header;
	private final String description;
	private final Date date;
	private final Intent intent;
	private String notificationId;

	public TrNotification(final TrNotificationType type, final String header,
			final String description, final Date date, final Intent intent,
			final String notificationId) {
		this.type = type;
		this.header = header;
		this.description = description;
		this.date = date;
		this.intent = intent;
		this.notificationId = notificationId;
	}

	public TrNotificationType getType() {
		return type;
	}

	public int getIcon() {
		return type.getIcon();
	}

	public String getHeader() {
		return header;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public Intent getIntent() {
		return intent;
	}

	public String getNotificationId() {
		if (ID_NOT_SET.equals(notificationId))
			notificationId = String.valueOf(this.hashCode());
		return notificationId;
	}

}
