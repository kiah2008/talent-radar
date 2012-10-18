package com.menatwork;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.menatwork.hunts.Hunt;
import com.menatwork.hunts.SimpleSkillHuntBuilder;
import com.menatwork.model.UserBuilder;
import com.menatwork.notification.TrNotification;

public class HuntsActivity extends ListActivity {

	private static final String KEY_TITLE = "header";
	private static final String KEY_QUANTITY = "quantity";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_INTENT = "intent";

	private static final String[] DATA_KEYS = new String[] { //
	/*    */KEY_TITLE, //
			KEY_QUANTITY, //
			KEY_DESCRIPTION };

	private static final int[] DATA_VIEW_IDS = new int[] { //
	/*    */R.id.hunt_header, //
			R.id.hunt_quantity, //
			R.id.hunt_description };

	// ************************************************ //
	// ====== Instance members ======
	// ************************************************ //

	private final List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
	private Handler mainLooperHandler;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hunts);
		initializeListAdapter();
		initializeAlreadyExistentHunts();
	}

	// ************************************************ //
	// ====== ListAdapter Stuff ======
	// ************************************************ //

	private void initializeListAdapter() {
		final SimpleAdapter adapter = new SimpleAdapter(this, //
				list, //
				R.layout.hunt_row_view, //
				DATA_KEYS, //
				DATA_VIEW_IDS);

		setListAdapter(adapter);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);
		final Map<String, Object> notification = (Map<String, Object>) getListAdapter()
				.getItem(position);
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
	private void initializeAlreadyExistentHunts() {
		final Collection<Hunt> hunts = getTalentRadarApplication()
				.getHuntingCriteriaEngine().getHunts();

		addHuntsAndNotify(hunts);

		// TODO - hardcoded data for demo purposes - miguel - 18/10/2012
		addHuntsAndNotify(
				SimpleSkillHuntBuilder
						.newInstance()
						.setTitle("Java developer")
						.addRequiredSkills("Java", "Maven", "Struts", "JPA",
								"Spring") //
						.addPreferredSkills("Git") //
						.addUsers() //
						.build(), //
				SimpleSkillHuntBuilder
						.newInstance()
						.setTitle("Webapp dev teamleader")
						.addRequiredSkills("PHP", "SVN", "Teamwork",
								"Management") //
						.addPreferredSkills("PM", "Scrum master") //
						.addUsers( //
								UserBuilder.newInstance() //
										.setId("1") //
										.setUserName("Gonzalo") //
										.setUserSurname("Balabasquer") //
										.setHeadline("Webapp Architect") //
										.build()) //
						.build());
	}

	// TODO - Beware, duplication of logic, what is that 'list.clear()' doing
	// there? - miguel - 18/10/2012
	public void addHunt(final Hunt hunt) {
		final Map<String, Object> huntMap = hunt2HuntMap(hunt);
		list.add(huntMap);
	}

	protected void addHunts(final Collection<? extends Hunt> hunts) {
		list.clear();
		for (final Hunt hunt : hunts)
			list.add(hunt2HuntMap(hunt));
	}

	/**
	 * Adds a Hunt to the list of notifications shown in the HuntsActivity,
	 * mapping it to the correct representation.
	 *
	 * This method ALSO notifies the ListAdapter for the list shown to be
	 * refreshed in screen.
	 *
	 * @param hunts
	 */
	protected void addHuntsAndNotify(final Collection<? extends Hunt> hunts) {
		mainLooperHandler = new Handler(this.getMainLooper());
		mainLooperHandler.post(new Runnable() {
			@Override
			public void run() {
				addHunts(hunts);
				notifyDataSetChanged();
			}
		});
	}

	protected void addHuntsAndNotify(final Hunt... hunts) {
		addHuntsAndNotify(Arrays.asList(hunts));
	}

	/**
	 * Maps a {@link TrNotification} to a map containing every value that will
	 * be showed in the Dashboard.
	 *
	 * @param hunt
	 * @return
	 */
	protected Map<String, Object> hunt2HuntMap(final Hunt hunt) {
		final Map<String, Object> notificationMap = new HashMap<String, Object>();
		notificationMap.put(KEY_TITLE, hunt.getTitle());
		notificationMap.put(KEY_QUANTITY, formatQuantity(hunt.getQuantity()));
		notificationMap.put(KEY_DESCRIPTION, hunt.getDescription());
		notificationMap.put(KEY_INTENT, hunt.getIntent());
		return notificationMap;
	}

	protected String formatQuantity(final int quantity) {
		return quantity > 0 ? "(" + quantity + ")" : "";
	}

	// ************************************************ //
	// ====== TalentRadarCommons ======
	// ************************************************ //

	public TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) getApplication();
	}
}
