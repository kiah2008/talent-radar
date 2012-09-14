package com.menatwork.test.location;

import junit.framework.TestCase;
import android.location.Location;

import com.menatwork.location.FixedLocationSource;
import com.menatwork.location.LocationSource;
import com.menatwork.location.LocationSourceManager;
import com.menatwork.location.LocationSourceManagerListener;

//TODO - this component should:
//	1- manage sweep frequency,
//	2- announce new actualizations,
//	3- accuracy criteria,
//	4- add/remove location sources,
//	5- activate/deactivate announcements - boris - 29/08/2012
public class LocationSourceManagerTest extends TestCase {

	public void testLocationIsTheOnlyOne() throws Exception {
		final LocationSourceManager locationSourceManager = new LocationSourceManager();

		final Location newLocation = new Location("stub");
		newLocation.setLatitude(0);
		newLocation.setLongitude(0);

		locationSourceManager.addLocationSource(new FixedLocationSource(newLocation));
		locationSourceManager.setMillisecondsBetweenUpdates(100);

		final MockLocationSourceManagerListener mockListener = new MockLocationSourceManagerListener();
		locationSourceManager.addListener(mockListener);

		locationSourceManager.activate();

		Thread.sleep(200);

		locationSourceManager.deactivate();

		mockListener.assertLocationReceived(newLocation);
	}

	private class MockLocationSourceManagerListener implements LocationSourceManagerListener {

		private Location locationReceived;

		private LocationSource locationSourceReceived;

		@Override
		public void onLocationUpdate(final Location location, final LocationSource locationSource) {
			this.locationReceived = location;
			this.locationSourceReceived = locationSource;
		}

		public void assertLocationReceived(final Location expectedLocation) {
			assertEquals("expectedLocation is locationReceived", expectedLocation, locationReceived);
		}

		public void assertLocationSourceReceived(final LocationSource expectedLocationSource) {
			assertEquals("expectedLocationSource is locationSourceReceived", expectedLocationSource,
					locationSourceReceived);
		}
	}
}
