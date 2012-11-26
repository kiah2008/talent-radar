package com.menatwork.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class UserConfirmationPrompter {

	private Object confirmationLock;
	private boolean confirmed;
	private final CharSequence message;
	private final Context activity;
	private final String title;

	public UserConfirmationPrompter(final Activity activity,
			final String title, final String message) {
		this.activity = activity;
		this.title = title;
		this.message = message;
	}

	public static boolean prompt(final Activity activity, final String title,
			final String message) {
		final UserConfirmationPrompter userConfirmationPrompter = new UserConfirmationPrompter(
				activity, title, message);
		return userConfirmationPrompter.prompt();
	}

	private boolean prompt() {
		final Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(title);
		builder.setMessage(message);
		// TODO strings.xml
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				UserConfirmationPrompter.this.userCancelled();
			}
		});
		// TODO strings.xml
		builder.setPositiveButton("S’", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dialog, final int which) {
				UserConfirmationPrompter.this.userConfirmed();
			}
		});

		builder.create().show();

		synchronized (confirmationLock) {
			try {
				confirmationLock.wait();
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		return confirmed;
	}

	protected void userConfirmed() {
		confirmed = true;
		confirmationLock.notify();
	}

	protected void userCancelled() {
		confirmed = false;
		confirmationLock.notify();
	}

}
