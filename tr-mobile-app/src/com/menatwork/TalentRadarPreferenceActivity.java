package com.menatwork;

import android.os.Bundle;
import android.preference.PreferenceActivity;

// TODO - should rewire preferences changes with the app's listener - boris - 04/09/2012
public class TalentRadarPreferenceActivity extends PreferenceActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
