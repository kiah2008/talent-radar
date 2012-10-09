package com.menatwork;

import android.content.SharedPreferences;

public interface PrivacySettings {

}

class SharedTalentRadarPrivacySettings //
		extends TransactionalSharedPreferencesEditor //
		implements PrivacySettings {

	private static final String PRIVACY_SKILLS_PUBLIC = "PRIVACY_SKILLS_PUBLIC";
	private static final String PRIVACY_HEADLINE_PUBLIC = "PRIVACY_HEADLINE_PUBLIC";
	private static final String PRIVACY_NAME_PUBLIC = "PRIVACY_NAME_PUBLIC";

	public SharedTalentRadarPrivacySettings(SharedPreferences sharedPreferences) {
		super(sharedPreferences);
	}

	@Override
	protected void notifyChanges(String... keys) {
		// TODO Auto-generated method stub
	}

	public boolean isNamePublic() {
		return getBoolean(PRIVACY_NAME_PUBLIC, false);
	}

	public void setNamePublic(boolean pnblic) {
		this.putBoolean(PRIVACY_NAME_PUBLIC, pnblic);
	}

	public boolean isHeadlinePublic() {
		return getBoolean(PRIVACY_HEADLINE_PUBLIC, false);
	}

	public void setHeadlinePublic(boolean pnblic) {
		this.putBoolean(PRIVACY_HEADLINE_PUBLIC, pnblic);
	}

	public boolean isSkillsPublic() {
		return getBoolean(PRIVACY_SKILLS_PUBLIC, false);
	}

	public void setSkillsPublic(boolean pnblic) {
		this.putBoolean(PRIVACY_SKILLS_PUBLIC, pnblic);
	}

}
