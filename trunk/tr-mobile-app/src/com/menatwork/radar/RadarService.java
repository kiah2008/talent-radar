package com.menatwork.radar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RadarService extends Service {

	@Override
	public IBinder onBind(final Intent intent) {
		throw new UnsupportedOperationException("Service.onBind");
	}

}
