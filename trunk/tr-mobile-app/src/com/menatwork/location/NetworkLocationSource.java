package com.menatwork.location;

import android.location.LocationManager;

import com.menatwork.GuiTalentRadarActivity;

/**
 * Implemented over Android's native Network location service. Uses wifi and
 * cell tower triangulation.
 * 
 * @author boris
 * 
 */
public class NetworkLocationSource extends AndroidServiceLocationSource {

	public NetworkLocationSource(final GuiTalentRadarActivity activity, final long millisecondsBetweenUpdates) {
		super(LocationManager.NETWORK_PROVIDER, activity, millisecondsBetweenUpdates);
	}

}