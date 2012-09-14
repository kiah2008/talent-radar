package com.menatwork;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.menatwork.location.GpsLocationSource;
import com.menatwork.location.LocationSourceManager;
import com.menatwork.location.NetworkLocationSource;
import com.menatwork.model.User;
import com.menatwork.notification.TrNotificationManager;
import com.menatwork.preferences.PreferencesChanges;
import com.menatwork.preferences.SharedTalentRadarPreferences;
import com.menatwork.preferences.TalentRadarPreferences;
import com.menatwork.preferences.TalentRadarPreferencesListener;
import com.menatwork.skills.DefaultSkillButtonFactory;
import com.menatwork.skills.SkillButtonFactory;
import com.menatwork.utils.AndroidUtils;

public class TalentRadarApplication extends Application implements
		TalentRadarPreferencesListener {

	private static Context context;

	private final Object deviceRegistrationLock = new Object();
	private String deviceRegistrationId;

	private User localUser;

	private SkillButtonFactory skillButtonFactory;
	private LocationSourceManager locationSourceManager;
	private TalentRadarPreferences preferences;
	private TrNotificationManager notificationManager;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;

		skillButtonFactory = DefaultSkillButtonFactory.newInstance();
		preferences = new SharedTalentRadarPreferences(
				PreferenceManager.getDefaultSharedPreferences(this), this, this);
		notificationManager = TrNotificationManager.newInstance();
		locationSourceManager = new NaiveLocationSourceManager();
	}

	public static Context getContext() {
		return context;
	}

	public void loadLocalUser(final User user) {
		Log.d("TalentRadarApplication", "loadLocalUser() " + user);
		this.localUser = user;
		this.persistLocalUserId(user.getId());
	}

	private void persistLocalUserId(final String id) {
		if (id.equals(preferences.getLocalUserId())) {
			// do nothing!
		} else {
			preferences.beginNewEdition();
			preferences.setLocalUserId(id);
			// FIXME - beware to use BOTH methods, choose one to finish edition
			// - boris - 13/09/2012
			preferences.commitChanges();
			preferences.discardChanges();
		}
	}

	public void logOut() {
		this.persistLocalUserId(User.EMPTY_USER_ID);
	}

	public User getLocalUser() {
		return localUser;
	}

	public String getLocalUserId() {
		return localUser.getId();
	}

	public SkillButtonFactory getSkillButtonFactory() {
		return skillButtonFactory;
	}

	public TrNotificationManager getNotificationManager() {
		return notificationManager;
	}

	// ************************************************ //
	// ====== Preferences ======
	// ************************************************ //

	public TalentRadarPreferences getPreferences() {
		return preferences;
	}

	@Override
	public synchronized void onPreferencesChanged(
			final PreferencesChanges changes,
			final TalentRadarPreferences preferences) {
		Log.d("TalentRadarApplication", "onPreferencesChanged");

		if (changes.hasLocationSourceManagerPreferencesChanged()) {
			locationSourceManager.deactivate();
			updateLocationSourceManagerConfiguration(preferences);
			locationSourceManager.activate();
		}
	}

	// ************************************************ //
	// ====== LocationSourceManager ======
	// ************************************************ //

	public LocationSourceManager getLocationSourceManager() {
		return locationSourceManager;
	}

	public void setLocationSourceManager(
			final LocationSourceManager locationSourceManager) {
		this.locationSourceManager = locationSourceManager;
	}

	public void startLocationSourceManager() {
		final LocationSourceManager locationSourceManager = new LocationSourceManager();

		setLocationSourceManager(locationSourceManager);
		updateLocationSourceManagerConfiguration(getPreferences());
		locationSourceManager.activate();
	}

	public void stopLocationSourceManager() {
		getLocationSourceManager().deactivate();
		setLocationSourceManager(null);
	}

	private void updateLocationSourceManagerConfiguration(
			final TalentRadarPreferences preferences) {

		final long actualizationFrequencyMilliseconds = preferences
				.getActualizationFrequencyMilliseconds();
		final long millisecondsBetweenUpdates = actualizationFrequencyMilliseconds / 2;

		// change actualization frequency
		locationSourceManager
				.setMillisecondsBetweenUpdates(actualizationFrequencyMilliseconds);

		// change location sources
		locationSourceManager.removeAllLocationSources();

		if (preferences.isNetworkLocationActivation())
			locationSourceManager.addLocationSource( //
					new NetworkLocationSource(this, millisecondsBetweenUpdates));

		if (preferences.isGpsLocationActivation())
			locationSourceManager.addLocationSource( //
					new GpsLocationSource(this, millisecondsBetweenUpdates));
	}

	// ************************************************ //
	// ====== GCM Stuff ======
	// ************************************************ //

	public String getDeviceRegistrationId() {
		if (deviceRegistrationId == null) {
			if (!this.isDeviceRegistered())
				throw new RuntimeException(
						"Device not registered, deviceRegistrationId == null");
			else {
				deviceRegistrationId = GCMRegistrar.getRegistrationId(this);
			}
		}
		return deviceRegistrationId;
	}

	public boolean isDeviceRegistered() {
		return GCMRegistrar.isRegistered(this);
	}

	/**
	 * Synchronous method for registering the local device for GCM usage. This
	 * method will wait for 10 seconds tops to get the registration answer,
	 * after that it will stop waiting and continue execution. If the
	 * registration answer comes, it will be registered but the Talent Radar
	 * server won't be notified. There is something TODO here.
	 */
	public void registerDevice() {
		try {
			synchronized (deviceRegistrationLock) {
				GCMRegistrar.register(this, GCMIntentService.SENDER_ID);
				deviceRegistrationLock.wait();
				if (deviceRegistrationId == null) {
					Log.w("TalentRadarApp",
							"Timeout registering device, continuing excecution...");
				} else {
					Log.d("TalentRadarApp", "Registered device");
				}
			}
		} catch (final InterruptedException e) {
		}
	}

	public void onDeviceRegistered(final String registrationId) {
		this.deviceRegistrationId = registrationId;
		synchronized (deviceRegistrationLock) {
			deviceRegistrationLock.notify();
		}
	}

	// ************************************************ //
	// ====== Other utils ======
	// ************************************************ //

	/**
	 * @see AndroidUtils#isRunningOnEmulator()
	 */
	public boolean isRunningOnEmulator() {
		return AndroidUtils.isRunningOnEmulator();
	}

	// ************************************************ //
	// ====== NaiveLocationSourceManager ======
	// ************************************************ //

	private final class NaiveLocationSourceManager extends
			LocationSourceManager {
		@Override
		public void activate() {
			// nothing to do here!
		}

		@Override
		public void deactivate() {
			// nothing to do here!
		}
	}

}
