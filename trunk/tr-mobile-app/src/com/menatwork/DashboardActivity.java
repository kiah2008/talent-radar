package com.menatwork;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class DashboardActivity extends ListActivity {

	private static final String[] DATA_KEYS = new String[] { //
	/*    */"timestamp", //
			"icon", //
			"header", //
			"description" };

	private static final int[] DATA_VIEW_IDS = new int[] { //
	/*    */R.id.dashboard_notification_timestamp, //
			R.id.dashboard_notification_icon, //
			R.id.dashboard_notification_header, //
			R.id.dashboard_notification_description };

	private final List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dashboard_notification_list_view);
		final SimpleAdapter adapter = new SimpleAdapter(this, //
				list, //
				R.layout.dashboard_notification_row_view, //
				DATA_KEYS, //
				DATA_VIEW_IDS);

		meteleUnaNotification();

		setListAdapter(adapter);
	}

	private void meteleUnaNotification() {
		final Map<String, Object> notification1 = new HashMap<String, Object>();
		notification1.put("timestamp", new Date());
		notification1.put("header", "Pinged! by Alejo");
		notification1.put("description", "Alejo just pinged you.");
		list.add(notification1);
	}

	public void notifyDataSetChanged() {
		getListAdapter().notifyDataSetChanged();
	}

	@Override
	public BaseAdapter getListAdapter() {
		return (BaseAdapter) super.getListAdapter();
	}

	public void addNofitication() {
		final HashMap<String, String> map = new HashMap<String, String>();
		// TODO - add properties to map - miguel - 10/09/2012
		list.add(map);
		notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		// TODO - lo que debe pasar cuando hacen click - miguel - 10/09/2012
		final Object o = this.getListAdapter().getItem(position);
		final String notification = o.toString();
		Toast.makeText(this,
				"You have chosen the notification: " + " " + notification,
				Toast.LENGTH_SHORT).show();
	}
}
