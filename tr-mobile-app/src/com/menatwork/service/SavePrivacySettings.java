package com.menatwork.service;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.BaseResponse;

public class SavePrivacySettings extends StandardServiceCall<BaseResponse> {

	// post_uri_save_privacy http://www.talent-radar.com/app/users/setPrivacy
	// data[User][id]
	// data[User][show_name]
	// data[User][show_headline]
	// data[User][show_skills]
	// data[User][show_in_searches]

	public static SavePrivacySettings newInstance(final Context context,
			final String userId, final Boolean isNamePublic,
			final Boolean isHeadlinePublic, final Boolean isSkillsPublic,
			final Boolean isStealthy) {
		return new SavePrivacySettings(context, userId, isNamePublic,
				isHeadlinePublic, isSkillsPublic, isStealthy);
	}

	private SavePrivacySettings(final Context context, final String userId,
			final Boolean isNamePublic, final Boolean isHeadlinePublic,
			final Boolean isSkillsPublic, final Boolean isStealthy) {
		super(context, BaseResponse.class);
		setParameter(R.string.post_key_save_privacy_user_id, userId);
		setParameter(R.string.post_key_save_privacy_name_public, isNamePublic);
		setParameter(R.string.post_key_save_privacy_headline_public,
				isHeadlinePublic);
		setParameter(R.string.post_key_save_privacy_skills_public,
				isSkillsPublic);
		setParameter(R.string.post_key_save_privacy_stealthy, isStealthy);
	}

	@Override
	protected String getMethodUri() {
		return this.getString(R.string.post_uri_save_privacy);
	}

}
