package com.menatwork;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Resources res = getResources();

		// Drawables
		final TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab

		final Intent dashboardIntent = new Intent(this, DashboardActivity.class);
		spec = tabHost.newTabSpec("dashboard");
		spec.setIndicator("Dashboard", res.getDrawable(R.drawable.icon_dashboard_tab));
		spec.setContent(dashboardIntent);
		tabHost.addTab(spec);

		final Intent radarIntent = new Intent(this, RadarActivity.class);
		spec = tabHost.newTabSpec("radar").setIndicator("Radar", res.getDrawable(R.drawable.icon_radar_tab))
				.setContent(radarIntent);
		tabHost.addTab(spec);

		final Intent profileIntent = new Intent(this, ProfileActivity.class);
		spec = tabHost.newTabSpec("profile").setIndicator("Profile" //
				, res.getDrawable(R.drawable.icon_profile_tab) //
				).setContent(profileIntent);
		tabHost.addTab(spec);

	}

}
