package com.menatwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.menatwork.hunts.DefaultHunt;
import com.menatwork.hunts.Hunt;
import com.menatwork.hunts.HuntingCriteriaEngine;
import com.menatwork.hunts.HuntingCriteriaListener;
import com.menatwork.notification.TrNotification;

public class HuntsActivity extends ListActivity implements
		HuntingCriteriaListener {

	private static final String KEY_TITLE = "header";
	private static final String KEY_QUANTITY = "quantity";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_HUNT = "hunt";

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

	private final List<Map<String, ?>> huntMaps = new ArrayList<Map<String, ?>>();
	private Handler mainLooperHandler;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hunts);
		initializeListAdapter();
		initializeListViewEvents();

		getHuntingCriteriaEngine().addListener(this);
		DefaultHunt.getInstance().addListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initializeAlreadyExistentHunts();
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.hunt_menu, menu);
		super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_hunt:
			startActivity(new Intent(this, NewHuntActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// ************************************************ //
	// ====== ListAdapter Stuff ======
	// ************************************************ //

	private void initializeListViewEvents() {
		final ListView listView = getListView();

		registerForContextMenu(listView);
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v,
			final ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		getMenuInflater().inflate(R.menu.hunt_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.edit_hunt:
			editHunt(huntAt((int) info.id));
			return true;
		case R.id.remove_hunt:
			removeHunt(huntAt((int) info.id));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void removeHunt(final Hunt hunt) {
		if (isDefaultHunt(hunt))
			Toast.makeText(this,
					"No es posible remover la búsqueda por defecto",
					Toast.LENGTH_SHORT).show();
		else {
			getHuntingCriteriaEngine().removeHunt(hunt);

			// delete it from the ui
			huntMaps.remove(findHuntMapFor(hunt));
			notifyDataSetChanged();
		}
	}

	private Map<String, ?> findHuntMapFor(final Hunt hunt) {
		for (final Map<String, ?> huntMap : huntMaps)
			if (hunt.equals(huntMap.get(KEY_HUNT)))
				return huntMap;

		throw new NoSuchElementException("there's no hunt map with hunt = "
				+ hunt);
	}

	private void editHunt(final Hunt hunt) {
		if (isDefaultHunt(hunt))
			Toast.makeText(this,
					"No es posible editar la búsqueda por defecto",
					Toast.LENGTH_SHORT).show();
		else {
			final Intent intent = new Intent(this, NewHuntActivity.class);
			intent.putExtra(NewHuntActivity.EXTRAS_HUNT_ID, hunt.getId());
			startActivity(intent);
		}
	}

	private void initializeListAdapter() {
		final SimpleAdapter adapter = new SimpleAdapter(this, //
				huntMaps, //
				R.layout.hunt_row_view, //
				DATA_KEYS, //
				DATA_VIEW_IDS);

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		super.onListItemClick(l, v, position, id);

		final Hunt hunt = huntAt(position);

		if (hunt.getUsersQuantity() <= 0)
			Toast.makeText(this, R.string.hunts_empty, Toast.LENGTH_SHORT)
					.show();
		else {
			final Intent intent = new Intent(this,
					HuntMiniProfilesActivity.class);
			intent.putExtra(HuntMiniProfilesActivity.EXTRAS_HUNT_ID,
					hunt.getId());
			startActivity(intent);
		}
	}

	@SuppressWarnings("unchecked")
	protected Hunt huntAt(final int position) {
		final Map<String, Object> huntMap = (Map<String, Object>) getListAdapter()
				.getItem(position);
		final Hunt hunt = (Hunt) huntMap.get(KEY_HUNT);
		return hunt;
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
		final Collection<Hunt> hunts = getHuntingCriteriaEngine().getHunts();

		removeAllHunts();
		// TODO - validate! default hunt should be included in hunting engine -
		// miguel - 04/11/2012
		// addHuntsAndNotify(DefaultHunt.getInstance());
		addHuntsAndNotify(hunts);
	}

	private void removeAllHunts() {
		huntMaps.clear();
	}

	public void addHunt(final Hunt hunt) {
		final Map<String, Object> huntMap = hunt2HuntMap(hunt);
		huntMaps.add(huntMap);
	}

	protected void addHunts(final Collection<? extends Hunt> hunts) {
		for (final Hunt hunt : hunts)
			addHunt(hunt);
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
	 * be showed in the activity.
	 *
	 * @param hunt
	 * @return
	 */
	protected Map<String, Object> hunt2HuntMap(final Hunt hunt) {
		final Map<String, Object> huntMap = new HashMap<String, Object>();
		huntMap.put(KEY_TITLE, hunt.getTitle());
		huntMap.put(KEY_QUANTITY, formatQuantity(hunt.getUsersQuantity()));
		huntMap.put(KEY_DESCRIPTION, hunt.getDescription());
		huntMap.put(KEY_HUNT, hunt);
		return huntMap;
	}

	protected String formatQuantity(final int quantity) {
		return quantity > 0 ? "(" + quantity + ")" : "";
	}

	private boolean isDefaultHunt(final Hunt hunt) {
		return hunt instanceof DefaultHunt;
	}

	// ****************************************************** //
	// ====== HuntinCriteriaListener implementation ======
	// ****************************************************** //

	@Override
	public void onHuntsSateModified() {
		initializeAlreadyExistentHunts();
	}

	// ************************************************ //
	// ====== TalentRadarCommons ======
	// ************************************************ //

	private TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) getApplication();
	}

	private HuntingCriteriaEngine getHuntingCriteriaEngine() {
		return getTalentRadarApplication().getHuntingCriteriaEngine();
	}
}
