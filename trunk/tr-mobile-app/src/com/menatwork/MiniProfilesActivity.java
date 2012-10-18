package com.menatwork;

import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;

import com.menatwork.miniprofile.MiniProfileItemRow;
import com.menatwork.miniprofile.MiniProfileListController;
import com.menatwork.model.User;

public class MiniProfilesActivity extends GuiTalentRadarActivity {

	public static final String EXTRAS_HUNT_ID = "extras-users";

	private MiniProfileListController listController;

	@Override
	protected int getViewLayoutId() {
		return R.layout.mini_profile_list;
	}

	@Override
	protected void setupButtons() {
	}

	@Override
	protected void findViewElements() {
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		final Bundle extras = getIntent().getExtras();
		if (hasUsersToShow(extras)) {
			final List<User> users = getUsers(extras);

			final List<MiniProfileItemRow> miniProfiles = new LinkedList<MiniProfileItemRow>();
			for (final User user : users)
				miniProfiles.add(new MiniProfileItemRow(user));

			listController = new MiniProfileListController(this,
					R.id.mini_profiles_list_view, miniProfiles);
		} else {
			// notify there are no users
		}
	}

	private boolean hasUsersToShow(final Bundle extras) {
		return extras != null //
				&& extras.containsKey(EXTRAS_HUNT_ID) //
				&& extras.getString(EXTRAS_HUNT_ID) != null;
	}

	private List<User> getUsers(final Bundle extras) {
		return getTalentRadarApplication().getHuntingCriteriaEngine()
				.getHuntById((String) extras.get(EXTRAS_HUNT_ID)).getUsers();
	}

}
