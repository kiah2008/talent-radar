package com.menatwork;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.menatwork.location.LocationSourceManager;
import com.menatwork.model.User;
import com.menatwork.skills.DefaultSkillButtonFactory;
import com.menatwork.skills.SkillButtonFactory;

public class TalentRadarApplication extends Application {

	private static final String EMULATOR_BUILD_PRODUCT = "sdk";

	private User localUser;
	private final SkillButtonFactory skillButtonFactory;
	private final Object deviceRegistrationLock = new Object();
	private String deviceRegistrationId;

	private LocationSourceManager locationSourceManager;

	private static Context context;

	public TalentRadarApplication() {
		super();
		context = this;
		this.skillButtonFactory = DefaultSkillButtonFactory.newInstance();
	}

	public static Context getContext() {
		return context;
	}

	public void loadLocalUser(final User user) {
		Log.d("TalentRadarApplication", "loadLocalUser() " + user);
		this.localUser = user;
	}

	public User getLocalUser() {
		return localUser;
	}

	public SkillButtonFactory getSkillButtonFactory() {
		return skillButtonFactory;
	}

	public String getDeviceRegistrationId() {
		if (deviceRegistrationId == null) {
			if (!this.isDeviceRegistered())
				throw new RuntimeException("Device not registered, deviceRegistrationId == null");
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
					Log.w("TalentRadarApp", "Timeout registering device, continuing excecution...");
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

	/**
	 * Tells whether the application is running on an emulator rather than a
	 * real phone.
	 * 
	 * @return <code>true</code> - if running on emulator
	 */
	public boolean isRunningOnEmulator() {
		// XXX - should work for version 2.3.3 and above and the generic
		// google's emulator (beware of intel's and other implementations) -
		// miguel - 27/08/2012
		return EMULATOR_BUILD_PRODUCT.equals(Build.PRODUCT);
	}

	public LocationSourceManager getLocationSourceManager() {
		return locationSourceManager;
	}

	public void setLocationSourceManager(final LocationSourceManager locationSourceManager) {
		this.locationSourceManager = locationSourceManager;
	}
}
