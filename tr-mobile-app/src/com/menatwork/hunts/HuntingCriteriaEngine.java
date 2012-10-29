package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import com.menatwork.model.User;
import com.menatwork.radar.RadarListener;

public class HuntingCriteriaEngine implements RadarListener {

	private final Collection<Hunt> hunts;

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public static HuntingCriteriaEngine newInstance() {
		return new HuntingCriteriaEngine();
	}

	protected HuntingCriteriaEngine() {
		hunts = new ArrayList<Hunt>();
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
		for (final User user : surroundingUsers)
			for (final Hunt hunt : hunts)
				hunt.addUserIfCriteriaMatched(user);
	}
}
