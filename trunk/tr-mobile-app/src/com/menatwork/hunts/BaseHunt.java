package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.List;

import com.menatwork.model.User;

/**
 * Base class for hunts, defining common behaviour (users manipulation).
 *
 * @author miguel
 *
 */
public abstract class BaseHunt implements Hunt {

	protected final List<User> users;

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public BaseHunt() {
		this(new ArrayList<User>());
	}

	public BaseHunt(final List<User> users) {
		this.users = users;
	}

	// ************************************************ //
	// ====== Instance methods ======
	// ************************************************ //

	protected boolean addUser(final User user) {
		if (!isUserAlreadyInHunt(user))
			return users.add(user);

		return false;
	}

	@Override
	public boolean removeUserWithId(final String userId) {
		return users.remove(findUserById(userId));
	}

	/**
	 * Searchs a user by its ID in the current hunt and returns the user object
	 * according to it.
	 *
	 * @param userId
	 *            ID of the user to be find
	 * @return A {@link User} or null, if no user with userId is found
	 */
	protected User findUserById(final String userId) {
		for (final User user : users)
			if (user.getId().equals(userId))
				return user;

		return null;
	}

	protected boolean isUserAlreadyInHunt(final User user) {
		return users.contains(user) || hasUserWithSameId(user);
	}

	protected boolean hasUserWithSameId(final User newUser) {
		final String userId = newUser.getId();
		return findUserById(userId) != null;
	}

	@Override
	public int getUsersQuantity() {
		return users.size();
	}

	@Override
	public List<User> getUsers() {
		return users;
	}

}
