package com.menatwork.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.TalentRadarApplication;

public class DataObjectPrivacySettings implements PrivacySettings {
	private boolean namePublic, headlinePublic, skillsPublic, stealthy;
	private String nickname;
	private boolean picturePublic;
	private boolean jobPositionsPublic;

	@Override
	public boolean isNamePublic() {
		return namePublic;
	}

	@Override
	public boolean isHeadlinePublic() {
		return headlinePublic;
	}

	@Override
	public boolean isSkillsPublic() {
		return skillsPublic;
	}

	@Override
	public boolean isStealthy() {
		return stealthy;
	}

	@Override
	public boolean isJobPositionsPublic() {
		return jobPositionsPublic;
	}

	@Override
	public String getNickname() {
		return nickname;
	}

	public void setNamePublic(final boolean namePublic) {
		this.namePublic = namePublic;
	}

	public void setHeadlinePublic(final boolean headlinePublic) {
		this.headlinePublic = headlinePublic;
	}

	public void setSkillsPublic(final boolean skillsPublic) {
		this.skillsPublic = skillsPublic;
	}

	public void setStealthy(final boolean stealthy) {
		this.stealthy = stealthy;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean isPicturePublic() {
		return picturePublic;
	}

	public void setPicturePublic(final boolean picturePublic) {
		this.picturePublic = picturePublic;
	}

	public void setJobPositionsPublic(final boolean jobPositionsPublic) {
		this.jobPositionsPublic = jobPositionsPublic;
	}

	@Override
	public Map<String, Object> asMap() {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		final Context context = TalentRadarApplication.getContext();
		map.put(context.getString(R.string.privacy_name_public_key),
				this.isNamePublic());
		map.put(context.getString(R.string.privacy_headline_public_key),
				this.isHeadlinePublic());
		map.put(context.getString(R.string.privacy_skills_public_key),
				this.isSkillsPublic());
		map.put(context.getString(R.string.privacy_stealthy_key),
				this.isStealthy());
		map.put(context.getString(R.string.privacy_picture_public_key),
				this.isPicturePublic());
		map.put(context.getString(R.string.privacy_job_positions_public_key),
				this.isJobPositionsPublic());
		map.put(context.getString(R.string.privacy_nickname_key),
				this.getNickname());
		return Collections.unmodifiableMap(map);
	}

	@Override
	public String toString() {
		return "DataObjectPrivacySettings [namePublic=" + namePublic
				+ ", headlinePublic=" + headlinePublic + ", skillsPublic="
				+ skillsPublic + ", stealthy=" + stealthy + ", nickname="
				+ nickname + ", picturePublic=" + picturePublic
				+ "jobPositionsPublic=" + jobPositionsPublic + "]";
	}

}
