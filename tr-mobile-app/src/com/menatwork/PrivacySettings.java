package com.menatwork;

import android.content.Context;
import android.content.SharedPreferences;

public interface PrivacySettings {

}

class SharedTalentRadarPrivacySettings //
		extends TransactionalSharedPreferencesEditor //
		implements PrivacySettings {

	private final Context context;

	public SharedTalentRadarPrivacySettings(Context context,
			SharedPreferences sharedPreferences) {
		super(sharedPreferences);
		this.context = context;
	}

	@Override
	protected void notifyChanges(String... keys) {
		// TODO Auto-generated method stub
	}

	public boolean isNamePublic() {
		return getBoolean(context.getString(R.string.privacy_name_public_key),
				Boolean.parseBoolean(context
						.getString(R.string.privacy_name_public_default)));
	}

	public void setNamePublic(boolean pnblic) {
		this.putBoolean(context.getString(R.string.privacy_name_public_key),
				pnblic);
	}

	public boolean isHeadlinePublic() {
		return getBoolean(
				context.getString(R.string.privacy_headline_public_key),
				Boolean.parseBoolean(context
						.getString(R.string.privacy_headline_public_default)));
	}

	public void setHeadlinePublic(boolean pnblic) {
		this.putBoolean(
				context.getString(R.string.privacy_headline_public_key), pnblic);
	}

	public boolean isSkillsPublic() {
		return getBoolean(
				context.getString(R.string.privacy_skills_public_key),
				Boolean.parseBoolean(context
						.getString(R.string.privacy_skills_public_default)));
	}

	public void setSkillsPublic(boolean pnblic) {
		this.putBoolean(context.getString(R.string.privacy_skills_public_key),
				pnblic);
	}

}
