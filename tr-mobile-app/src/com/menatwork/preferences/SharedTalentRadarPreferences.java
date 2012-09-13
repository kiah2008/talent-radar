package com.menatwork.preferences;

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
	}

	@Override
	public void commitChanges() {
		final boolean commitSuccessfull = editor.commit();

		if (!commitSuccessfull)
			throw new RuntimeException("could not save changes to preferences");

		// TODO - should add keys that were modified; i could initialize them
		// after beginNewEdition and use them here - miguel - 13/09/2012
		notifyChanges();
		discardChanges();
	}

	// TODO - add which fields have been changed so that the listeners can act
	// upon this - boris - 13/09/2012
	private void notifyChanges(final String... keys) {
		for (final TalentRadarPreferencesListener listener : listeners)
			listener.onPreferencesChanged(new SharedPreferencesChanges(keys),
					this);
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
		setActualizationFrequencySeconds(milliseconds / 1000);
	}

	public void setActualizationDurationMilliseconds(final long milliseconds) {
		setActualizationDurationSeconds(milliseconds / 1000);
	}

	@Override
	public void setActualizationFrequencySeconds(final long seconds) {
		editor.putLong( //
				context.getString(R.string.preferences_actualization_frequency_key), //
				seconds);
	}

	@Override
	public void setActualizationDurationSeconds(final long seconds) {
		editor.putLong( //
				context.getString(R.string.preferences_actualization_duration_key), //
				seconds);
	}

	@Override
	public String getPingMessage() {
		return sharedPreferences.getString( //
				context.getString(R.string.preferences_ping_message_key), //
				context.getString(R.string.default_ping_message));
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
				context.getString(R.string.preferences_user_id),
				User.EMPTY_USER_ID);
	}

	@Override
	public void setLocalUserId(final String id) {
		editor.putString(context.getString(R.string.preferences_user_id), id);
	}

}
