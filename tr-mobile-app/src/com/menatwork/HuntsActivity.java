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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.menatwork.hunts.Hunt;
import com.menatwork.hunts.HuntingCriteriaEngine;
import com.menatwork.hunts.SimpleSkillHuntBuilder;
import com.menatwork.model.UserBuilder;
import com.menatwork.notification.TrNotification;

public class HuntsActivity extends ListActivity {

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

	private final List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
	private Handler mainLooperHandler;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hunts);
		initializeListAdapter();
		initializeListViewEvents();
		initializeAlreadyExistantHunts();
	}

	// ************************************************ //
	// ====== ListAdapter Stuff ======
	// ************************************************ //

	private void initializeListViewEvents() {
		final ListView listView = getListView();

		registerForContextMenu(listView);
		// listView.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(final AdapterView<?> adapterView,
		// final View view, final int pos, final long id) {
		// onLongListItemClick(view, pos, id);
		// return true;
		// }
		//
		// });
	}

	// protected void onLongListItemClick(final View view, final int position,
	// final long id) {
	// final Map<String, Object> huntMap = (Map<String, Object>)
	// getListAdapter()
	// .getItem(position);
	// final Hunt hunt = (Hunt) huntMap.get(KEY_HUNT);
	//
	// }

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.hunt_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.edit_hunt:
			editHunt(huntAt((int) info.id));
			return true;
		case R.id.remove_hunt:
			deleteHunt(huntAt((int) info.id));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void deleteHunt(final Hunt hunt) {
		getTalentRadarApplication().getHuntingCriteriaEngine().removeHunt(hunt);

		// delete it from the ui
		list.remove(findHuntMapFor(hunt));
		notifyDataSetChanged();
	}

	private Map<String, ?> findHuntMapFor(final Hunt hunt) {
		for (final Map<String, ?> huntMap : list)
			if (hunt.equals(huntMap.get(KEY_HUNT)))
				return huntMap;

		throw new NoSuchElementException("there's no hunt map with hunt = " + hunt);
	}

	private void editHunt(final Hunt hunt) {
		// start new hunt activity by passing the hunt id to be edited
	}

	private void initializeListAdapter() {
		final SimpleAdapter adapter = new SimpleAdapter(this, //
				list, //
				R.layout.hunt_row_view, //
				DATA_KEYS, //
				DATA_VIEW_IDS);

		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(final ListView l, final View v, final int position, final long id) {
		super.onListItemClick(l, v, position, id);

		final Hunt hunt = huntAt(position);

		if (hunt.getQuantity() <= 0)
			Toast.makeText(this, "¡Esta búsqueda no ha encontrado usuarios todavía!", Toast.LENGTH_SHORT)
					.show();
		else {
			final Intent intent = new Intent(this, MiniProfilesActivity.class);
			intent.putExtra(MiniProfilesActivity.EXTRAS_HUNT_ID, hunt.getId());
			startActivity(intent);
		}
	}

	@SuppressWarnings("unchecked")
	protected Hunt huntAt(final int position) {
		final Map<String, Object> huntMap = (Map<String, Object>) getListAdapter().getItem(position);
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
	private void initializeAlreadyExistantHunts() {
		final HuntingCriteriaEngine huntingCriteriaEngine = getTalentRadarApplication()
				.getHuntingCriteriaEngine();
		final Collection<Hunt> hunts = huntingCriteriaEngine.getHunts();

		addHuntsAndNotify(hunts);

		// TODO - hardcoded data for demo purposes - miguel - 18/10/2012
		huntingCriteriaEngine
				.addHunts(
						SimpleSkillHuntBuilder.newInstance().setId("2")
								.setTitle("Java developer")
								.addRequiredSkills("Java", "Maven", "Struts", "JPA", "Spring")
								//
								.addPreferredSkills("Git")
								//
								.addUsers(
										UserBuilder
												.newInstance()
												//
												.setId("1")
												//
												.setUserName("Alejo")
												//
												.setUserSurname("Abdala")
												//
												.setHeadline("QA Developer")
												//
												.setProfilePictureUrl(
														"http://m3.licdn.com/mpr/mprx/0_oivHPfsr3BHERRh1ECncPuu23rOIUYr1eXTBPuIrOnoRkVFPQL5Xj2xOfjYq4MA0IhB9yIJuE9-D")
												.build(), //
										UserBuilder
												.newInstance()
												//
												.setId("4")
												//
												.setUserName("Miguel")
												//
												.setUserSurname("Oliva")
												//
												.setHeadline("Ssr Java developer")
												//
												.setProfilePictureUrl(
														"http://m3.licdn.com/mpr/mprx/0_gPLYkt6SyeNSY1UcgB9TkANaYflmpzUcxcA3krFxTW5YiluBAvztoKPlKGAlx-sRyKF8wBJJYJLD")
												.build() //
								) //
								.build(), //
						SimpleSkillHuntBuilder.newInstance().setId("1").setTitle("Webapp dev teamleader")
								.addRequiredSkills("PHP", "SVN", "Teamwork", "Management") //
								.addPreferredSkills("PM", "Scrum master") //
								.addUsers( //
								// UserBuilder.newInstance() //
								// .setId("2") //
								// .setUserName("Gonzalo") //
								// .setUserSurname("Balabasquer") //
								// .setHeadline("Webapp Architect") //
								// .setProfilePictureUrl("http:\\/\\/m3.licdn.com\\/mpr\\/mprx\\/0_imOA6V2nneFoQTEMiuyG6ZpQswisXTIMT2om6ZaQ5mcERLpJ7eaje4seNL_5bQovGSpfd0pnr2ks")
								// .build() //
								) //
								.build());
		addHuntsAndNotify(huntingCriteriaEngine.getHunts());
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
		final Map<String, Object> huntMap = new HashMap<String, Object>();
		huntMap.put(KEY_TITLE, hunt.getTitle());
		huntMap.put(KEY_QUANTITY, formatQuantity(hunt.getQuantity()));
		huntMap.put(KEY_DESCRIPTION, hunt.getDescription());
		huntMap.put(KEY_HUNT, hunt);
		return huntMap;
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
