package com.menatwork.location;

import android.app.Activity;
import android.location.LocationManager;

public class GpsLocationSource extends AndroidServiceLocationSource {

	public GpsLocationSource(final Activity activity, final long millisecondsBetweenUpdates) {
		super(LocationManager.GPS_PROVIDER, activity, millisecondsBetweenUpdates);
	}

}
