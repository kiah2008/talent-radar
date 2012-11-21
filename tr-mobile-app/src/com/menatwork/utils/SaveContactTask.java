package com.menatwork.utils;

import android.content.Context;

import com.menatwork.model.User;

public class SaveContactTask extends SaveContactBaseTask<User> {

	public SaveContactTask(final Context context) {
		super(context);
	}

	@Override
	protected Void doInBackground(final User... params) {
		final User user = params[0];

		saveContact(user);

		return null;
	}

}
