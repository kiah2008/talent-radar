package com.menatwork.hunts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONException;

import android.util.Log;

import com.menatwork.TalentRadarApplication;
import com.menatwork.model.User;
import com.menatwork.radar.RadarListener;
import com.menatwork.service.GetUserSkills;
import com.menatwork.service.response.GetUserSkillsResponse;

public class HuntingCriteriaEngine implements RadarListener {

	private final Collection<Hunt> hunts;
	private final Collection<HuntingCriteriaListener> listeners;

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public static HuntingCriteriaEngine withHunts(final Hunt... hunts) {
		final HuntingCriteriaEngine engine = new HuntingCriteriaEngine();
		engine.addHunts(hunts);
		return engine;
	}

	public static HuntingCriteriaEngine newInstance() {
		return HuntingCriteriaEngine.withHunts(new Hunt[0]);
	}

	protected HuntingCriteriaEngine() {
		hunts = new ArrayList<Hunt>();
		listeners = new LinkedList<HuntingCriteriaListener>();
	}

	// ************************************************ //
	// ====== Huntcessors ======
	// ************************************************ //

	public Hunt findHuntById(final String id) {
		for (final Hunt hunt : hunts)
			if (hunt.getId().equals(id))
				return hunt;

		throw new NoSuchElementException("there's no hunt with id = " + id);
	}

	public Collection<Hunt> getHunts() {
		return hunts;
	}

	public void addHunts(final Hunt... hunts) {
		for (final Hunt hunt : hunts)
			addHunt(hunt);
	}

	public void addHunt(final Hunt hunt) {
		hunts.add(hunt);
	}

	public void removeHunt(final Hunt hunt) {
		hunts.remove(hunt);
	}

	// ************************************************ //
	// ====== RadarListener implementation ======
	// ************************************************ //

	@Override
	public void onNewSurroundingUsers(
			final List<? extends User> surroundingUsers) {
		if (hunts.isEmpty())
			return;

		boolean usersAdded = false;

		for (final User user : surroundingUsers) {
			initializeUserSkills(user);

			for (final Hunt hunt : hunts) {
				final boolean userAdded = hunt.addUserIfCriteriaMatched(user);
				// if the user is added, usersAdded will become true [so we'll
				// need to notify]
				usersAdded |= userAdded;
			}
		}

		if (usersAdded)
			notifyUsersAdded();
	}

	private void notifyUsersAdded() {
		for (final HuntingCriteriaListener listener : listeners)
			listener.onHuntsSateModified();
	}

	/**
	 * In the present implementation, the user doesn't bring his/her skills with
	 * the shareLocationAndGetUsers service.
	 *
	 * @param user
	 *            User to retrieve skills and initialize for
	 */
	private void initializeUserSkills(final User user) {
		final GetUserSkills getUserSkills = GetUserSkills.newInstance(
				TalentRadarApplication.getContext(), user.getId());

		try {
			final GetUserSkillsResponse userSkillsResponse = getUserSkills
					.execute();
			user.setSkills(userSkillsResponse.getSkills());

		} catch (final JSONException e) {
			Log.e("HuntingCriteriaEngine",
					"couldn't fetch skills from userId = " + user.getId());
			e.printStackTrace();
		} catch (final IOException e) {
			Log.e("HuntingCriteriaEngine",
					"couldn't fetch skills from userId = " + user.getId());
			e.printStackTrace();
		}
	}

	public void addListener(final HuntingCriteriaListener listener) {
		listeners.add(listener);
	}
}
