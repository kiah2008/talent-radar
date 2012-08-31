package com.menatwork.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.menatwork.R;
import com.menatwork.TalentRadarApplication;

public class SharedTalenRadarPreferences implements TalentRadarPreferences {

	private static final String PING_MESSAGE = "ping_message";
	private static final String ACTUALIZATION_FREQUENCY_MILLISECONDS = "actualization_frequency_milliseconds";
	private static final String ACTUALIZATION_DURATION_MILLISECONDS = "actualization_duration_milliseconds";
	private static final String GPS_LOCATION_ACTIVATION = "gps_location_activation";
	private static final String NETWORK_LOCATION_ACTIVATION = "network_location_activation";

	private final SharedPreferences sharedPreferences;
	private final TalentRadarPreferencesListener[] listeners;

	private Editor editor;

	public SharedTalenRadarPreferences(final SharedPreferences sharedPreferences,
			final TalentRadarPreferencesListener... listeners) {
		this.sharedPreferences = sharedPreferences;
		this.listeners = listeners;
	}

	// ************************************************ //
	// ====== Edition Session ======
	// ************************************************ //

	@Override
	public void setNewEdition() {
		editor = sharedPreferences.edit();
	}

	@Override
	public void commitChanges() {
		final boolean commitSuccessfull = editor.commit();

		if (!commitSuccessfull)
			throw new RuntimeException("could not save changes to preferences");

		for (final TalentRadarPreferencesListener listener : listeners)
			listener.onPreferencesChanged(this);

		discardChanges();
	}

	@Override
	public void discardChanges() {
		editor = null;
	}

	// ************************************************ //
	// ====== Config Getters + Setters ======
	// ************************************************ //

	@Override
	public boolean isNetworkLocationActivation() {
		return sharedPreferences.getBoolean(NETWORK_LOCATION_ACTIVATION, true);
	}

	@Override
	public boolean isGpsLocationActivation() {
		return sharedPreferences.getBoolean(GPS_LOCATION_ACTIVATION, true);
	}

	@Override
	public long getActualizationDurationMilliseconds() {
		return sharedPreferences.getLong(ACTUALIZATION_DURATION_MILLISECONDS, 120000);
	}

	@Override
	public long getActualizationFrequencyMilliseconds() {
		return sharedPreferences.getLong(ACTUALIZATION_FREQUENCY_MILLISECONDS, 30000);
	}

	@Override
	public long getActualizationDurationSeconds() {
		return getActualizationDurationMilliseconds() / 1000;
	}

	@Override
	public long getActualizationFrequencySeconds() {
		return getActualizationFrequencyMilliseconds() / 1000;
	}

	@Override
	public void setNetworkLocationActivation(final boolean checked) {
		editor.putBoolean(NETWORK_LOCATION_ACTIVATION, checked);
	}

	@Override
	public void setGpsLocationActivation(final boolean checked) {
		editor.putBoolean(GPS_LOCATION_ACTIVATION, checked);
	}

	public void setActualizationFrequencyMilliseconds(final long milliseconds) {
		editor.putLong(ACTUALIZATION_FREQUENCY_MILLISECONDS, milliseconds);
	}

	public void setActualizationDurationMilliseconds(final long milliseconds) {
		editor.putLong(ACTUALIZATION_DURATION_MILLISECONDS, milliseconds);
	}

	@Override
	public void setActualizationFrequencySeconds(final long seconds) {
		setActualizationFrequencyMilliseconds(seconds * 1000);
	}

	@Override
	public void setActualizationDurationSeconds(final long seconds) {
		setActualizationDurationMilliseconds(seconds * 1000);
	}

	// TODO - Not sure if this is the best option, but it was the easiest one (:
	// - boris - 31/08/2012
	@Override
	public String getPingMessage() {
		return sharedPreferences.getString(PING_MESSAGE,
				TalentRadarApplication.getContext().getString(R.string.default_ping_message));
	}

	@Override
	public void setPingMessage(final String pingMessage) {
		editor.putString(PING_MESSAGE, pingMessage);
	}

}
