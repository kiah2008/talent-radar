package com.menatwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;

import com.menatwork.model.User;
import com.menatwork.preferences.TalentRadarConfiguration;
import com.menatwork.service.Defect;

public class DispatcherActivity extends AbstractLoginActivity {

	private User localUser;

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		super.postCreate(savedInstanceState);
		startApplication();
	}

	private void startApplication() {
		// start the task that will asynchronously retrieve all existing skills
		// in the system
		new GetSkillsTask(getTalentRadarApplication()).execute();

		final TalentRadarConfiguration preferences = getTalentRadarApplication()
				.getPreferences();

		final String localUserId = preferences.getLocalUserId();
		if (notValidUserId(localUserId))
			startActivity(new Intent(this, LoginActivity.class));
		else {
			final DispatcherGetUserTask getUserTask = new DispatcherGetUserTask(
					this);
			getUserTask.execute(localUserId, localUserId);
		}
	}

	private void onTaskExecuted() {
		if (localUser == null)
			startActivity(new Intent(this, LoginActivity.class));
		else
			// this actually should have been an "onSuccessfulLogin", so, here
			// it goes
			finishSuccessfulLogin(this, localUser, null);
	}

	private boolean notValidUserId(final String localUserId) {
		return User.EMPTY_USER_ID.equals(localUserId);
	}

	// ************************************************ //
	// ====== DispatcherGetUserTask ======
	// ************************************************ //

	private class DispatcherGetUserTask extends GetUserTask {

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(activity, "",
					activity.getString(R.string.dispatcher_loading_message));
			progressDialog.getWindow().setGravity(Gravity.BOTTOM);
		}

		public DispatcherGetUserTask(final TalentRadarActivity activity) {
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
		return hasExpirationSaved() || hasExpirationDatePassed();
	}

	private boolean hasExpirationSaved() {
		return getPreferences().isApplicationExpired();
	}

	private boolean hasExpirationDatePassed() {
		return System.currentTimeMillis() >= expirationTimeMillis();
	}

	private long expirationTimeMillis() {
		try {
			final Date expirationDate = new SimpleDateFormat("dd/MM/yyyy")
					.parse("21/09/2012");
			return expirationDate.getTime();

		} catch (final ParseException e) {
			throw new Defect("invalid date format");
		}
	}

	@Override
	protected void setupButtons() {
	}

	@Override
	protected void findViewElements() {
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.dispatcher;
	}

	@Override
	/**
	 * Prevent orientation changes
	 */
	public void onConfigurationChanged(final Configuration newConfig) {
		// newConfig.orientation = Configuration.ORIENTATION_PORTRAIT;
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
