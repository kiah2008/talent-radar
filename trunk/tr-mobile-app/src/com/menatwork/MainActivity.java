package com.menatwork;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.mentatwork.R;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Resources res = getResources(); // Resource object to get
		// Drawables
		final TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, DashboardActivity.class);
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("dashboard").setIndicator("Dashboard" //
				// , res.getDrawable(R.drawable.ic_tab_artists) //
				).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, RadarActivity.class);
		spec = tabHost.newTabSpec("radar").setIndicator("Radar" //
				// , res.getDrawable(R.drawable.ic_tab_albums) //
				).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ProfileActivity.class);
		spec = tabHost.newTabSpec("profile").setIndicator("Profile" //
				// , res.getDrawable(R.drawable.ic_tab_songs) //
				).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(2);
	}

}