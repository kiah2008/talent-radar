package com.menatwork.preferences;

import android.content.SharedPreferences;

public class SharedTalenRadarPreferences implements TalentRadarPreferences {

	private final SharedPreferences sharedPreferences;

	public SharedTalenRadarPreferences(final SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

	@Override
	public boolean getNetworkLocationActivation() {
		return sharedPreferences
				.getBoolean("network_location_activation", true);
	}

	@Override
	public boolean getGpsLocationActivation() {
		return sharedPreferences.getBoolean("gps_location_activation", true);
	}

	@Override
	public long getActualizationDurationMilliseconds() {
		return sharedPreferences.getLong("actualization_duration_milliseconds",
				120000);
	}

	@Override
	public long getActualizationFrequencyMilliseconds() {
		return sharedPreferences.getLong(
				"actualization_frequency_milliseconds", 30000);
	}

	@Override
	public long getActualizationDurationSeconds() {
		return getActualizationDurationMilliseconds() / 1000;
	}

	@Override
	public long getActualizationFrequencySeconds() {
		return getActualizationFrequencyMilliseconds() / 1000;
	}

}
