package com.menatwork.radar;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

import com.menatwork.R;
import com.menatwork.TalentRadarActivity;
import com.menatwork.miniprofile.MiniProfileItemRow;
import com.menatwork.miniprofile.MiniProfileListController;
import com.menatwork.model.User;
import com.menatwork.model.UserBuilder;
import com.menatwork.radar.RadarService.RadarBinder;
import com.menatwork.service.ShareLocationAndGetUsers;
import com.menatwork.service.response.ShareLocationAndGetUsersResponse;

public class RadarActivity extends TalentRadarActivity implements RadarServiceListener {

	// TODO - used for the RadarService implementation -> Not used right now! -
	// boris - 17/08/2012
	private final ServiceConnection serviceConnection = new RadarServiceConnection();
	private boolean boundToRadarService = false;

	// TODO - Stub implementation so that we can test this service and
	// notificaciones - miguel - 02/08/2012
	private Button shareButton;

	private SlidingDrawer slidingDrawer;
	private List<MiniProfileItemRow> miniProfileItems;

	@Override
	protected int getViewLayoutId() {
		return R.layout.radar;
	}

	@Override
	protected void findViewElements() {
		shareButton = findButtonById(R.id.sharelocation);
		slidingDrawer = findViewById(R.id.radar_sliding_drawer, SlidingDrawer.class);
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
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				Log.d("RadarActivity", "Show surrounders button pressed");
				showMiniProfileList();
			}
		});
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		super.postCreate(savedInstanceState);

		// TODO - no puedo hacer esto en asynctask, me pide que lo haga en el
		// mismo thread de ui. Pensar forma de encapsular comportamiento de GPS
		// para stubear - boris - 17/08/2012
		new SuscribeToLocationServiceTask().doInBackground();
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
		Toast.makeText(this, "llegaron nuevos usuarios", Toast.LENGTH_SHORT).show();
	}

	// ************************************************ //
	// ====== ShareLocationAsyncTask ======
	// ************************************************ //

	private class ShareLocationAndGetUsersTask extends
			AsyncTask<Void, Void, ShareLocationAndGetUsersResponse> {

		@Override
		protected ShareLocationAndGetUsersResponse doInBackground(final Void... params) {
			try {

				final User localUser = getTalentRadarApplication().getLocalUser();

				final double longitude = 0;
				final double latitude = 0;

				// TODO - should be gotten from configuration - miguel -
				// 02/08/2012
				final int durationSeconds = 30;

				final ShareLocationAndGetUsers serviceCall = ShareLocationAndGetUsers.newInstance(
						RadarActivity.this, localUser.getId(), latitude, longitude, durationSeconds);

				final ShareLocationAndGetUsersResponse response = handleResponse(serviceCall.execute());

				refreshSurroundingContacts(response.parseSurroundingUsers());

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
			Log.d("RadarServiceRunnable", "JSON Response");
			Log.d("RadarServiceRunnable", response.toString());
			return response;
		}

	}

	private class SuscribeToLocationServiceTask extends
			AsyncTask<Void, Void, ShareLocationAndGetUsersResponse> {

		@Override
		protected ShareLocationAndGetUsersResponse doInBackground(final Void... params) {
			// Acquire a reference to the system Location Manager
			final LocationManager locationManager = (LocationManager) RadarActivity.this
					.getSystemService(Context.LOCATION_SERVICE);

			// Define a listener that responds to location updates
			final LocationListener locationListener = new LocationListener() {
				@Override
				public void onLocationChanged(final Location location) {
					try {
						Log.d("LocationChanged", "location.latitude = " + location.getLatitude()
								+ " location.longitude = " + location.getLongitude());
						final User localUser = getTalentRadarApplication().getLocalUser();

						// TODO - should be gotten from configuration - miguel -
						// 02/08/2012
						final int durationSeconds = 30;

						final ShareLocationAndGetUsers serviceCall = ShareLocationAndGetUsers.newInstance( //
								RadarActivity.this, //
								localUser.getId(), //
								location.getLatitude(), //
								location.getLongitude(), //
								durationSeconds);

						final ShareLocationAndGetUsersResponse response = handleResponse(serviceCall
								.execute());

						refreshSurroundingContacts(response.parseSurroundingUsers());

					} catch (final JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (final IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				@Override
				public void onStatusChanged(final String provider, final int status, final Bundle extras) {
				}

				@Override
				public void onProviderEnabled(final String provider) {
				}

				@Override
				public void onProviderDisabled(final String provider) {
				}
			};

			// Register the listener with the Location Manager to receive
			// location updates
			final int millisecondsBetweenUpdates = 10000; // 10 seconds
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					millisecondsBetweenUpdates, 0, locationListener);

			// para arrancar cuando todav�a no tenemos nada. :P
			final Location lastKnownLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (lastKnownLocation != null)
				Log.d("lastKnownLocation", "location.latitude = " + lastKnownLocation.getLatitude()
						+ " location.longitude = " + lastKnownLocation.getLongitude());
			else
				Log.d("lastKnownLocation", "location == null");

			return null;
		}

		private ShareLocationAndGetUsersResponse handleResponse(
				final ShareLocationAndGetUsersResponse response) {
			Log.d("RadarServiceRunnable", "JSON Response");
			Log.d("RadarServiceRunnable", response.toString());
			return response;
		}

	}

	/* *************************************************** */
	/* ********* RadarServiceConnection ****************** */
	/* *************************************************** */

	private final class RadarServiceConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(final ComponentName className, final IBinder service) {
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

	public void refreshSurroundingContacts(final List<? extends User> parseSurroundingUsers) {
		final List<MiniProfileItemRow> newMiniProfileItems = new LinkedList<MiniProfileItemRow>();
		for (final User user : parseSurroundingUsers)
			newMiniProfileItems.add(new MiniProfileItemRow(user));

		miniProfileItems = newMiniProfileItems;
	}

	protected void showMiniProfileList() {
		// XXX - get item rows from the actual users captured by the location
		// service - boris - 17/08/2012
		// final MiniProfileListController listController = new
		// MiniProfileListController(RadarActivity.this,
		// R.id.radar_mini_profiles_list_view, miniProfileItems);
		// listController.showList();

		final List<MiniProfileItemRow> itemRows = Arrays
				.asList( //
				new MiniProfileItemRow( //
						UserBuilder.newInstance().setHeadline("Java Dev").setUserName("Graciela").setId("1")
								.build()), //
						new MiniProfileItemRow( //
								UserBuilder.newInstance().setHeadline("Le Putite").setUserName("Rita")
										.setId("2").build()), //
						// FIXME - what to do when headline exceeds the maximum
						// space for text - boris - 17/08/2012
						new MiniProfileItemRow( //
								UserBuilder
										.newInstance()
										.setHeadline(
												"Pero me puedes decir: el hombre mas grande de la fuckin historia del universo. FUCK YEAH!")
										.setUserName("Chuck Norris").setId("3").build()), //
						new MiniProfileItemRow( //
								UserBuilder.newInstance().setHeadline("PHP EA PP WOW Web")
										.setUserName("Gonzalox").setId("4").build()) //
				);

		final MiniProfileListController listController = new MiniProfileListController(RadarActivity.this,
				R.id.radar_mini_profiles_list_view, itemRows);
		listController.showList();
	}

}
