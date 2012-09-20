package com.menatwork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.menatwork.notification.TrNotification;
import com.menatwork.notification.TrNotificationListener;
import com.menatwork.notification.TrNotificationManager;

public class DashboardActivity extends ListActivity implements TrNotificationListener {

	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_ICON = "icon";
	private static final String KEY_HEADER = "header";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_INTENT = "intent";

	private static final String[] DATA_KEYS = new String[] { //
	/*    */KEY_TIMESTAMP, //
			KEY_ICON, //
			KEY_HEADER, //
			KEY_DESCRIPTION };

	private static final int[] DATA_VIEW_IDS = new int[] { //
	/*    */R.id.dashboard_notification_timestamp, //
			R.id.dashboard_notification_icon, //
			R.id.dashboard_notification_header, //
			R.id.dashboard_notification_description };

	// ************************************************ //
	// ====== Instance members ======
	// ************************************************ //

	private final List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
	private Handler mainLooperHandler;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_notification_list_view);
		initializeListAdapter();
		initializeAlreadyExistentNotifications();
		suscribeToNewNotications();
	}

	@Override
	protected void onDestroy() {
		unsuscribeFromNofitications();
		super.onDestroy();
	}

	// ************************************************ //
	// ====== ListAdapter Stuff ======
	// ************************************************ //

	private void initializeListAdapter() {
		final SimpleAdapter adapter = new SimpleAdapter(this, //
				list, //
				R.layout.dashboard_notification_row_view, //
				DATA_KEYS, //
				DATA_VIEW_IDS);

		setListAdapter(adapter);

		// addNofiticationAndNotify(TrNotificationBuilder.newInstance() //
		// .setType(TrNotificationType.PING) //
		// .setDate(new Date()) //
		// .setDescription("le descriptionçç") //
		// .setHeader("Pinged! by Pomodoro").build());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		final Map<String, Object> notification = (Map<String, Object>) getListAdapter().getItem(position);
		final Intent intent = (Intent) notification.get(KEY_INTENT);
		
		if (intent != null)
			startActivity(intent);
	}

	public void notifyDataSetChanged() {
		getListAdapter().notifyDataSetChanged();
	}

	@Override
	public BaseAdapter getListAdapter() {
		return (BaseAdapter) super.getListAdapter();
	}

	// ************************************************ //
	// ====== TrNotificationListener ======
	// ************************************************ //

	/**
	 * Adds every notification that has been already registered by the
	 * notification manager and shows it in the list.
	 */
	private void initializeAlreadyExistentNotifications() {
		final Collection<TrNotification> notifications = getTalentRadarApplication().getNotificationManager()
				.getNotifications();

		addNofiticationsAndNotify(notifications);

		notifyDataSetChanged();
	}

	/**
	 * Suscribes to new notifications from the notification manager.
	 */
	private void suscribeToNewNotications() {
		getTalentRadarApplication().getNotificationManager().addNotificationListener(this);
	}

	/**
	 * Unsuscribes from new notifications from the notification manager.
	 */
	private void unsuscribeFromNofitications() {
		getTalentRadarApplication().getNotificationManager().removeNotificationListener(this);
	}

	@Override
	public void onNewNotification(final TrNotificationManager notificationManager,
			final TrNotification notification) {
		addNofiticationsAndNotify(notificationManager.getNotifications());
	}

	/**
	 * Adds a TrNotification to the list of notifications shown in the
	 * Dashboard, mapping it to the correct representation.
	 * 
	 * <b>NOTE:</b> This method DOESN'T notify the ListAdapter that it has to
	 * refresh its view. You should call
	 * {@link DashboardActivity#notifyDataSetChanged()} afterwards or use
	 * {@link DashboardActivity#addNofiticationAndNotify(TrNotification)}
	 * instead.
	 * 
	 * @param notification
	 */
	protected void addNofitication(final TrNotification notification) {
		final Map<String, Object> notificationMap = trNotification2NotificationMap(notification);
		list.add(notificationMap);
	}

	protected void addNotifications(final Collection<TrNotification> notifications) {
		list.clear();
		for (final TrNotification trNotification : notifications)
			list.add(trNotification2NotificationMap(trNotification));
	}

	/**
	 * Adds a TrNotification to the list of notifications shown in the
	 * Dashboard, mapping it to the correct representation.
	 * 
	 * This method ALSO notifies the ListAdapter for the list shown to be
	 * refreshed in screen.
	 * 
	 * @param notification
	 */
	protected void addNofiticationsAndNotify(final Collection<TrNotification> notifications) {
		mainLooperHandler = new Handler(this.getMainLooper());
		mainLooperHandler.post(new Runnable() {
			@Override
			public void run() {
				addNotifications(notifications);
				notifyDataSetChanged();
			}
		});
	}

	/**
	 * Maps a {@link TrNotification} to a map containing every value that will
	 * be showed in the Dashboard.
	 * 
	 * @param notification
	 * @return
	 */
	protected Map<String, Object> trNotification2NotificationMap(final TrNotification notification) {
		final Map<String, Object> notificationMap = new HashMap<String, Object>();
		notificationMap.put(KEY_TIMESTAMP, notification.getDate());
		notificationMap.put(KEY_HEADER, notification.getHeader());
		notificationMap.put(KEY_DESCRIPTION, notification.getDescription());
		notificationMap.put(KEY_ICON, notification.getIcon());
		notificationMap.put(KEY_INTENT, notification.getIntent());
		return notificationMap;
	}

	// ************************************************ //
	// ====== TalentRadarCommons ======
	// ************************************************ //

	public TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) getApplication();
	}
}
