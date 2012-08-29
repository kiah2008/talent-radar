package com.menatwork.radar;

import java.io.IOException;
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
import com.menatwork.radar.RadarService.RadarBinder;
import com.menatwork.service.ShareLocationAndGetUsers;
import com.menatwork.service.response.ShareLocationAndGetUsersResponse;

public class RadarActivity extends TalentRadarActivity implements RadarServiceListener, LocationListener {

	// TODO - used for the RadarService implementation -> Not used right now! -
	// boris - 17/08/2012
	private final ServiceConnection serviceConnection = new RadarServiceConnection();
	private boolean boundToRadarService = false;

	// TODO - Stub implementation so that we can test this service and
	// notificaciones - miguel - 02/08/2012
	private Button shareButton;

	private SlidingDrawer slidingDrawer;

	private final Object miniProfileItemsLock = new Object();
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

				Location mockLocation;
				if (isRunningOnEmulator()) {
					// mock location for thy button
					mockLocation = new Location("mockis");
					mockLocation.setLatitude(0);
					mockLocation.setLongitude(0);
				} else {
					mockLocation = new NetworkLocationSource(RadarActivity.this).getLastKnownLocation();

					if (mockLocation == null) {
						Toast.makeText(RadarActivity.this, "No hay location, enviando (0,0)",
								Toast.LENGTH_SHORT).show();

						mockLocation = new Location("mockis");
						mockLocation.setLatitude(0);
						mockLocation.setLongitude(0);
					}
				}

				task.execute(mockLocation);
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

		// initialize location source
		final LocationSource locationSource = new NetworkLocationSource(this);

		locationSource.addLocationListener(this);
		locationSource.registerForLocationUpdates(10000);
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

	/* ********************************************* */
	/* ********* LocationListener ****************** */
	/* ********************************************* */

	@Override
	public void onLocationChanged(final Location location) {
		new ShareLocationAndGetUsersTask().execute(location);
	}

	@Override
	public void onProviderDisabled(final String provider) {
		Log.d("RadarActivity", "LocationListener.onProviderDisabled [provider = " + provider + "]");
	}

	@Override
	public void onProviderEnabled(final String provider) {
		Log.d("RadarActivity", "LocationListener.onProviderEnabled [provider = " + provider + "]");
	}

	@Override
	public void onStatusChanged(final String provider, final int status, final Bundle extras) {
		Log.d("RadarActivity", "LocationListener.onStatusChanged [provider = " + provider + ", status = "
				+ status + "]");
	}

	// ************************************************ //
	// ====== ShareLocationAsyncTask ======
	// ************************************************ //

	private class ShareLocationAndGetUsersTask extends
			AsyncTask<Location, Void, ShareLocationAndGetUsersResponse> {

		@Override
		protected ShareLocationAndGetUsersResponse doInBackground(final Location... location) {
			try {

				final User localUser = getTalentRadarApplication().getLocalUser();

				final double latitude = location[0].getLatitude();
				final double longitude = location[0].getLongitude();

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

			// TODO - should return null, throw an exception or nothing at all?
			// - boris - 22/08/2012
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

		synchronized (miniProfileItemsLock) {
			miniProfileItems = newMiniProfileItems;
		}
	}

	protected void showMiniProfileList() {
		LinkedList<MiniProfileItemRow> copy;
		
		synchronized (miniProfileItemsLock) {
			copy = new LinkedList<MiniProfileItemRow>(miniProfileItems);
		}

		final MiniProfileListController listController = new MiniProfileListController(RadarActivity.this,
				R.id.radar_mini_profiles_list_view, copy);
		listController.showList();

		// XXX - stub list for testing purposes, comment if needed - boris -
		// 22/08/2012
		// final List<MiniProfileItemRow> itemRows = Arrays
		// .asList( //
		// new MiniProfileItemRow( //
		// UserBuilder.newInstance().setHeadline("Java Dev").setUserName("Graciela").setId("1")
		// .build()), //
		// new MiniProfileItemRow( //
		// UserBuilder.newInstance().setHeadline("Le Putite").setUserName("Rita")
		// .setId("2").build()), //
		// // FIXME - what to do when headline exceeds the maximum
		// // space for text - boris - 17/08/2012
		// new MiniProfileItemRow( //
		// UserBuilder
		// .newInstance()
		// .setHeadline(
		// "Pero me puedes decir: el hombre mas grande de la fuckin historia del universo. FUCK YEAH!")
		// .setUserName("Chuck Norris").setId("3").build()), //
		// new MiniProfileItemRow( //
		// UserBuilder.newInstance().setHeadline("PHP EA PP WOW Web")
		// .setUserName("Gonzalox").setId("4").build()) //
		// );

		// final MiniProfileListController listController = new
		// MiniProfileListController(RadarActivity.this,
		// R.id.radar_mini_profiles_list_view, itemRows);
		// listController.showList();
	}
}
