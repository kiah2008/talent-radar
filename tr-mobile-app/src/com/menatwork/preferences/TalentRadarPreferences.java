package com.menatwork.preferences;

public interface TalentRadarPreferences {

	boolean getNetworkLocationActivation();

	boolean getGpsLocationActivation();

	long getActualizationDurationMilliseconds();

	long getActualizationFrequencyMilliseconds();

	long getActualizationDurationSeconds();

	long getActualizationFrequencySeconds();

	void setNetworkLocationActivation(boolean checked);

	void setGpsLocationActivation(boolean checked);

	void setActualizationDurationSeconds(long seconds);

	void setActualizationFrequencySeconds(long seconds);

	/**
	 * This method should be called before trying to change any preference. It
	 * starts a new session for edition. Kind of a transaction.
	 */
	void setNewEdition();

	/**
	 * This method should be called after finishing changes in the application
	 * preferences. It confirms the changes in the configuration.
	 * <p />
	 * <b>IMPORTANT:</b> This method shouldn't be called before starting a new
	 * edition session calling setNewEdtion.
	 */
	void commitChanges();

	/**
	 * This method should be called after finishing changes in the application
	 * preferences. It discard the changes made.
	 * <p />
	 * <b>IMPORTANT:</b> This method shouldn't be called before starting a new
	 * edition session calling setNewEdtion.
	 */
	void discardChanges();

}
