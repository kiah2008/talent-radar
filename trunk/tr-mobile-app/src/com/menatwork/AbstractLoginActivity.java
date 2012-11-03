package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.menatwork.model.User;
import com.menatwork.service.GetSkills;
import com.menatwork.service.SaveDeviceId;
import com.menatwork.service.response.SaveDeviceIdResponse;
import com.menatwork.service.response.SkillsResponse;
import com.menatwork.utils.LogUtils;

public abstract class AbstractLoginActivity extends GuiTalentRadarActivity {
	static final int LINKED_IN_REQUEST_CODE = 0303456;

	void finishSuccessfulLogin(final Activity caller, final User user,
			final ProgressDialog progressDialog) {
		if (isRunningOnEmulator())
			doFinishSuccesfulLogin(caller, user, progressDialog);
		else
			new SaveDeviceIdTask() {
				@Override
				protected void onPostExecute(final SaveDeviceIdResponse result) {
					doFinishSuccesfulLogin(caller, user, progressDialog);
				}

			}.execute(user.getId());
	}

	void doFinishSuccesfulLogin(final Activity caller, final User user,
			final ProgressDialog progressDialog) {
		getTalentRadarApplication().loadLocalUser(user);
		if (progressDialog != null)
			progressDialog.dismiss();

		// start the task that will asynchronously retrieve all existing skills
		// in the system
		new GetSkillsTask(getTalentRadarApplication()).execute();

		final Intent intent = new Intent(caller, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

		// finiquitate some activities so that they are not hanging around
		// NOTE: Android seems to have some mechanisms to avoid doing this
		// manually, but they're REALLY buggy at the time, so let's be honest to
		// ourselves
		finishActivity(LINKED_IN_REQUEST_CODE);
		finish();
	}

	private class SaveDeviceIdTask extends
			AsyncTask<String, Void, SaveDeviceIdResponse> {

		@Override
		protected SaveDeviceIdResponse doInBackground(final String... params) {
			final String userId = params[0];

			try {
				final TalentRadarApplication talentRadarApplication = getTalentRadarApplication();
				if (!talentRadarApplication.isDeviceRegistered())
					talentRadarApplication.registerDevice();
				final String deviceId = talentRadarApplication
						.getDeviceRegistrationId();
				final SaveDeviceId saveDeviceId = SaveDeviceId.newInstance(
						AbstractLoginActivity.this, userId, deviceId);
				SaveDeviceIdResponse saveDeviceIdResponse;
				saveDeviceIdResponse = saveDeviceId.execute();
				LogUtils.d(AbstractLoginActivity.this,
						"GCM Registration response", saveDeviceIdResponse);
				return saveDeviceIdResponse;
			} catch (final JSONException e) {
				Log.e("SaveDeviceIdTask", "Error parsing JSON response", e);
				throw new RuntimeException(e);
			} catch (final IOException e) {
				Log.e("SaveDeviceIdTask", "IO error trying to register device",
						e);
				throw new RuntimeException(e);
			}
		}

	}

	private class GetSkillsTask extends AsyncTask<Void, Void, SkillsResponse> {

		private final TalentRadarApplication trApplication;

		private GetSkillsTask(final TalentRadarApplication trApplication) {
			super();
			this.trApplication = trApplication;
		}

		@Override
		protected SkillsResponse doInBackground(final Void... arg0) {
			try {
				final GetSkills getSkills = GetSkills
						.newInstance(AbstractLoginActivity.this);
				SkillsResponse skillsResponse;
				skillsResponse = getSkills.execute();
				return skillsResponse;
			} catch (final JSONException e) {
				Log.e("GetSkillsTask", "Error parsing JSON response", e);
				throw new RuntimeException(e);
			} catch (final IOException e) {
				Log.e("GetSkillsTask", "IO error trying to get system skills",
						e);
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(final SkillsResponse result) {
			if (result.isSuccessful())
				trApplication.loadSkills(result.getSkills());
		}
	}

}
