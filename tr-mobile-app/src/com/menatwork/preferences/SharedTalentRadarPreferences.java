package com.menatwork.preferences;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.menatwork.R;
import com.menatwork.model.User;

public class SharedTalentRadarPreferences implements TalentRadarPreferences,
		OnSharedPreferenceChangeListener {

	private final SharedPreferences sharedPreferences;
	private final TalentRadarPreferencesListener[] listeners;

	private Editor editor;
	private Set<String> keysChanged;

	private final Context context;

	public SharedTalentRadarPreferences(
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
	public void beginNewEdition() {
		editor = sharedPreferences.edit();
		keysChanged = new HashSet<String>();
	}

	@Override
	public void commitChanges() {
		final boolean commitSuccessful = editor.commit();

		if (!commitSuccessful)
			throw new RuntimeException("could not save changes to preferences");

		notifyChanges(keysChanged.toArray(new String[0]));
		discardChanges();
	}

	private void notifyChanges(final String... keys) {
		for (final TalentRadarPreferencesListener listener : listeners)
			listener.onPreferencesChanged(new SharedPreferencesChanges(keys),
					this);
	}

	@Override
	public void discardChanges() {
		editor = null;
		keysChanged = null;
	}

	// ************************************************ //
	// ====== Config Getters + Setters ======
	// ************************************************ //

	@Override
	public boolean isNetworkLocationActivation() {
		return sharedPreferences
				.getBoolean(
						context.getString(R.string.preferences_network_activation_key), //
						Boolean.parseBoolean(context
								.getString(R.string.preferences_network_activation_default_value)));
	}

	@Override
	public boolean isGpsLocationActivation() {
		return sharedPreferences
				.getBoolean(
						context.getString(R.string.preferences_gps_activation_key),
						Boolean.parseBoolean(context
								.getString(R.string.preferences_gps_activation_default_value)));
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
		return sharedPreferences
				.getLong(
						context.getString(R.string.preferences_actualization_duration_key),
						Long.valueOf(context
								.getString(R.string.preferences_actualization_duration_default_value)));
	}

	@Override
	public long getActualizationFrequencySeconds() {
		return sharedPreferences
				.getLong(
						context.getString(R.string.preferences_actualization_frequency_key),
						Long.valueOf(context
								.getString(R.string.preferences_actualization_frequency_default_value)));
	}

	@Override
	public void setNetworkLocationActivation(final boolean checked) {
		final String key = context
				.getString(R.string.preferences_network_activation_key);
		keysChanged.add(key);
		editor.putBoolean(key, checked);
	}

	@Override
	public void setGpsLocationActivation(final boolean checked) {
		final String key = context
				.getString(R.string.preferences_gps_activation_key);
		keysChanged.add(key);
		editor.putBoolean(key, checked);
	}

	public void setActualizationFrequencyMilliseconds(final long milliseconds) {
		setActualizationFrequencySeconds(milliseconds / 1000);
	}

	public void setActualizationDurationMilliseconds(final long milliseconds) {
		setActualizationDurationSeconds(milliseconds / 1000);
	}

	@Override
	public void setActualizationFrequencySeconds(final long seconds) {
		final String key = context
				.getString(R.string.preferences_actualization_frequency_key);
		keysChanged.add(key);
		editor.putLong(key, seconds);
	}

	@Override
	public void setActualizationDurationSeconds(final long seconds) {
		final String key = context
				.getString(R.string.preferences_actualization_duration_key);
		keysChanged.add(key);
		editor.putLong(key, seconds);
	}

	@Override
	public String getPingMessage() {
		return sharedPreferences
				.getString(
						//
						context.getString(R.string.preferences_ping_message_key), //
						context.getString(R.string.preferences_ping_message_default_value));
	}

	@Override
	public void setPingMessage(final String pingMessage) {
		editor.putString( //
				context.getString(R.string.preferences_ping_message_key), //
				pingMessage);
	}

	// ************************************************ //
	// ====== OnSharedPreferenceChangeListener ======
	// ************************************************ //

	@Override
	public void onSharedPreferenceChanged(
			final SharedPreferences sharedPreferences, final String key) {
		notifyChanges(key);
	}

	// ************************************************ //
	// ========= Local user persistent data =========
	// ************************************************ //

	@Override
	public String getLocalUserId() {
		return sharedPreferences.getString(
				context.getString(R.string.preferences_user_id_key),
				User.EMPTY_USER_ID);
	}

	@Override
	public void setLocalUserId(final String id) {
		final String key = context.getString(R.string.preferences_user_id_key);
		keysChanged.add(key);
		editor.putString(key, id);
	}

}
