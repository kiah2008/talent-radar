package com.menatwork;

import com.menatwork.model.User;
import com.menatwork.skills.DefaultSkillButtonFactory;
import com.menatwork.skills.SkillButtonFactory;

import android.app.Application;
import android.util.Log;

public class TalentRadarApplication extends Application {

	private User localUser;
	private SkillButtonFactory skillButtonFactory;

	public TalentRadarApplication() {
		super();
		this.skillButtonFactory = DefaultSkillButtonFactory.newInstance();
	}

	public void loadLocalUser(User user) {
		Log.d("TalentRadarApplication", "loadLocalUser() " + user);
		this.localUser = user;
	}

	public User getLocalUser() {
		return localUser;
	}

	public SkillButtonFactory getSkillButtonFactory() {
		return skillButtonFactory;
	}

}
