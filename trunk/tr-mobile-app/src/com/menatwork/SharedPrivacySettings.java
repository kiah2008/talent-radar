package com.menatwork;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

import com.menatwork.model.PrivacySettings;
import com.menatwork.utils.ReflectionUtils;

class SharedPrivacySettings //
		extends TransactionalSharedPreferencesEditor //
		implements PrivacySettings {

	private final Context context;

	public SharedPrivacySettings(final Context context,
			final SharedPreferences sharedPreferences) {
		super(sharedPreferences);
		this.context = context;
	}

	@Override
	public boolean isNamePublic() {
		return this
				.getBoolean(
						getString(R.string.privacy_name_public_key),
						Boolean.parseBoolean(getString(R.string.privacy_name_public_default)));
	}

	public void setNamePublic(final boolean namePublic) {
		this.putBoolean(getString(R.string.privacy_name_public_key), namePublic);
	}

	@Override
	public boolean isHeadlinePublic() {
		return this
				.getBoolean(
						getString(R.string.privacy_headline_public_key),
						Boolean.parseBoolean(getString(R.string.privacy_headline_public_default)));
	}

	public void setHeadlinePublic(final boolean headlinePublic) {
		this.putBoolean(getString(R.string.privacy_headline_public_key),
				headlinePublic);
	}

	@Override
	public boolean isSkillsPublic() {
		return this
				.getBoolean(
						getString(R.string.privacy_skills_public_key),
						Boolean.parseBoolean(getString(R.string.privacy_skills_public_default)));
	}

	public void setSkillsPublic(final boolean skillsPublic) {
		this.putBoolean(getString(R.string.privacy_skills_public_key),
				skillsPublic);
	}

	@Override
	public boolean isStealthy() {
		return this
				.getBoolean(
						getString(R.string.privacy_stealthy_key),
						Boolean.parseBoolean(getString(R.string.privacy_stealthy_default)));
	}

	public void setStealthy(final boolean stealthy) {
		this.putBoolean(getString(R.string.privacy_stealthy_key), stealthy);
	}

	@Override
	public String getNickname() {
		return this.getString(getString(R.string.privacy_nickname_key),
				getString(R.string.privacy_stealthy_default));
	}

	public void setNickname(final String nickname) {
		this.putString(getString(R.string.privacy_nickname_key), nickname);
	}

	private String getString(final int resId) {
		return context.getString(resId);
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

	@Override
	protected void notifyChanges(final String... keys) {
	}

	void loadFrom(final PrivacySettings newSettings) {
		this.beginNewEdition();
		final Method[] declaredMethods = newSettings.getClass()
				.getDeclaredMethods();

		try {
			for (final Method method : declaredMethods)
				if (ReflectionUtils.isGetter(method)) {
					final Method setter = ReflectionUtils.setterForGetter(
							method, this.getClass());
					setter.invoke(this,
							method.invoke(newSettings, new Object[] {}));
				}
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading privacy settings");
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading privacy settings");
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading privacy settings");
		} finally {
			this.commitChanges();
		}
	}

	@Override
	public String toString() {
		return "SharedPrivacySettings [isNamePublic()=" + isNamePublic()
				+ ", isHeadlinePublic()=" + isHeadlinePublic()
				+ ", isSkillsPublic()=" + isSkillsPublic() + ", isStealthy()="
				+ isStealthy() + ", getNickname()=" + getNickname() + "]";
	}
	
	

}