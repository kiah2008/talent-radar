package com.menatwork.service;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.BaseResponse;

public class SavePrivacySettings extends StandardServiceCall<BaseResponse> {

	// post_uri_save_privacy http://www.talent-radar.com/app/users/setPrivacy
	// data[User][id]
	// data[User][username]
	// data[User][show_name]
	// data[User][show_headline]
	// data[User][show_skills]
	// data[User][show_picture]
	// data[User][show_in_searches]

	public static SavePrivacySettings newInstance(final Context context,
			final String userId, final Boolean isNamePublic,
			final String nickname, final Boolean isHeadlinePublic,
			final Boolean isSkillsPublic, final Boolean isPicturePublic,
			final Boolean isStealthy) {
		return new SavePrivacySettings(context, userId, isNamePublic, nickname,
				isHeadlinePublic, isSkillsPublic, isPicturePublic, isStealthy);
	}

	private SavePrivacySettings(final Context context, final String userId,
			final Boolean isNamePublic, final String nickname,
			final Boolean isHeadlinePublic, final Boolean isSkillsPublic,
			final Boolean isPicturePublic, final Boolean isStealthy) {
		super(context, BaseResponse.class);
		setParameter(R.string.post_key_save_privacy_user_id, userId);
		setParameter(R.string.post_key_save_privacy_nickname, nickname);
		setParameter(R.string.post_key_save_privacy_name_public,
				replaceFor(isNamePublic));
		setParameter(R.string.post_key_save_privacy_headline_public,
				replaceFor(isHeadlinePublic));
		setParameter(R.string.post_key_save_privacy_skills_public,
				replaceFor(isSkillsPublic));
		setParameter(R.string.post_key_save_privacy_picture_public,
				replaceFor(isPicturePublic));
		setParameter(R.string.post_key_save_privacy_show_in_searches,
				replaceFor(!isStealthy));
	}

	private String replaceFor(final boolean value) {
		return value ? "1" : "0";
	}

	@Override
	protected String getMethodUri() {
		return this.getString(R.string.post_uri_save_privacy);
	}

}
