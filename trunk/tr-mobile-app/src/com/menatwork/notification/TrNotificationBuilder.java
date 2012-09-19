package com.menatwork.notification;

import java.util.Date;

import android.content.Intent;

import com.menatwork.service.Defect;

public class TrNotificationBuilder {

	private TrNotificationType type;
	private String header;
	private String description;
	private Date date;
	private Intent intent;
	private String notificationId;

	public static TrNotificationBuilder newInstance() {
		return new TrNotificationBuilder();
	}

	protected TrNotificationBuilder() {
		this.header = "";
		this.description = "";
		this.date = new Date();
		this.notificationId = TrNotification.ID_NOT_SET;
	}

	// ************************************************ //
	// ====== Build ======
	// ************************************************ //

	public TrNotification build() {
		if (type == null)
			throw new Defect("type == null");

		return new TrNotification(type, header, description, date, intent,
				notificationId);
	}

	// ************************************************ //
	// ====== Setters ======
	// ************************************************ //

	public TrNotificationBuilder setType(final TrNotificationType type) {
		this.type = type;
		return this;
	}

	public TrNotificationBuilder setHeader(final String header) {
		this.header = header;
		return this;
	}

	public TrNotificationBuilder setDescription(final String description) {
		this.description = description;
		return this;
	}

	public TrNotificationBuilder setDate(final Date date) {
		this.date = date;
		return this;
	}

	public TrNotificationBuilder setIntent(final Intent intent) {
		this.intent = intent;
		return this;
	}

	public TrNotificationBuilder setNotificationId(final String id) {
		this.notificationId = id;
		return this;
	}

}
