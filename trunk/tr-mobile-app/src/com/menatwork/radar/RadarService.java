package com.menatwork.radar;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.menatwork.TalentRadarApplication;
import com.menatwork.service.ShareLocationAndGetUsers;
import com.menatwork.service.ShareLocationAndGetUsersResponse;

public class RadarService extends Service {

	/**
	 * Binder given to clients to interact with the service
	 */
	private final RadarBinder binder = new RadarBinder();

	/**
	 * Listeners to notify when something interesting happens
	 * {@link RadarServiceListener}
	 */
	private final List<RadarServiceListener> listeners = new LinkedList<RadarServiceListener>();

	private Thread thread;

	@Override
	public void onCreate() {
		super.onCreate();

		thread = new Thread(new RadarServiceRunnable(), "RadarService");
	}

	@Override
	public int onStartCommand(final Intent intent, final int flags,
			final int startId) {
		super.onStartCommand(intent, flags, startId);

		thread.start();

		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(final Intent intent) {
		Toast.makeText(this, "Bindeando servicio de Radar", Toast.LENGTH_SHORT)
				.show();

		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// TODO - should free every fuckin' resource in the app - boris -
		// 25/07/2012
		Toast.makeText(this, "Finalizando servicio de Radar",
				Toast.LENGTH_SHORT).show();
	}

	/* ************************************************* */
	/* ********* RadarServiceRunnable ****************** */
	/* ************************************************* */

	private final class RadarServiceRunnable implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					try {
						// TODO - we've got to take the gps location first -
						// miguel - 27/07/2012

						// TODO - where do i get my own user id from? - miguel -
						// 27/07/2012
							// XXX - take a look at the snippet down here 
							((TalentRadarApplication)RadarService.this.getApplication()).getLocalUser().getId();
							// alme - 07/08/2012

						// TODO - fuckin duration? - miguel - 27/07/2012
						final ShareLocationAndGetUsers serviceCall = ShareLocationAndGetUsers
								.newInstance(RadarService.this, "1", 0, 0, 30);

						handleResponse(serviceCall.execute());
					} catch (final JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (final IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// TODO - los 5 segundos deberían venir por configuración -
					// miguel - 28/07/2012
					Thread.sleep(5000);
				} // main while

			} catch (final InterruptedException e) {
				Log.d("RadarSeriviceRunnable", "Thread interrupted");
			}
		}

		private ShareLocationAndGetUsersResponse handleResponse(
				final ShareLocationAndGetUsersResponse response) {
			Log.d("RadarSericeRunnable", "JSON Response");
			Log.d("RadarSericeRunnable", response.toString());
			return response;
		}
	}

	/* ************************************************** */
	/* ********* RadarServiceListener ******************* */
	/* ************************************************** */

	public void addRadarServiceListener(final RadarServiceListener listener) {
		listeners.add(listener);
	}

	public void removeRadarServiceListener(final RadarServiceListener listener) {
		listeners.remove(listener);
	}

	/* ***************************************** */
	/* ********* RadarBinder ******************* */
	/* ***************************************** */

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
