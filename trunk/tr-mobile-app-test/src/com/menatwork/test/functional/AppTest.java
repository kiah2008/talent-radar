package com.menatwork.test.functional;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.SlidingDrawer;

import com.jayway.android.robotium.solo.Solo;
import com.menatwork.MainActivity;
import com.menatwork.R;

public class AppTest extends ActivityInstrumentationTestCase2<MainActivity> {

	protected Solo solo;

	public AppTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	public void testSaraza() throws Exception {
		solo.assertCurrentActivity("current activity", MainActivity.class);

		final SlidingDrawer slidingDrawer = (SlidingDrawer) solo.getView(R.id.radar_sliding_drawer);
		solo.setSlidingDrawer(slidingDrawer, Solo.OPENED);

		assertFalse(solo.getCurrentListViews().isEmpty());
		// ListView miniProfiles = (ListView)
		// solo.getView(R.id.radar_mini_profiles_list_view);
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
}
