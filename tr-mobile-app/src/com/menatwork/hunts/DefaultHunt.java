package com.menatwork.hunts;

import java.util.LinkedList;
import java.util.List;

import com.menatwork.model.User;

public class DefaultHunt extends BaseHunt {

	private static final DefaultHunt DEFAULT_HUNT_SINGLETON;

	private final List<HuntingCriteriaListener> listeners;

	static {
		DEFAULT_HUNT_SINGLETON = new DefaultHunt();
	}

	public static DefaultHunt getInstance() {
		return DEFAULT_HUNT_SINGLETON;
	}

	private DefaultHunt() {
		this.listeners = new LinkedList<HuntingCriteriaListener>();
	}

	@Override
	public String getId() {
		return "default-hunt";
	}

	@Override
	public String getTitle() {
		return "Búsqueda por defecto";
	}

	@Override
	public void setTitle(final String title) {
		// won't change title
	}

	@Override
	public String getDescription() {
		return "Aquí encontrarás los usuarios que guardes a mano";
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
			notifyHuntStateModified();

		return userAdded;
	}

	private void notifyHuntStateModified() {
		for (final HuntingCriteriaListener listener : listeners)
			listener.onHuntsSateModified();
	}

	// ************************************************ //
	// ====== Not required for the default hunt ======
	// ************************************************ //

	@Override
	public List<String> getRequiredSkills() {
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException("Hunt.getRequiredSkills");
	}

	@Override
	public List<String> getPreferredSkills() {
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException("Hunt.getPreferredSkills");
	}

	@Override
	public void setRequiredSkills(final List<String> requiredSkills) {
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException("Hunt.setRequiredSkills");
	}

	@Override
	public void setPreferredSkills(final List<String> preferredSkills) {
		// TODO Auto-generated constructor stub
		throw new UnsupportedOperationException("Hunt.setPreferredSkills");
	}

	public void addListener(final HuntingCriteriaListener listener) {
		listeners.add(listener);
	}

}
