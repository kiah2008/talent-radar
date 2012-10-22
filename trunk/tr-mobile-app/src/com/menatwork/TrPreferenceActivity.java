package com.menatwork;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.menatwork.model.PrivacySettings;
import com.menatwork.service.SavePrivacySettings;
import com.menatwork.service.response.Response;

public class TrPreferenceActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	private Map<String, Object> initialPrivacySettings;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		enableOrDisableNicknameEditing();
		PreferenceManager.getDefaultSharedPreferences(getTalentRadarApp())
				.registerOnSharedPreferenceChangeListener(this);
		initialPrivacySettings = getCurrentPrivacySettingsAsMap();
	}

	private TalentRadarApplication getTalentRadarApp() {
		final TalentRadarApplication trapp = (TalentRadarApplication) getApplication();
		/* it's a TRAPP! */
		return trapp;
	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			final Map<String, Object> currentPrivacySettingsAsMap = getCurrentPrivacySettingsAsMap();
			if (!initialPrivacySettings.equals(currentPrivacySettingsAsMap)) {
				savePreferences(currentPrivacySettingsAsMap);
				return true; // handled event
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private Map<String, Object> getCurrentPrivacySettingsAsMap() {
		return getTalentRadarApp().getPrivacySettings().asMap();
	}

	@SuppressWarnings("unchecked")
	private void savePreferences(
			final Map<String, Object> currentPrivacySettingsAsMap) {
		new SavePreferencesAsyncTask().execute(currentPrivacySettingsAsMap);
	}

	private class SavePreferencesAsyncTask extends
			AsyncTask<Map<String, Object>, Void, Response> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(TrPreferenceActivity.this, "",
					getString(R.string.generic_wait));
		}

		@Override
		protected Response doInBackground(final Map<String, Object>... params) {
			try {
				final PrivacySettings privacySettings = getTalentRadarApp()
						.getPrivacySettings();
				final String localUserId = getTalentRadarApp().getLocalUserId();
				final Boolean isStealthy = privacySettings.isStealthy();
				final Boolean isSkillsPublic = privacySettings.isSkillsPublic();
				final Boolean isHeadlinePublic = privacySettings
						.isHeadlinePublic();
				final String nickname = privacySettings.getNickname();
				final Boolean isNamePublic = privacySettings.isNamePublic();
				final Boolean isPicturePublic = privacySettings.isPicturePublic();
				final SavePrivacySettings savePrivacySettings = SavePrivacySettings
						.newInstance(TrPreferenceActivity.this, localUserId,
								isNamePublic, nickname, isHeadlinePublic,
								isSkillsPublic, isPicturePublic, isStealthy);
				return savePrivacySettings.execute();
			} catch (final JSONException e) {
				Log.e("SavePreferencesAsyncTask",
						"Error parsing JSON response", e);
				throw new RuntimeException(e);
			} catch (final IOException e) {
				Log.e("SavePreferencesAsyncTask",
						"IO error trying to register device", e);
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(final Response result) {
			progressDialog.dismiss();
			if (!result.isSuccessful())
				Toast.makeText(TrPreferenceActivity.this,
						R.string.generic_error, Toast.LENGTH_LONG).show();
			TrPreferenceActivity.this.finish();
		}

	}

	@Override
	public void onSharedPreferenceChanged(final SharedPreferences prefs,
			final String preferenceKey) {
		final String isNamePublicPreferenceKey = getString(R.string.privacy_name_public_key);

		if (isNamePublicPreferenceKey.equals(preferenceKey))
			enableOrDisableNicknameEditing();

	}

	protected void enableOrDisableNicknameEditing() {
		final Preference nicknamePreferenceControl = getPreferenceScreen()
				.findPreference(getString(R.string.privacy_nickname_key));
		nicknamePreferenceControl.setEnabled(!getTalentRadarApp()
				.getPrivacySettings().isNamePublic());
	}

}
