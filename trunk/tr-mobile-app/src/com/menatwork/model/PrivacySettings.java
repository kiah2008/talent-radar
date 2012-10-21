package com.menatwork.model;

import java.util.Map;

public interface PrivacySettings {

	public boolean isNamePublic();

	public boolean isHeadlinePublic();

	public boolean isSkillsPublic();

	boolean isStealthy();

	String getNickname();

	Map<String, Object> asMap();

}
