package com.menatwork.notification;

import java.util.Date;

public class TrNotification {

	private final TrNotificationType type;

	private final String header;
	private final String description;
	private final Date date;

	public TrNotification(final TrNotificationType type, final String header,
			final String description, final Date date) {
		this.type = type;
		this.header = header;
		this.description = description;
		this.date = date;
	}

	public TrNotificationType getType() {
		return type;
	}

	public Void getIcon() {
		// FIXME - right, ehhrr, fix this, should return an icon, not null -
		// miguel - 13/09/2012
		// return type.getIcon();
		return null;
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
}
