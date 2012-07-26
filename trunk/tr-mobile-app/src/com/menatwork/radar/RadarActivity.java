package com.menatwork.radar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.menatwork.R;
import com.menatwork.TalentRadarActivity;
import com.menatwork.radar.RadarService.RadarBinder;

public class RadarActivity extends TalentRadarActivity {

	private final ServiceConnection serviceConnection = new RadarServiceConnection();
	private boolean boundToRadarService = false;

	@Override
	protected int getViewLayoutId() {
		return R.layout.radar;
	}

	@Override
	protected void setupButtons() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void findViewElements() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onStart() {
		super.onStart();

		// Binding with RadarService
		final Intent intent = new Intent(this, RadarService.class);
		bindService(intent, serviceConnection, Context.BIND_NOT_FOREGROUND);

	}

	@Override
	protected void onStop() {
		super.onStop();

		if (boundToRadarService) {
			unbindService(serviceConnection);
			boundToRadarService = false;
		}
	}

	private final class RadarServiceConnection implements ServiceConnection {
		@Override
		public void onServiceConnected(final ComponentName className, final IBinder service) {
			// We've bound to RadarService, cast the IBinder and get
			// RadarService instance
			final RadarBinder binder = (RadarBinder) service;
			final RadarService radarService = binder.getService();
			// TODO - the idea is to tell the service to add this activity to
			// some kind of listenership so that it can tell us when new user
			// notificaciones arrive - boris - 25/07/2012

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
