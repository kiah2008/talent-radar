package com.menatwork.location;

import android.content.Context;
import android.location.LocationManager;

public class GpsLocationSource extends AndroidServiceLocationSource {

	public GpsLocationSource(final Context context,
			final long millisecondsBetweenUpdates) {
		super(LocationManager.GPS_PROVIDER, context, millisecondsBetweenUpdates);
	}

}
