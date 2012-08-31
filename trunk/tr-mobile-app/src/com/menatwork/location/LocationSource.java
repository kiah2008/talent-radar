package com.menatwork.location;

import android.location.Location;

public interface LocationSource {

	Location getLastKnownLocation();

	void register();

	void unregister();

}
