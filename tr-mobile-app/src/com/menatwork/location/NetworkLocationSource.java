package com.menatwork.location;

import android.content.Context;
import android.location.LocationManager;

/**
 * Implemented over Android's native Network location service. Uses wifi and
 * cell tower triangulation.
 *
 * @author boris
 *
 */
public class NetworkLocationSource extends AndroidServiceLocationSource {

	public NetworkLocationSource(final Context context,
			final long millisecondsBetweenUpdates) {
		super(LocationManager.NETWORK_PROVIDER, context,
				millisecondsBetweenUpdates);
	}

}