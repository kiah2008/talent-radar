package com.menatwork.hunts;

import java.util.List;

import com.menatwork.model.User;

/**
 * Common interface for the hunts in the system (no matter the kind of criteria
 * defined in each).
 * 
 * @author miguel
 * 
 */
public interface Hunt {

	String getId();

	String getTitle();

	/**
	 * Tells how many users have been added to this hunt.
	 * 
	 * @return Integer representing the number of users in the hunt
	 */
	int getUsersQuantity();

	/**
	 * A textual representation of the hunt to be shown to the final user. May
	 * vary according to the kind of requirements/criteria defined by the hunt.
	 * 
	 * @return String with representation
	 */
	String getDescription();

	List<User> getUsers();

	/**
	 * Adds the user to the current hunt if the criteria defined by the hunt is
	 * matched.
	 * 
	 * @return <code>true</code> - if user is added<br />
	 *         <code>false</code> - otherwise
	 * 
	 */
	boolean addUserIfCriteriaMatched(User user);

	/**
	 * Removes the user with the ID specified by parameter from the current
	 * hunt.
	 * 
	 * @param userId
	 * @return <code>true</code> - if the user has successfully been removed<br />
	 *         <code>false</code> - otherwise
	 */
	boolean removeUserWithId(String userId);

	void emptyUsers();

	int getIcon();

	boolean isSaveableToPortfolio();

}