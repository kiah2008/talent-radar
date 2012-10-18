package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.Collection;

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

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public static HuntingCriteriaEngine newInstance() {
		return new HuntingCriteriaEngine();
	}

}
