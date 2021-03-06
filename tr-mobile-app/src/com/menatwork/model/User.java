package com.menatwork.model;

import java.util.List;

public interface User {

	String EMPTY_USER_ID = "this-is-a-non-existing-user-id";

	String getId();

	String getDisplayableLongName();

	String getDisplayableShortName();

	String getHeadline();

	List<String> getSkills();

	void setSkills(List<String> skills);

	String getEmail();

	String getName();

	String getSurname();

	String getNickname();

	String getProfilePictureUrl();

	PrivacySettings getPrivacySettings();

	boolean hasSkill(String requiredSkill);

	List<JobPosition> getJobPositions();

}
