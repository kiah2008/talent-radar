package com.menatwork.location;

import android.location.Location;

public class ConstantLocationSource implements LocationSource {

	private final Location location;

	public ConstantLocationSource(final Location location) {
		this.location = location;
	}

	@Override
	public Location getLastKnownLocation() {
		return location;
	}
}
