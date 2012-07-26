package com.menatwork.radar;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class RadarService extends Service {

	/**
	 * Binder given to clients
	 */
	private final RadarBinder binder = new RadarBinder();

	@Override
	public int onStartCommand(final Intent intent, final int flags, final int startId) {
		// TODO - should connect or something here - boris - 25/07/2012
		return Service.START_REDELIVER_INTENT;
	}

	@Override
	public IBinder onBind(final Intent intent) {
		Toast.makeText(this, "Bindeando servicio de Radar", Toast.LENGTH_SHORT).show();
		return binder;
	}

	@Override
	public void onDestroy() {
		// TODO - should free every fuckin' resource in the app - boris -
		// 25/07/2012
		Toast.makeText(this, "Finalizando servicio de Radar", Toast.LENGTH_SHORT).show();
	}

	/**
	 * Class used for the client Binder. Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */
	public class RadarBinder extends Binder {
		RadarService getService() {
			return RadarService.this;
		}
	}
}
