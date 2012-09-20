package com.menatwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.menatwork.model.User;
import com.menatwork.preferences.TalentRadarPreferences;

public class DispatcherActivity extends TalentRadarActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (hasExpired()) {
			startActivity(new Intent(this, ExpiredActivity.class));
		} else {
			startApplication();
		} // if hasExpired
	}

	private void startApplication() {
		final ProgressDialog progress = ProgressDialog.show(this,
				getString(R.string.generic_wait), "");

		try {
			final TalentRadarPreferences preferences = getTalentRadarApplication()
					.getPreferences();

			final String localUserId = preferences.getLocalUserId();
			User localUser = null;

			if (notValidUserId(localUserId)
					|| (localUser = getUserById(localUserId)) == null) {
				startActivity(new Intent(this, LoginActivity.class));
			} else {
				getTalentRadarApplication().loadLocalUser(localUser);
				startActivity(new Intent(this, MainActivity.class));
			}
		} finally {
			progress.dismiss();
		}
	}

	private boolean notValidUserId(final String localUserId) {
		return User.EMPTY_USER_ID.equals(localUserId);
	}

	// ************************************************ //
	// ====== Trial mode ======
	// ************************************************ //

	private boolean hasExpired() {
		return System.currentTimeMillis() >= expirationTimeMillis();
	}

	private long expirationTimeMillis() {
		try {
			final Date expirationDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse("21/09/2012");
			return expirationDate.getTime();
		} catch (final ParseException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
