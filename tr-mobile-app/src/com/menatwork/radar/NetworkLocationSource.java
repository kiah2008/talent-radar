package com.menatwork.radar;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.menatwork.TalentRadarActivity;

public class NetworkLocationSource implements LocationSource {

	private Location lastKnownLocation;
	private final List<LocationListener> listeners = new ArrayList<LocationListener>();
	private final TalentRadarActivity activity;

	public NetworkLocationSource(final TalentRadarActivity activity) {
		this.activity = activity;
	}

	private void initializeLastKnownLocation(final LocationManager locationManager) {
		lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		if (lastKnownLocation != null)
			Log.d("lastKnownLocation", "location.latitude = " + lastKnownLocation.getLatitude()
					+ " location.longitude = " + lastKnownLocation.getLongitude());
		else
			Log.d("lastKnownLocation", "location == null");
	}

	@Override
	public Location getLastKnownLocation() {
		return lastKnownLocation;
	}

	@Override
	public void addLocationListener(final LocationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void registerForLocationUpdates(final long millisecondsBetweenUpdates) {
		// Acquire a reference to the system Location Manager
		final LocationManager locationManager = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		final LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(final Location location) {
				Log.d("NetworkLocationSource", "location.latitude = " + location.getLatitude()
						+ " location.longitude = " + location.getLongitude());

				lastKnownLocation = location;

				for (final LocationListener listener : listeners)
					listener.onLocationChanged(location);
			}

			@Override
			public void onStatusChanged(final String provider, final int status, final Bundle extras) {
				for (final LocationListener listener : listeners)
					listener.onStatusChanged(provider, status, extras);
			}

			@Override
			public void onProviderEnabled(final String provider) {
				for (final LocationListener listener : listeners)
					listener.onProviderEnabled(provider);
			}

			@Override
			public void onProviderDisabled(final String provider) {
				for (final LocationListener listener : listeners)
					listener.onProviderDisabled(provider);
			}

		};

		// Register the listener with the Location Manager to receive
		// location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, millisecondsBetweenUpdates,
				0, locationListener);

		initializeLastKnownLocation(locationManager);
	}

}
