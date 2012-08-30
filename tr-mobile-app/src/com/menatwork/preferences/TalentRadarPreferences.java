package com.menatwork.preferences;

public interface TalentRadarPreferences {

	boolean getNetworkLocationActivation();

	boolean getGpsLocationActivation();

	long getActualizationDurationMilliseconds();

	long getActualizationFrequencyMilliseconds();

	long getActualizationDurationSeconds();

	long getActualizationFrequencySeconds();

}
