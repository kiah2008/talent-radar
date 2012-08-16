package com.menatwork.radar;

import java.io.IOException;
import java.util.Collection;

import org.json.JSONException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.menatwork.R;
import com.menatwork.TalentRadarActivity;
import com.menatwork.model.User;
import com.menatwork.radar.RadarService.RadarBinder;
import com.menatwork.service.ShareLocationAndGetUsers;
import com.menatwork.service.ShareLocationAndGetUsersResponse;

public class RadarActivity extends TalentRadarActivity implements
		RadarServiceListener {

	private final ServiceConnection serviceConnection = new RadarServiceConnection();
	private boolean boundToRadarService = false;

	// TODO - Stub implementation so that we can test this service and
	// notificaciones - miguel - 02/08/2012
	private Button shareButton;

	@Override
	protected int getViewLayoutId() {
		return R.layout.radar;
	}

	@Override
	protected void findViewElements() {
		shareButton = findButtonById(R.id.sharelocation);
	}

	@Override
	protected void setupButtons() {
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final ShareLocationAndGetUsersTask task = new ShareLocationAndGetUsersTask();
				task.execute();
			}
		});
	}

	// ************************************************ //
	// ====== State changes ======
	// ************************************************ //

	@Override
	protected void onStart() {
		super.onStart();

		// Binding with RadarService
		final Intent intent = new Intent(this, RadarService.class);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		Log.i("RadarActivity", "binding service");
	}

	@Override
	protected void onStop() {
		super.onStop();

		if (boundToRadarService) {
			unbindService(serviceConnection);
			boundToRadarService = false;
		}
	}

	@Override
	public void usersFound(final Collection<? extends User> users) {
		// TODO - Jao, como has estao! Basically, we should (1) replace the last
		// users found list with the new one and (2) replace the dots in the
		// radar with new random ones - miguel - 26/07/2012
		Toast.makeText(this, "llegaron nuevos usuarios", Toast.LENGTH_SHORT)
				.show();
	}

	// ************************************************ //
	// ====== ShareLocationAsyncTask ======
	// ************************************************ //

	private class ShareLocationAndGetUsersTask extends
			AsyncTask<Void, Void, ShareLocationAndGetUsersResponse> {

		@Override
		protected ShareLocationAndGetUsersResponse doInBackground(
				final Void... params) {
			try {

				final User localUser = getTalentRadarApplication()
						.getLocalUser();

				// TODO - we've got to take the gps location first -
				// miguel - 27/07/2012

				// Acquire a reference to the system Location Manager
				final LocationManager locationManager = (LocationManager) RadarActivity.this
						.getSystemService(Context.LOCATION_SERVICE);

				// Define a listener that responds to location updates
				final LocationListener locationListener = new LocationListener() {
					@Override
					public void onLocationChanged(final Location location) {
						// Called when a new location is found by the network
						// location
						// provider.
//						makeUseOfNewLocation(location);
					}

					@Override
					public void onStatusChanged(final String provider,
							final int status, final Bundle extras) {
					}

					@Override
					public void onProviderEnabled(final String provider) {
					}

					@Override
					public void onProviderDisabled(final String provider) {
					}
				};

				// Register the listener with the Location Manager to receive
				// location
				// updates
				final int millisecondsBetweenUpdates = 10000; // 10 seconds
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, millisecondsBetweenUpdates, millisecondsBetweenUpdates,
						locationListener);

				// para arrancar cuando todavía no tenemos nada. :P
				final Location lastKnownLocation = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

				final double longitude = millisecondsBetweenUpdates;
				final double latitude = millisecondsBetweenUpdates;

				// TODO - should be gotten from configuration - miguel -
				// 02/08/2012
				final int durationSeconds = 30;

				final ShareLocationAndGetUsers serviceCall = ShareLocationAndGetUsers
						.newInstance(RadarActivity.this, localUser.getId(),
								latitude, longitude, durationSeconds);

				handleResponse(serviceCall.execute());
			} catch (final JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		private ShareLocationAndGetUsersResponse handleResponse(
				final ShareLocationAndGetUsersResponse response) {
			Log.d("RadarSericeRunnable", "JSON Response");
			Log.d("RadarSericeRunnable", response.toString());
			return response;
		}

	}

	/* *************************************************** */
	/* ********* RadarServiceConnection ****************** */
	/* *************************************************** */

	private final class RadarServiceConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(final ComponentName className,
				final IBinder service) {
			// We've bound to RadarService, cast the IBinder and get
			// RadarService instance
			final RadarBinder binder = (RadarBinder) service;
			final RadarService radarService = binder.getService();

			radarService.addRadarServiceListener(RadarActivity.this);

			boundToRadarService = true;
			Log.i("radar activity", "radar service connected");
		}

		@Override
		public void onServiceDisconnected(final ComponentName componentName) {
			Log.e("radar activity", "radar service disconnected");
			boundToRadarService = false;
		}
	}

}
