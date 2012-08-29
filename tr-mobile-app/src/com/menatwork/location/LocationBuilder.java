package com.menatwork.location;

import android.location.Location;

public class LocationBuilder {

	private double longitude;
	private double latitude;
	private String providerName;
	private long time;

	protected LocationBuilder() {
		this.providerName = "default";
		this.longitude = 0;
		this.latitude = 0;
		this.time = 0;
	}

	public static LocationBuilder newInstance() {
		return new LocationBuilder();
	}

	public Location build() {
		final Location newLocation = new Location(providerName);
		
		newLocation.setLatitude(latitude);
		newLocation.setLongitude(longitude);
		newLocation.setTime(time);
		
		return newLocation;
	}

	// ************************************************ //
	// ====== Setters ======
	// ************************************************ //

	public LocationBuilder setCoordinates(final double latitude, final double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		return this;
	}

	public LocationBuilder setLatitude(final double latitude) {
		this.latitude = latitude;
		return this;
	}

	public LocationBuilder setLongitude(final double longitude) {
		this.longitude = longitude;
		return this;
	}

	public LocationBuilder setProviderName(final String providerName) {
		this.providerName = providerName;
		return this;
	}

	public LocationBuilder setTime(final long time) {
		this.time = time;
		return this;
	}
}
