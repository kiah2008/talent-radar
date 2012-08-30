package com.menatwork.location;

import android.app.Activity;
import android.location.LocationManager;

/**
 * Implemented over Android's native Network location service. Uses wifi and
 * cell tower triangulation.
 * 
 * @author boris
 * 
 */
public class NetworkLocationSource extends AndroidServiceLocationSource {

	public NetworkLocationSource(final Activity activity, final long millisecondsBetweenUpdates) {
		super(LocationManager.NETWORK_PROVIDER, activity, millisecondsBetweenUpdates);
	}

}