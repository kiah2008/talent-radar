package com.menatwork.location;

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
public class AndroidServiceLocationSource implements LocationSource,
		LocationListener {

	private Location lastKnownLocation;

	private final String providerName;
	private final Context context;
	private final long millisecondsBetweenUpdates;

	public AndroidServiceLocationSource(final String providerName,
			final Context context, final long millisecondsBetweenUpdates) {
		this.providerName = providerName;
		this.context = context;
		this.millisecondsBetweenUpdates = millisecondsBetweenUpdates;
	}

	@Override
	public Location getLastKnownLocation() {
		return lastKnownLocation;
	}

	@Override
	public void register() {
		registerForLocationUpdates(millisecondsBetweenUpdates, context,
				providerName);
	}

	@Override
	public void unregister() {
		// Acquire a reference to the system Location Manager
		final LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		// Unregister the listener with the Location Manager to receive
		// location updates
		locationManager.removeUpdates(this);
	}

	private void registerForLocationUpdates(
			final long millisecondsBetweenUpdates, final Context context,
			final String providerName) {
		// Acquire a reference to the system Location Manager
		final LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		// Register the listener with the Location Manager to receive
		// location updates
		locationManager.requestLocationUpdates(providerName,
				millisecondsBetweenUpdates, 0, this);

		initializeLastKnownLocation(locationManager, providerName);
	}

	private void initializeLastKnownLocation(
			final LocationManager locationManager, final String providerName) {
		lastKnownLocation = locationManager.getLastKnownLocation(providerName);

		if (lastKnownLocation != null)
			Log.d("lastKnownLocation",
					"provider = " + providerName + " location.latitude = "
							+ lastKnownLocation.getLatitude()
							+ " location.longitude = "
							+ lastKnownLocation.getLongitude());
		else
			Log.d("lastKnownLocation", "provider = " + providerName
					+ " location == null");
	}

	// ************************************************ //
	// ====== LocationListener ======
	// ************************************************ //

	@Override
	public void onLocationChanged(final Location location) {
		Log.d(getClass().getSimpleName(),
				"location.latitude = " + location.getLatitude()
						+ " location.longitude = " + location.getLongitude());

		lastKnownLocation = location;
	}

	@Override
	public void onStatusChanged(final String provider, final int status,
			final Bundle extras) {
		Log.d(getClass().getSimpleName(),
				"LocationListener.onStatusChanged [provider = " + provider
						+ ", status = " + status + "]");
	}

	@Override
	public void onProviderEnabled(final String provider) {
		Log.d(getClass().getSimpleName(),
				"LocationListener.onProviderEnabled [provider = " + provider
						+ "]");
	}

	@Override
	public void onProviderDisabled(final String provider) {
		Log.d(getClass().getSimpleName(),
				"LocationListener.onProviderDisabled [provider = " + provider
						+ "]");
	}
}
