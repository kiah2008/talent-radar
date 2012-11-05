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

	private boolean isUserAlreadyInHunt(final User user) {
		return users.contains(user) || hasUserWithSameId(user);
	}

	private boolean hasUserWithSameId(final User newUser) {
		for (final User user : users)
			if (user.getId().equals(newUser.getId()))
				return true;

		return false;
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
