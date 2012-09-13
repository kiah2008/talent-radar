package com.menatwork.location;

import java.util.EventListener;

import android.location.Location;

public interface LocationSourceManagerListener extends EventListener {

	void onLocationUpdate(Location location, LocationSource locationSource);

}
