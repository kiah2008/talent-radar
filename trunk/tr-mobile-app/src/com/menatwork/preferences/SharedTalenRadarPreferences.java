package com.menatwork.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.menatwork.R;

public class SharedTalenRadarPreferences implements TalentRadarPreferences,
		OnSharedPreferenceChangeListener {

	private static final String PING_MESSAGE = "ping_message";
	private static final String ACTUALIZATION_FREQUENCY_MILLISECONDS = "actualization_frequency_milliseconds";
	private static final String ACTUALIZATION_DURATION_MILLISECONDS = "actualization_duration_milliseconds";
	private static final String GPS_LOCATION_ACTIVATION = "gps_location_activation";
	private static final String NETWORK_LOCATION_ACTIVATION = "network_location_activation";

	private final SharedPreferences sharedPreferences;
	private final TalentRadarPreferencesListener[] listeners;

	private Editor editor;
	private final Context context;

	public SharedTalenRadarPreferences(
			final SharedPreferences sharedPreferences, //
			final Context context, //
			final TalentRadarPreferencesListener... listeners) {
		this.sharedPreferences = sharedPreferences;
		this.context = context;
		this.listeners = listeners;

		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
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

		notifyChanges();
		discardChanges();
	}

	private void notifyChanges() {
		for (final TalentRadarPreferencesListener listener : listeners)
			listener.onPreferencesChanged(this);
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
		return sharedPreferences.getBoolean(
				context.getString(R.string.preferences_network_activation_key),
				true);
	}

	@Override
	public boolean isGpsLocationActivation() {
		return sharedPreferences.getBoolean(
				context.getString(R.string.preferences_gps_activation_key),
				true);
	}

	@Override
	public long getActualizationDurationMilliseconds() {
		return getActualizationDurationSeconds() * 1000;
	}

	@Override
	public long getActualizationFrequencyMilliseconds() {
		return getActualizationFrequencySeconds() * 1000;
	}

	@Override
	public long getActualizationDurationSeconds() {
		return Long.valueOf(sharedPreferences.getString(context
				.getString(R.string.preferences_actualization_duration_key),
				"120"));
	}

	@Override
	public long getActualizationFrequencySeconds() {
		return Long.valueOf(sharedPreferences.getString(context
				.getString(R.string.preferences_actualization_frequency_key),
				"30"));
	}

	@Override
	public void setNetworkLocationActivation(final boolean checked) {
		editor.putBoolean(
				context.getString(R.string.preferences_network_activation_key),
				checked);
	}

	@Override
	public void setGpsLocationActivation(final boolean checked) {
		editor.putBoolean(
				context.getString(R.string.preferences_gps_activation_key),
				checked);
	}

	public void setActualizationFrequencyMilliseconds(final long milliseconds) {
		setActualizationFrequencyMilliseconds(milliseconds / 1000);
	}

	public void setActualizationDurationMilliseconds(final long milliseconds) {
		setActualizationDurationSeconds(milliseconds / 1000);
	}

	@Override
	public void setActualizationFrequencySeconds(final long seconds) {
		editor.putString(context
				.getString(R.string.preferences_actualization_frequency_key),
				String.valueOf(seconds));
	}

	@Override
	public void setActualizationDurationSeconds(final long seconds) {
		editor.putString(context
				.getString(R.string.preferences_actualization_duration_key),
				String.valueOf(seconds));
	}

	@Override
	public String getPingMessage() {
		return sharedPreferences.getString(
				context.getString(R.string.preferences_ping_message_key), //
				context.getString(R.string.default_ping_message));
	}

	@Override
	public void setPingMessage(final String pingMessage) {
		editor.putString(
				context.getString(R.string.preferences_ping_message_key),
				pingMessage);
	}

	// ************************************************ //
	// ====== OnSharedPreferenceChangeListener ======
	// ************************************************ //

	@Override
	public void onSharedPreferenceChanged(
			final SharedPreferences sharedPreferences, final String key) {
		notifyChanges();
	}

}
