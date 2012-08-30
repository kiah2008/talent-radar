package com.menatwork;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

import com.menatwork.location.GpsLocationSource;
import com.menatwork.location.LocationSourceManager;
import com.menatwork.location.NetworkLocationSource;
import com.menatwork.radar.RadarActivity;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		initializeTabs();
		startRadarService();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		stopRadarService();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings:
			showSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showSettings() {
		final Intent settingsIntent = new Intent(this, SettingsActivity.class);
		startActivity(settingsIntent);
	}

	// ************************************************ //
	// ====== Radar Location ======
	// ************************************************ //

	private void stopRadarService() {
		// stopService(new Intent(this, RadarService.class));
		// Log.i("MainActivity", "stopping radar service");

		final TalentRadarApplication talentRadarApplication = getTalentRadarApplication();

		final LocationSourceManager locationSourceManager = talentRadarApplication.getLocationSourceManager();
		locationSourceManager.deactivate();

		talentRadarApplication.setLocationSourceManager(null);
	}

	private void startRadarService() {
		// startService(new Intent(this, RadarService.class));
		// Log.i("MainActivity", "starting radar service");

		// TODO - 20 seconds hardcoded - boris - 29/08/2012
		final LocationSourceManager locationSourceManager = new LocationSourceManager(20000);
		getTalentRadarApplication().setLocationSourceManager(locationSourceManager);

		// add some location sources (both network and gps)
		// TODO - location sources should be configurable - boris - 29/08/2012
		// TODO - 30 seconds hardcoded - boris - 29/08/2012
		locationSourceManager.addLocationSource(new NetworkLocationSource(this, 30000));
		locationSourceManager.addLocationSource(new GpsLocationSource(this, 30000));

		locationSourceManager.activate();
	}

	public TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) getApplication();
	}

	// ************************************************ //
	// ====== Managing Tabs ======
	// ************************************************ //

	private void initializeTabs() {
		addTab(DashboardActivity.class, //
				"dashboard", //
				"Dashboard", //
				R.drawable.icon_dashboard_tab);

		addTab(RadarActivity.class, //
				"radar", //
				"Radar", //
				R.drawable.icon_radar_tab);

		addTab(ProfileActivity.class, //
				"profile", //
				"Profile", //
				R.drawable.icon_profile_tab);
	}

	private void addTab(final Class<? extends Activity> tabClass, final String tabTag, final String tabLabel,
			final int tabIconId) {

		// get tab host
		final TabHost tabHost = getTabHost();

		// create tab spec
		final Drawable tabIcon = getResources().getDrawable(tabIconId);
		final Intent intent = new Intent(this, tabClass);

		final TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTag) //
				.setIndicator(tabLabel, tabIcon) //
				.setContent(intent);

		// add tabs pec to host
		tabHost.addTab(tabSpec);
	}

}
