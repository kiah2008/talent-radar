package com.menatwork.hunts;

import java.util.List;

import com.menatwork.model.User;

public interface Hunt {

	String getTitle();

	int getQuantity();

	String getDescription();

	List<User> getUsers();

	String getId();

	/**
	 * Adds the user to the current hunt if the criteria defined by the hunt is
	 * matched.
	 *
	 * @return <code>true</code> - if user is added to hunt<br />
	 *         <code>false</code> - otherwise
	 */
	boolean addUserIfCriteriaMatched(User user);

}