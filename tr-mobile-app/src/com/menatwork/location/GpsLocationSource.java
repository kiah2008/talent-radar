package com.menatwork.location;

import android.location.LocationManager;

import com.menatwork.GuiTalentRadarActivity;

public class GpsLocationSource extends AndroidServiceLocationSource {

	public GpsLocationSource(final GuiTalentRadarActivity activity, final long millisecondsBetweenUpdates) {
		super(LocationManager.GPS_PROVIDER, activity, millisecondsBetweenUpdates);
	}

}
