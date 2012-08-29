package com.menatwork.location;

import android.location.Location;

public interface LocationSourceManagerListener {

	void onLocationUpdate(Location location, LocationSource locationSource);

}
