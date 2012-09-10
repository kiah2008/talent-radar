package com.menatwork;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

import com.menatwork.R;
import com.menatwork.location.LocationBuilder;
import com.menatwork.location.LocationSource;
import com.menatwork.location.LocationSourceManager;
import com.menatwork.location.LocationSourceManagerListener;
import com.menatwork.miniprofile.MiniProfileItemRow;
import com.menatwork.miniprofile.MiniProfileListController;
import com.menatwork.model.User;
import com.menatwork.radar.RadarService;
import com.menatwork.service.ShareLocationAndGetUsers;
import com.menatwork.service.response.ShareLocationAndGetUsersResponse;

public class RadarActivity extends GuiTalentRadarActivity implements
		LocationSourceManagerListener {

	// TODO - used for the RadarService implementation -> Not used right now! -
	// boris - 17/08/2012
	private final ServiceConnection serviceConnection = new RadarServiceConnection();
	private boolean boundToRadarService = false;

	private SlidingDrawer slidingDrawer;

	// TODO - should be some kind of concurrent collection, maybe - boris -
	// 29/08/2012
	private final Object miniProfileItemsLock = new Object();
	private List<MiniProfileItemRow> miniProfileItems = new LinkedList<MiniProfileItemRow>();

	@Override
	protected int getViewLayoutId() {
		return R.layout.radar;
	}

	@Override
	protected void findViewElements() {
		slidingDrawer = findViewById(R.id.radar_sliding_drawer,
				SlidingDrawer.class);
	}

	@Override
	protected void setupButtons() {
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

		final LocationSourceManager locationSourceManager = getTalentRadarApplication()
				.getLocationSourceManager();
		locationSourceManager.addListener(this);
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

	public synchronized void refreshSurroundingContacts(
			final List<? extends User> parseSurroundingUsers) {
		final List<MiniProfileItemRow> newMiniProfileItems = new LinkedList<MiniProfileItemRow>();
		for (final User user : parseSurroundingUsers)
			newMiniProfileItems.add(new MiniProfileItemRow(user));

		// beware of concurrent use of this collection
		synchronized (miniProfileItemsLock) {
			miniProfileItems = newMiniProfileItems;
		}
	}

	protected void showMiniProfileList() {
		LinkedList<MiniProfileItemRow> copy;

		// beware of concurrent use of this collection
		synchronized (miniProfileItemsLock) {
			copy = new LinkedList<MiniProfileItemRow>(miniProfileItems);
		}

		final MiniProfileListController listController = new MiniProfileListController(
				RadarActivity.this, R.id.radar_mini_profiles_list_view, copy);
		listController.showList();
	}

	/* ********************************************* */
	/* ********* LocationSourceManagerListener ***** */
	/* ********************************************* */

	@Override
	public void onLocationUpdate(final Location location,
			final LocationSource locationSource) {
		Log.d("RadarActivity", "new location = " + location);
		new ShareLocationAndGetUsersTask().execute(location);
	}

	// ************************************************ //
	// ====== ShareLocationAsyncTask ======
	// ************************************************ //

	private class ShareLocationAndGetUsersTask extends
			AsyncTask<Location, Void, ShareLocationAndGetUsersResponse> {

		@Override
		protected ShareLocationAndGetUsersResponse doInBackground(
				final Location... locations) {
			try {
				final Location location = initializeLocation(locations);
				if (location != null) {
					final User localUser = getTalentRadarApplication()
							.getLocalUser();

					final double latitude = location.getLatitude();
					final double longitude = location.getLongitude();

					final long durationSeconds = getPreferences()
							.getActualizationDurationSeconds();

					final ShareLocationAndGetUsers serviceCall = ShareLocationAndGetUsers
							.newInstance(RadarActivity.this, localUser.getId(),
									latitude, longitude, durationSeconds);

					final ShareLocationAndGetUsersResponse response = handleResponse(serviceCall
							.execute());

					refreshSurroundingContacts(response.parseSurroundingUsers());
				}
			} catch (final JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		private Location initializeLocation(final Location... locations) {
			return isRunningOnEmulator() ? LocationBuilder.newInstance()
					.setCoordinates(0, 0).build() : locations[0];
		}

		private ShareLocationAndGetUsersResponse handleResponse(
				final ShareLocationAndGetUsersResponse response) {
			Log.d("ShareLocationAndGetUsersTask", "JSON Response");
			Log.d("ShareLocationAndGetUsersTask", response.toString());
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
			// final RadarBinder binder = (RadarBinder) service;
			// final RadarService radarService = binder.getService();

			// radarService.addRadarServiceListener(RadarActivity.this);

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