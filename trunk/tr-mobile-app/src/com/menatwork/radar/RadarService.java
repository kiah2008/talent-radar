package com.menatwork.radar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class RadarService extends Service {

	@Override
	public int onStartCommand(final Intent intent, final int flags,
			final int startId) {
		Toast.makeText(this, "Iniciando servicio de Radar", Toast.LENGTH_SHORT).show();
		// TODO - dunno if it has to do something - miguel - 25/07/2012
		return START_REDELIVER_INTENT;
	}

	@Override
	public IBinder onBind(final Intent intent) {
		// TODO - dunno if it has to do something - miguel - 25/07/2012
		Toast.makeText(this, "Bindeando servicio de Radar", Toast.LENGTH_SHORT).show();

		return null;
	}

	@Override
	public void onDestroy() {
		// TODO - Clean service state - miguel - 25/07/2012
		Toast.makeText(this, "Finalizando servicio de Radar", Toast.LENGTH_SHORT).show();
	}

}
