package com.menatwork.notification;

import com.menatwork.R;

public enum TrNotificationType {
	PING(R.drawable.poke), //
	ADDED_TO_SEARCH(R.drawable.capture);

	private final int iconId;

	private TrNotificationType(final int iconId) {
		this.iconId = iconId;
	}

	public int getIcon() {
		return iconId;
	}

}
