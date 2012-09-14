package com.menatwork.location;

import android.location.Location;

public class FixedLocationSource implements LocationSource {

	private final Location location;

	public FixedLocationSource(final Location location) {
		this.location = location;
	}

	@Override
	public Location getLastKnownLocation() {
		return location;
	}

	@Override
	public void register() {
		// nothing to do here!
	}

	@Override
	public void unregister() {
		// nothing to do here!
	}
}
