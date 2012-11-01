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
	int getQuantity();

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

	// ************************************************ //
	// ====== Accessing the criteria in the hunt ======
	// ************************************************ //
	// FIXME - these methods shouldn't be here, may have to implement a visitor
	// - miguel - 01/11/2012

	List<String> getRequiredSkills();

	List<String> getPreferredSkills();

	void setTitle(String title);

	void setRequiredSkills(List<String> requiredSkills);

	void setPreferredSkills(List<String> preferredSkills);


}