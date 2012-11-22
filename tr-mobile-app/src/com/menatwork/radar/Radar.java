package com.menatwork.radar;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.location.Location;
import android.util.Log;

import com.menatwork.TalentRadarApplication;
import com.menatwork.location.LocationBuilder;
import com.menatwork.location.LocationSource;
import com.menatwork.location.LocationSourceManager;
import com.menatwork.location.LocationSourceManagerListener;
import com.menatwork.model.User;
import com.menatwork.service.ShareLocationAndGetUsers;
import com.menatwork.service.response.ShareLocationAndGetUsersResponse;
import com.menatwork.utils.AndroidUtils;

public class Radar implements LocationSourceManagerListener {

	private final List<RadarListener> listeners;

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public static Radar observingLocationUpdatesFrom(final LocationSourceManager locationSourceManager) {
		return new Radar(locationSourceManager);
	}

	private Radar(final LocationSourceManager locationSourceManager) {
		this.listeners = new LinkedList<RadarListener>();
		locationSourceManager.addListener(this);
	}

	// ************************************************ //
	// ====== Listener accessors ======
	// ************************************************ //

	public void addListeners(final RadarListener... listeners) {
		this.listeners.addAll(Arrays.asList(listeners));
	}

	public void removeListener(final RadarListener listener) {
		listeners.remove(listener);
	}

	/* ********************************************* */
	/* ********* LocationSourceManagerListener ***** */
	/* ********************************************* */

	@Override
	public void onLocationUpdate(final Location loc, final LocationSource locationSource) {
		Log.d("Radar", "new location = " + loc);

		new ShareLocationAndGetUsersThread(loc).start();
	}

	public TalentRadarApplication getTalentRadarApplication() {
		return TalentRadarApplication.getContext();
	}

	public void notifySurroundingUsers(final List<? extends User> surroundingUsers) {
		for (final RadarListener listener : listeners)
			listener.onNewSurroundingUsers(surroundingUsers);
	}

	// ************************************************ //
	// ====== ShareLocationThread ======
	// ************************************************ //

	private final class ShareLocationAndGetUsersThread extends Thread {

		private final Location loc;

		private ShareLocationAndGetUsersThread(final Location loc) {
			super("ShareLocationAndGetUsersThread");
			this.loc = loc;
		}

		@Override
		public void run() {
			try {
				final Location location = initializeLocation(loc);
				if (location != null) {
					final TalentRadarApplication talentRadarApplication = getTalentRadarApplication();
					final User localUser = talentRadarApplication.getLocalUser();

					final double latitude = location.getLatitude();
					final double longitude = location.getLongitude();

					final long durationSeconds = talentRadarApplication.getPreferences()
							.getActualizationDurationSeconds();

					final ShareLocationAndGetUsers serviceCall = ShareLocationAndGetUsers.newInstance( //
							talentRadarApplication, //
							localUser.getId(), //
							latitude, //
							longitude, //
							durationSeconds);

					final ShareLocationAndGetUsersResponse response = handleResponse(serviceCall.execute());

					final List<? extends User> surroundingUsers = response.parseSurroundingUsers();

					notifySurroundingUsers(Collections.unmodifiableList(surroundingUsers));
				}
			} catch (final JSONException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		private Location initializeLocation(final Location... locations) {
			return AndroidUtils.isRunningOnEmulator() ? LocationBuilder.newInstance().setCoordinates(0, 0)
					.build() : locations[0];
		}

		private ShareLocationAndGetUsersResponse handleResponse(
				final ShareLocationAndGetUsersResponse response) {
			Log.d("ShareLocationAndGetUsersTask", "JSON Response");
			Log.d("ShareLocationAndGetUsersTask", response.toString());
			return response;
		}
	}

}
