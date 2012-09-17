package com.menatwork.model;

import java.util.List;

public interface User {

	String EMPTY_USER_ID = "this-is-a-non-existing-user-id";

	String getId();

	String getFullName();

	String getHeadline();

	List<String> getSkills();

	void setSkills(List<String> skills);

	String getEmail();

	String getName();

	String getSurname();

	String getUsername();

	String getProfilePictureUrl();

}
