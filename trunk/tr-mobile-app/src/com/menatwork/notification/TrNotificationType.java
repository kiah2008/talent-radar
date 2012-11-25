package com.menatwork.notification;

import com.menatwork.R;

public enum TrNotificationType {
	PING(R.drawable.poke, 1), //
	ADDED_TO_SEARCH(R.drawable.new_hunt_icon2, 2), //
	CHAT(R.drawable.chat, 3);

	private final int iconId, typeId;

	private TrNotificationType(final int iconId, final int typeId) {
		this.iconId = iconId;
		this.typeId = typeId;
	}

	public int getIcon() {
		return iconId;
	}

	public int getTypeId() {
		return typeId;
	}

}
