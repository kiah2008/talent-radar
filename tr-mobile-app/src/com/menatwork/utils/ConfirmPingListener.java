package com.menatwork.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;

import com.menatwork.R;
import com.menatwork.TalentRadarApplication;
import com.menatwork.miniprofile.PingTask;
import com.menatwork.model.User;

public class ConfirmPingListener implements OnClickListener {
	private final Activity activity;
	private User user;
	private String id;
	private String shortName;

	public ConfirmPingListener(final Activity activity, final User user) {
		super();
		this.activity = activity;
		this.user = user;
	}

	public ConfirmPingListener(final Activity activity, final String id,
			final String shortName) {
		super();
		this.activity = activity;
		this.id = id;
		this.shortName = shortName;
	}

	@Override
	public void onClick(final View v) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		final String displayableShortName;
		final String userid;

		if (user != null) {
			displayableShortName = user.getDisplayableShortName();
			userid = user.getId();
		} else {
			displayableShortName = this.shortName;
			userid = this.id;
		}
		builder.setMessage(String.format(
				activity.getString(R.string.ping_confirmation_question),
				displayableShortName));
		builder.setPositiveButton(R.string.accept_option_label,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						final String localUserId = TalentRadarApplication
								.getContext().getLocalUser().getId();
						new PingTask(activity).execute(
						//
								localUserId, //
								userid, //
								displayableShortName);
					}
				});
		builder.setNegativeButton(R.string.cancel_option_label,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog,
							final int id) {
						// Do nothing
					}
				});
		builder.create().show();
	}
}