package com.menatwork.preferences;

import java.util.Arrays;
import java.util.List;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.TalentRadarApplication;

public class SharedConfigurationChanges implements ConfigurationChanges {

	private final List<String> keysThatChanged;

	public SharedConfigurationChanges(final String... keysThatChanged) {
		this.keysThatChanged = Arrays.asList(keysThatChanged);
	}

	// TODO - what if i changed this List<String> to a List<Integer (Ids)> and
	// avoid using the context - miguel - 22/09/2012

	@Override
	public boolean hasLocationSourceManagerConfigurationChanged() {
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
