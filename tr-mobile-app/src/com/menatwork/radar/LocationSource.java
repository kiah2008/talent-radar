package com.menatwork.radar;

import android.location.Location;
import android.location.LocationListener;

public interface LocationSource {

	Location getLastKnownLocation();

	void addLocationListener(LocationListener listener);

	void registerForLocationUpdates(long millisecondsBetweenUpdates);

}
