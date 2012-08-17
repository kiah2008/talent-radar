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
		return user.getUsername() + " (" + 23 /* TODO user.getAge() */+ ")";
	}

	// TODO - Some kind of image format - boris - 16/08/2012
	public Void getPicture() {
		return null;
	}

}
