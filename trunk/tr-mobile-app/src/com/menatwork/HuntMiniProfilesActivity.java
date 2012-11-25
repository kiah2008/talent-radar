package com.menatwork;

import java.util.LinkedList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.menatwork.hunts.Hunt;
import com.menatwork.miniprofile.MiniProfileAdapter;
import com.menatwork.miniprofile.MiniProfileItemRow;
import com.menatwork.miniprofile.MiniProfileListController;
import com.menatwork.model.ProxyUser;
import com.menatwork.model.User;

/**
 * Shows a list of miniprofiles contained in a given hunt.
 *
 * @author miguel
 *
 */
public class HuntMiniProfilesActivity extends GuiTalentRadarActivity {

	public static final String EXTRAS_HUNT_ID = "extras-hunt-id";

	private MiniProfileListController listController;

	@Override
	protected int getViewLayoutId() {
		return R.layout.mini_profile_list;
	}

	@Override
	protected void setupButtons() {
		// Nothing to do
	}

	@Override
	protected void findViewElements() {
		// Nothing to do
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		new LoadHuntUsersTask(this).execute();
	}

	private void loadHuntUsers() {
		if (hasUsersToShow()) {
			final List<User> users = getUsers();

			final List<MiniProfileItemRow> miniProfiles = new LinkedList<MiniProfileItemRow>();
			for (final User user : users)
				miniProfiles.add(new MiniProfileItemRow(user));

			listController = new MiniProfileListController(this,
					R.id.mini_profiles_list_view, miniProfiles, true);

			final ListView listView = listController.getListView();
			registerForContextMenu(listView);

			// listView.setOnCreateContextMenuListener(new
			// OnCreateContextMenuListener() {
			//
			// @Override
			// public void onCreateContextMenu(final ContextMenu menu,
			// final View v, final ContextMenuInfo menuInfo) {
			// getMenuInflater().inflate(
			// R.menu.hunt_miniprofile_context_menu, menu);
			// }
			// });
		}
	}

	@Override
	public void onCreateContextMenu(final ContextMenu menu, final View v,
			final ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.hunt_miniprofile_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(final MenuItem item) {
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.remove_miniprofile:
			removeUser(userIdFromMiniprofileAt((int) info.id));
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void removeUser(final String userId) {
		final boolean userSuccessfullyRemoved = getHunt().removeUserWithId(
				userId);
		if (userSuccessfullyRemoved)
			Toast.makeText(
					//
					this, //
					String.format(
							getString(R.string.hunt_miniprofile_context_menu_remove_successfully),
							getUserById(userId).getDisplayableShortName()), //
					Toast.LENGTH_SHORT //
			).show();

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// listController.updateList(usersToMiniProfiles(getUsers()));
				listController.removeMiniProfileBeUserId(userId);
				// listController.notifyDataSetChanged();
			}
		});
	}

	private List<MiniProfileItemRow> usersToMiniProfiles(final List<User> users) {
		final List<MiniProfileItemRow> rows = new LinkedList<MiniProfileItemRow>();

		for (final User user : users) {
			rows.add(new MiniProfileItemRow(user));
		}

		return rows;
	}

	private String userIdFromMiniprofileAt(final int position) {
		final MiniProfileItemRow miniprofile = ((MiniProfileAdapter) listController
				.getListView().getAdapter()).getItem(position);
		final String userId = miniprofile.getUserId();
		return userId;
	}

	private boolean hasUsersToShow() {
		final Bundle extras = getExtras();
		return extras != null //
				&& extras.containsKey(EXTRAS_HUNT_ID) //
				&& extras.getString(EXTRAS_HUNT_ID) != null;
	}

	private Bundle getExtras() {
		return getIntent().getExtras();
	}

	private List<User> getUsers() {
		return getHunt().getUsers();
	}

	private Hunt getHunt() {
		final Bundle extras = getExtras();
		return getTalentRadarApplication().getHuntingCriteriaEngine()
				.findHuntById((String) extras.get(EXTRAS_HUNT_ID));
	}

	// ************************************************ //
	// ====== LoadHuntUsersTask ======
	// ************************************************ //

	private final class LoadHuntUsersTask extends AsyncTask<Void, Void, Void> {

		private final HuntMiniProfilesActivity activity;
		private ProgressDialog progressDialog;

		public LoadHuntUsersTask(final HuntMiniProfilesActivity activity) {
			this.activity = activity;
		}

		@Override
		protected Void doInBackground(final Void... params) {
			for (final User user : activity.getUsers())
				if (user instanceof ProxyUser) {
					final ProxyUser proxy = (ProxyUser) user;
					proxy.loadRealUserIfNeeded();
				}

			return null;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(activity, "",
					activity.getString(R.string.generic_wait));
		}

		@Override
		protected void onPostExecute(final Void result) {
			progressDialog.dismiss();
			activity.loadHuntUsers();
		}

	}

}
