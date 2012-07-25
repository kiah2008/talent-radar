package com.menatwork;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.menatwork.model.User;

public class ProfileActivity extends TalentRadarActivity {

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		super.postCreate(savedInstanceState);

		final AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends

		// And then on your layout
		findViewById(R.id.profile_label_skills).startAnimation(alpha);
		findViewById(R.id.profile_label_email).startAnimation(alpha);

		final User localUser = getLocalUser();
		findTextViewById(R.id.profile_fullname)
				.setText(localUser.getFullName());
		findTextViewById(R.id.profile_headline)
				.setText(localUser.getHeadline());

		loadSkills();
	}

	@Override
	protected void setupButtons() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void findViewElements() {
		// TODO Auto-generated method stub
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.profile;
	}

	private User getLocalUser() {
		return ((TalentRadarApplication) getApplication()).getLocalUser();
	}

	private void loadSkills() {
		for (final String skill : getLocalUser().getSkills()) {
			final Button skillButton = getTalentRadarApplication()
					.getSkillButtonFactory().getSkillButton(skill);

			findViewGroupById(R.id.profile_layout_skills).addView(skillButton);
		}
	}

}
