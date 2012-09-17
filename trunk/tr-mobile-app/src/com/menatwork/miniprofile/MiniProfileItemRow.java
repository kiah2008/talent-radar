package com.menatwork.miniprofile;

import com.menatwork.model.User;

public class MiniProfileItemRow {

	private final User user;

	public MiniProfileItemRow(final User user) {
		this.user = user;
	}

	public String getHeadline() {
		return user.getHeadline();
	}

	public String getUsername() {
		return user.getUsername();
	}

	public String getPicture() {
		return user.getProfilePictureUrl();
	}

	public String getUserId() {
		return user.getId();
	}

}
