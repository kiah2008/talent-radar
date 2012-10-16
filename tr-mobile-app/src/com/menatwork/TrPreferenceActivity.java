package com.menatwork;

import java.util.Map;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.KeyEvent;
import android.widget.Toast;

public class TrPreferenceActivity extends PreferenceActivity {

	private Map<String, Object> initialPrivacySettings;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		initialPrivacySettings = getCurrentPrivacySettingsAsMap();
	}

	private TalentRadarApplication getTalentRadarApp() {
		final TalentRadarApplication trapp = (TalentRadarApplication) getApplication();
		return trapp;
	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			if (!initialPrivacySettings
					.equals(getCurrentPrivacySettingsAsMap()))
				savePreferences();
		return super.onKeyDown(keyCode, event);
	}

	private Map<String, Object> getCurrentPrivacySettingsAsMap() {
		return getTalentRadarApp().getPrivacySettings().asMap();
	}

	private void savePreferences() {
		Toast.makeText(this, "Saving preferences", Toast.LENGTH_LONG).show();
		try {
			Thread.sleep(2500);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
