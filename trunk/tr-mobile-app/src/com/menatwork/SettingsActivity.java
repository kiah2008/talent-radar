package com.menatwork;

import android.os.Bundle;

public class SettingsActivity extends GuiTalentRadarActivity {

	@Override
	protected void setupButtons() {
		// TODO Auto-generated constructor stub
		// save changes -> should take what's in screen and persist it (also
		// notify about the update)
		// reset changes -> should take what's persisted and reload settings
		// screen
	}

	@Override
	protected void findViewElements() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.settings;
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		// TODO - load everything from persisted - boris - 30/08/2012
	}
}
