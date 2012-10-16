package com.menatwork;

import java.util.Map;

public interface PrivacySettings {

	public boolean isNamePublic();

	public boolean isHeadlinePublic();

	public boolean isSkillsPublic();

	boolean isStealthy();

	public Map<String, Object> asMap();

}
