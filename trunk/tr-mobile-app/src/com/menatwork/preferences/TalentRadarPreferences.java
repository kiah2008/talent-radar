package com.menatwork.preferences;

/**
 * Common interface for the application's preferences access.
 * <p />
 * Note that there will be two kinds of access to this object:
 * <p />
 * - One for getting the preferences previously saved by the application. This
 * will be done by just calling the <code>getXXX</code> methods defined in this
 * interface.
 * <p />
 * - And the other and most tricky one is for setting and saving new values to
 * the preferences. It may not be obvious at first, but it uses the same
 * protocol as a <b>transaction</b>, i.e.:
 * <p />
 * <b>1)</b> You should first start a new transaction before setting the
 * preferences. This is done by calling the
 * {@link TalentRadarPreferences#beginNewEdition()}
 * <p />
 * <b>2)</b> Now you are able to set the new values of the preferences just by
 * calling the bunch of <code>setXXX</code> methods.
 * <p />
 * <b>3)</b> Last of all, you should either accept and confirm those changes by
 * calling {@link TalentRadarPreferences#commitChanges()} or if you decide to
 * cancel these changes, {@link TalentRadarPreferences#discardChanges()}.
 * Remember that you should call either of them ONCE AND ONLY ONCE.
 * 
 * @author boris
 * 
 */
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
