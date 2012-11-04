package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.List;

import com.menatwork.model.User;

public abstract class BaseHunt implements Hunt {

	protected final List<User> users;

	public BaseHunt() {
		this.users = new ArrayList<User>();
	}

	@Override
	public int getUsersQuantity() {
		return users.size();
	}

	@Override
	public List<User> getUsers() {
		return users;
	}

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

}
