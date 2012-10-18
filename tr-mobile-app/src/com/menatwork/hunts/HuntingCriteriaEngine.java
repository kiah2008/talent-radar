package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import com.menatwork.radar.RadarListener;

public class HuntingCriteriaEngine implements RadarListener {

	private final Collection<Hunt> hunts = new ArrayList<Hunt>();

	public Collection<Hunt> getHunts() {
		return hunts;
	}

	public void addHunt(final Hunt hunt) {
		hunts.add(hunt);
	}

	public void removeHunt(final Hunt hunt) {
		hunts.remove(hunt);
	}

	public Hunt getHuntById(final String id) {
		for (final Hunt hunt : hunts)
			if (hunt.getId().equals(id))
				return hunt;

		throw new NoSuchElementException("there's no hunt with id = " + id);
	}

	public void addHunts(final Hunt... hunts) {
		for (final Hunt hunt : hunts)
			addHunt(hunt);
	}

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public static HuntingCriteriaEngine newInstance() {
		return new HuntingCriteriaEngine();
	}

}
