package com.menatwork.service;



import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.SkillsResponse;

public class GetSkills extends StandardServiceCall<SkillsResponse> {

	public static GetSkills newInstance(final Context context) {
		return new GetSkills(context, SkillsResponse.class);
	}

	private GetSkills(final Context context,
			final Class<SkillsResponse> responseClass) {
		super(context, responseClass);
		this.setParameter(R.string.post_key_get_skills_skills, "tinpanvalley");
	}

	@Override
	protected String getMethodUri() {
		return getString(R.string.post_uri_get_skills);
	}

}