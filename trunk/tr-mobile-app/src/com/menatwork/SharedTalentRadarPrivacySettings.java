package com.menatwork;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

class SharedTalentRadarPrivacySettings //
		implements PrivacySettings {

	private final Context context;
	private final SharedPreferences sharedPreferences;

	public SharedTalentRadarPrivacySettings(final Context context,
			final SharedPreferences sharedPreferences) {
		this.context = context;
		this.sharedPreferences = sharedPreferences;
	}

	@Override
	public boolean isNamePublic() {
		return sharedPreferences.getBoolean(context
				.getString(R.string.privacy_name_public_key), Boolean
				.parseBoolean(context
						.getString(R.string.privacy_name_public_default)));
	}

	@Override
	public boolean isHeadlinePublic() {
		return sharedPreferences.getBoolean(context
				.getString(R.string.privacy_headline_public_key), Boolean
				.parseBoolean(context
						.getString(R.string.privacy_headline_public_default)));
	}

	@Override
	public boolean isSkillsPublic() {
		return sharedPreferences.getBoolean(context
				.getString(R.string.privacy_skills_public_key), Boolean
				.parseBoolean(context
						.getString(R.string.privacy_skills_public_default)));
	}

	@Override
	public boolean isStealthy() {
		return sharedPreferences.getBoolean(context
				.getString(R.string.privacy_stealthy_key), Boolean
				.parseBoolean(context
						.getString(R.string.privacy_stealthy_default)));
	}

	@Override
	public String getNickname() {
		return sharedPreferences.getString(
				context.getString(R.string.privacy_nickname_key),
				context.getString(R.string.privacy_stealthy_default));
	}

	@Override
	public Map<String, Object> asMap() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put(context.getString(R.string.privacy_name_public_key),
				this.isNamePublic());
		map.put(context.getString(R.string.privacy_headline_public_key),
				this.isHeadlinePublic());
		map.put(context.getString(R.string.privacy_skills_public_key),
				this.isSkillsPublic());
		map.put(context.getString(R.string.privacy_stealthy_key),
				this.isStealthy());
		map.put(context.getString(R.string.privacy_nickname_key),
				this.getNickname());
		return Collections.unmodifiableMap(map);
	}

}