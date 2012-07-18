package com.menatwork;

import com.menatwork.model.User;

import android.app.Application;
import android.util.Log;

public class TalentRadarApplication extends Application {

	private User localUser;

	public void loadLocalUser(User user) {
		Log.d("TalentRadarApplication", "loadLocalUser() " + user);
		this.localUser = user;
	}

	public User getLocalUser() {
		return localUser;
	}

}
