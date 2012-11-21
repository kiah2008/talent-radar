package com.menatwork.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.menatwork.R;
import com.menatwork.TransactionalSharedPreferencesEditor;
import com.menatwork.model.User;

public class SharedTalentRadarConfiguration //
		extends TransactionalSharedPreferencesEditor //
		implements TalentRadarConfiguration, OnSharedPreferenceChangeListener {

	private final TalentRadarConfigurationListener[] listeners;

	private final Context context;

	public SharedTalentRadarConfiguration(
			final SharedPreferences sharedPreferences, //
			final Context context, //
			final TalentRadarConfigurationListener... listeners) {
		super(sharedPreferences);
		this.context = context;
		this.listeners = listeners;

		sharedPreferences.registerOnSharedPreferenceChangeListener(this);
	}

	// ************************************************ //
	// ====== Edition Session ======
	// ************************************************ //

	@Override
	protected void notifyChanges(final String... keys) {
		for (final TalentRadarConfigurationListener listener : listeners)
			listener.onConfigurationChanged(
					new SharedConfigurationChanges(keys), this);
	}

	// ************************************************ //
	// ====== Config Getters + Setters ======
	// ************************************************ //

	@Override
	public boolean isVibrationEnabledOnHunts() {
		// TODO - implement as preference?
		return true;
	}

	@Override
	public boolean isVibrationEnabledOnPings() {
		// TODO - implement as preference?
		return true;
	}

	@Override
	public boolean isVibrationEnabledOnMessages() {
		// TODO - implement as preference?
		return false;
	}

	@Override
	public boolean isNetworkLocationActivation() {
		return getBoolean(
				context.getString(R.string.preferences_network_activation_key), //
				Boolean.parseBoolean(context
						.getString(R.string.preferences_network_activation_default_value)));
	}

	@Override
	public boolean isGpsLocationActivation() {
		return getBoolean(
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
		return getLong(
				context.getString(R.string.preferences_actualization_duration_key),
				Long.valueOf(context
						.getString(R.string.preferences_actualization_duration_default_value)));
	}

	@Override
	public long getActualizationFrequencySeconds() {
		return getLong(
				context.getString(R.string.preferences_actualization_frequency_key),
				Long.valueOf(context
						.getString(R.string.preferences_actualization_frequency_default_value)));
	}

	@Override
	public void setNetworkLocationActivation(final boolean checked) {
		final String key = context
				.getString(R.string.preferences_network_activation_key);
		putBoolean(key, checked);
	}

	@Override
	public void setGpsLocationActivation(final boolean checked) {
		final String key = context
				.getString(R.string.preferences_gps_activation_key);
		this.putBoolean(key, checked);
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
		this.putLong(key, seconds);
	}

	@Override
	public void setActualizationDurationSeconds(final long seconds) {
		final String key = context
				.getString(R.string.preferences_actualization_duration_key);
		this.putLong(key, seconds);
	}

	@Override
	public String getPingMessage() {
		return getString(
				//
				context.getString(R.string.preferences_ping_message_key), //
				context.getString(R.string.preferences_ping_message_default_value));
	}

	@Override
	public void setPingMessage(final String pingMessage) {
		final String key = context
				.getString(R.string.preferences_ping_message_key);
		this.putString( //
				key, //
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
		return getString(context.getString(R.string.preferences_user_id_key),
				User.EMPTY_USER_ID);
	}

	@Override
	public void setLocalUserId(final String id) {
		final String key = context.getString(R.string.preferences_user_id_key);
		this.putString(key, id);
	}

	// ************************************************ //
	// ====== Expiration ======
	// ************************************************ //

	@Override
	public boolean isApplicationExpired() {
		return getBoolean(
				context.getString(R.string.preferences_expiration_key), //
				false);
	}

	@Override
	public void setApplicationExpired(final boolean checked) {
		final String key = context
				.getString(R.string.preferences_expiration_key);
		this.putBoolean(key, checked);
	}
}
