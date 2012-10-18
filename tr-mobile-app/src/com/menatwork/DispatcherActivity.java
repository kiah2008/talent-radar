package com.menatwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.menatwork.model.User;
import com.menatwork.preferences.TalentRadarConfiguration;

public class DispatcherActivity extends TalentRadarActivity {

	private User localUser;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dispatcher);

		// EXPIRATION SNIPPET [in case we need it one more time]
		// if (hasExpired()) {
		// setExpired();
		// startActivity(new Intent(this, ExpiredActivity.class));
		// } else
		startApplication();
	}

	private void startApplication() {
		// final ProgressDialog progress = ProgressDialog.show(this,
		// getString(R.string.generic_wait), "");

		final TalentRadarConfiguration preferences = getTalentRadarApplication()
				.getPreferences();

		final String localUserId = preferences.getLocalUserId();
		if (notValidUserId(localUserId))
			startActivity(new Intent(this, LoginActivity.class));
		else {
			final DispatcherGetUserTask getUserTask = new DispatcherGetUserTask(
					this);
			getUserTask.execute(localUserId);
		}
	}

	private void onTaskExecuted() {
		if (localUser == null)
			startActivity(new Intent(this, LoginActivity.class));
		else {
			getTalentRadarApplication().loadLocalUser(localUser);
			startActivity(new Intent(this, MainActivity.class));
		}
	}

	private boolean notValidUserId(final String localUserId) {
		return User.EMPTY_USER_ID.equals(localUserId);
	}

	// ************************************************ //
	// ====== DispatcherGetUserTask ======
	// ************************************************ //

	private class DispatcherGetUserTask extends GetUserTask {

		public DispatcherGetUserTask(final Activity activity) {
			super(activity);
		}

		@Override
		protected void onPostExecute(final User result) {
			super.onPostExecute(result);
			localUser = result;
			onTaskExecuted();
		}

	}

	// ************************************************ //
	// ====== Trial mode ======
	// ************************************************ //

	@SuppressWarnings("unused")
	private void setExpired() {
		final TalentRadarConfiguration preferences = getPreferences();
		preferences.beginNewEdition();
		preferences.setApplicationExpired(true);
		preferences.commitChanges();
	}

	@SuppressWarnings("unused")
	private boolean hasExpired() {
		return expirationSaved() || expirationDatePassed();
	}

	private boolean expirationSaved() {
		return getPreferences().isApplicationExpired();
	}

	private boolean expirationDatePassed() {
		return System.currentTimeMillis() >= expirationTimeMillis();
	}

	private long expirationTimeMillis() {
		try {
			final Date expirationDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse("21/09/2012");
			return expirationDate.getTime();

		} catch (final ParseException e) {
			throw new RuntimeException("invalid date format");
		}
	}

}
