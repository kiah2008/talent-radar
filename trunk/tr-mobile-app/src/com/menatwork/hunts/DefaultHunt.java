package com.menatwork.hunts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.menatwork.R;
import com.menatwork.TalentRadarApplication;
import com.menatwork.model.User;

/**
 * DefaultHunt represents the hunt availabe for everyone where each user is
 * saved by demand and not a certain criteria.
 * 
 * @author miguel
 * 
 */
public class DefaultHunt extends BaseHunt {

	private static final DefaultHunt DEFAULT_HUNT_SINGLETON;

	private final List<HuntingCriteriaListener> listeners;

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	static {
		// it's created when the class is started
		DEFAULT_HUNT_SINGLETON = new DefaultHunt();
	}

	public static DefaultHunt getInstance() {
		return DEFAULT_HUNT_SINGLETON;
	}

	private DefaultHunt() {
		this.listeners = new LinkedList<HuntingCriteriaListener>();
	}

	// ************************************************ //
	// ====== Instance members ======
	// ************************************************ //

	@Override
	public String getId() {
		return "default-hunt";
	}

	@Override
	public String getTitle() {
		return getString(R.string.default_hunt_title);
	}

	@Override
	public String getDescription() {
		return getString(R.string.default_hunt_description);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [users=" + users + "]";
	}

	/**
	 * As the default hunt is not used as any other hunt, it doesn't have a
	 * certain criteria and for the sake of avoiding problems trying to use it
	 * as some other hunt, it won't ever accept a user (by some criteria). Use
	 * {@link #addUser(User)} instead to add a contact picked by the user.
	 * 
	 * <b>WONT ADD THE USER!</b>
	 */
	@Override
	public boolean addUserIfCriteriaMatched(final User user) {
		return false;
	}

	/**
	 * For the default hunt, use this method instead of
	 * {@link #addUserIfCriteriaMatched(User)}
	 * 
	 * @param user
	 */
	@Override
	public boolean addUser(final User user) {
		final boolean userAdded = super.addUser(user);

		if (userAdded)
			notifyHuntStateModified(user);

		return userAdded;
	}

	public void addUsers(final List<User> usersFromUserIds) {
		for (final User user : usersFromUserIds)
			addUser(user);
	}

	// ************************************************ //
	// ====== Listener manipulation ======
	// ************************************************ //

	public void addListener(final HuntingCriteriaListener listener) {
		listeners.add(listener);
	}

	private void notifyHuntStateModified(final User user) {
		for (final HuntingCriteriaListener listener : listeners)
			listener.onUsersAddedToHunt(this, Arrays.asList(user));
	}

	// ************************************************ //
	// ====== Talent Radar commons ======
	// ************************************************ //

	private String getString(final int stringId) {
		return TalentRadarApplication.getStringById(stringId);
	}

}
