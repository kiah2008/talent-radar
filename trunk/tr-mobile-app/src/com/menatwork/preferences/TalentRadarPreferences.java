package com.menatwork.preferences;

public interface TalentRadarPreferences {

	/**
	 * This method should be called before trying to change any preference. It
	 * starts a new session for edition. Kind of a transaction.
	 */
	void beginNewEdition();

	/**
	 * This method should be called after finishing changes in the application
	 * preferences. It confirms the changes in the configuration.
	 * <p />
	 * <b>IMPORTANT:</b> This method shouldn't be called before starting a new
	 * edition session calling {@link TalentRadarPreferences#beginNewEdition()}.
	 */
	void commitChanges();

	/**
	 * This method should be called after finishing changes in the application
	 * preferences. It discard the changes made.
	 * <p />
	 * <b>IMPORTANT:</b> This method shouldn't be called before starting a new
	 * edition session calling {@link TalentRadarPreferences#beginNewEdition()}.
	 */
	void discardChanges();

	boolean isNetworkLocationActivation();

	boolean isGpsLocationActivation();

	long getActualizationDurationMilliseconds();

	long getActualizationFrequencyMilliseconds();

	long getActualizationDurationSeconds();

	long getActualizationFrequencySeconds();

	String getPingMessage();

	String getLocalUserId();

	void setNetworkLocationActivation(boolean checked);

	void setGpsLocationActivation(boolean checked);

	void setActualizationDurationSeconds(long seconds);

	void setActualizationFrequencySeconds(long seconds);

	void setPingMessage(String string);

	void setLocalUserId(String id);

}
