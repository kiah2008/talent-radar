package com.menatwork;

import java.util.LinkedList;
import java.util.List;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

import com.menatwork.miniprofile.MiniProfileItemRow;
import com.menatwork.miniprofile.MiniProfileListController;
import com.menatwork.model.User;
import com.menatwork.radar.Radar;
import com.menatwork.radar.RadarListener;

public class RadarActivity extends GuiTalentRadarActivity implements
		RadarListener {

	// TODO - used for the RadarService implementation -> Not used right now! -
	// boris - 17/08/2012
	private final ServiceConnection serviceConnection = new RadarServiceConnection();
	private boolean boundToRadarService = false;

	private SlidingDrawer slidingDrawer;

	private final Object miniProfileItemsLock = new Object();
	private List<MiniProfileItemRow> miniProfileItems = new LinkedList<MiniProfileItemRow>();

	private MiniProfileListController listController;

	@Override
	protected int getViewLayoutId() {
		return R.layout.radar;
	}

	@Override
	protected void findViewElements() {
		slidingDrawer = findViewById(R.id.radar_sliding_drawer,
				SlidingDrawer.class);
	}

	@Override
	protected void setupButtons() {
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				Log.d("RadarActivity", "Show surrounders button pressed");
				showMiniProfileList();
			}
		});
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		super.postCreate(savedInstanceState);

		final Radar radar = getTalentRadarApplication().getRadar();
		radar.addListeners(this);

		listController = new MiniProfileListController(RadarActivity.this,
				R.id.radar_mini_profiles_list_view,
				new LinkedList<MiniProfileItemRow>());
	}

	// ************************************************ //
	// ====== RadarListener implementation ======
	// ************************************************ //

	@Override
	public void onNewSurroundingUsers(
			final List<? extends User> surroundingUsers) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				updateSurroundingContacts(surroundingUsers);
			}
		});
	}

	// ************************************************ //
	// ====== State changes ======
	// ************************************************ //

	@Override
	protected void onStart() {
		super.onStart();

		// Binding with RadarService
		// final Intent intent = new Intent(this, RadarService.class);
		// bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		// Log.i("RadarActivity", "binding service");
	}

	@Override
	protected void onStop() {
		super.onStop();

		// if (boundToRadarService) {
		// unbindService(serviceConnection);
		// boundToRadarService = false;
		// }
	}

	public synchronized void updateSurroundingContacts(
			final List<? extends User> parseSurroundingUsers) {
		final List<MiniProfileItemRow> newMiniProfileItems = new LinkedList<MiniProfileItemRow>();
		for (final User user : parseSurroundingUsers)
			newMiniProfileItems.add(new MiniProfileItemRow(user));

		// beware of concurrent use of this collection
		synchronized (miniProfileItemsLock) {
			miniProfileItems = newMiniProfileItems;
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listController.updateList(newMiniProfileItems);
			}
		});
	}

	protected void showMiniProfileList() {
		LinkedList<MiniProfileItemRow> copy;

		// beware of concurrent use of this collection
		synchronized (miniProfileItemsLock) {
			copy = new LinkedList<MiniProfileItemRow>(miniProfileItems);
		}

		listController.updateList(copy);
	}

	/* *************************************************** */
	/* ********* RadarServiceConnection ****************** */
	/* *************************************************** */

	private final class RadarServiceConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(final ComponentName className,
				final IBinder service) {
			// We've bound to RadarService, cast the IBinder and get
			// RadarService instance
			// final RadarBinder binder = (RadarBinder) service;
			// final RadarService radarService = binder.getService();

			// radarService.addRadarServiceListener(RadarActivity.this);

			boundToRadarService = true;
			Log.i("radar activity", "radar service connected");
		}

		@Override
		public void onServiceDisconnected(final ComponentName componentName) {
			Log.e("radar activity", "radar service disconnected");
			boundToRadarService = false;
		}
	}

}