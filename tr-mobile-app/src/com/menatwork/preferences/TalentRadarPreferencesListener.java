package com.menatwork.preferences;

public interface TalentRadarPreferencesListener {

	/**
	 * Notifies whenever there were changes made to the application's
	 * preferences.
	 *
	 * @param changes
	 *            It knows about which preferences were changed affecting which
	 *            parts of the application
	 * @param preferences
	 *            Preferences which were modified, should have the new values
	 */
	void onPreferencesChanged(PreferencesChanges changes,
			TalentRadarPreferences preferences);

}
