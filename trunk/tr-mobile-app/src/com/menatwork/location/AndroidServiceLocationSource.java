package com.menatwork.location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Main entrance for Android implemented locatino sources. To instantiate each
 * of its types you should pass a constant defined in {@link LocationManager}
 * like {@link LocationManager#GPS_PROVIDER}.
 * 
 * @author boris
 * 
 */
public class AndroidServiceLocationSource implements LocationSource {

	private Location lastKnownLocation;

	public AndroidServiceLocationSource(final String providerName, final Activity activity,
			final long millisecondsBetweenUpdates) {
		registerForLocationUpdates(millisecondsBetweenUpdates, activity, providerName);
	}

	private void initializeLastKnownLocation(final LocationManager locationManager, final String providerName) {
		lastKnownLocation = locationManager.getLastKnownLocation(providerName);

		if (lastKnownLocation != null)
			Log.d("lastKnownLocation",
					"provider = " + providerName + " location.latitude = " + lastKnownLocation.getLatitude()
							+ " location.longitude = " + lastKnownLocation.getLongitude());
		else
			Log.d("lastKnownLocation", "provider = " + providerName + " location == null");
	}

	@Override
	public Location getLastKnownLocation() {
		return lastKnownLocation;
	}

	private void registerForLocationUpdates(final long millisecondsBetweenUpdates, final Context activity,
			final String providerName) {
		// Acquire a reference to the system Location Manager
		final LocationManager locationManager = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		final LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(final Location location) {
				Log.d(getClass().getSimpleName(), "location.latitude = " + location.getLatitude()
						+ " location.longitude = " + location.getLongitude());

				lastKnownLocation = location;

				// for (final LocationListener listener : listeners)
				// listener.onLocationChanged(location);
			}

			@Override
			public void onStatusChanged(final String provider, final int status, final Bundle extras) {
				Log.d(getClass().getSimpleName(), "LocationListener.onStatusChanged [provider = " + provider
						+ ", status = " + status + "]");
				// for (final LocationListener listener : listeners)
				// listener.onStatusChanged(provider, status, extras);
			}

			@Override
			public void onProviderEnabled(final String provider) {
				Log.d(getClass().getSimpleName(), "LocationListener.onProviderEnabled [provider = "
						+ provider + "]");
				// for (final LocationListener listener : listeners)
				// listener.onProviderEnabled(provider);
			}

			@Override
			public void onProviderDisabled(final String provider) {
				Log.d(getClass().getSimpleName(), "LocationListener.onProviderDisabled [provider = "
						+ provider + "]");
				// for (final LocationListener listener : listeners)
				// listener.onProviderDisabled(provider);
			}

		};

		// Register the listener with the Location Manager to receive
		// location updates
		locationManager.requestLocationUpdates(providerName, millisecondsBetweenUpdates, 0, locationListener);

		initializeLastKnownLocation(locationManager, providerName);
	}

	// @Override
	// public void addLocationListener(final LocationListener listener) {
	// listeners.add(listener);
	// }

	// @Override
	// public void registerForLocationUpdates(final long
	// millisecondsBetweenUpdates) {
	// registerForUpdates(millisecondsBetweenUpdates);
	// }

}
