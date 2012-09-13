package com.menatwork.preferences;

import java.util.Arrays;
import java.util.List;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.TalentRadarApplication;

public class SharedPreferencesChanges implements PreferencesChanges {

	private final List<String> keysThatChanged;

	public SharedPreferencesChanges(final String... keysThatChanged) {
		this.keysThatChanged = Arrays.asList(keysThatChanged);
	}

	@Override
	public boolean hasLocationSourceManagerPreferencesChanged() {
		return hasKeyChanged(R.string.preferences_actualization_duration_key)
				|| hasKeyChanged(R.string.preferences_actualization_frequency_key)
				|| hasKeyChanged(R.string.preferences_network_activation_key)
				|| hasKeyChanged(R.string.preferences_gps_activation_key);
	}

	private boolean hasKeyChanged(final int key) {
		return keysThatChanged.contains(getContext().getString(key));
	}

	private Context getContext() {
		return TalentRadarApplication.getContext();
	}
}
