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

	@Override
	public void register() {
		// nothing to do here!
	}

	@Override
	public void unregister() {
		// nothing to do here!
	}
}
