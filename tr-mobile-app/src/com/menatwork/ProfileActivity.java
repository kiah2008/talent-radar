package com.menatwork;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.menatwork.model.User;

public class ProfileActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.profile);
		AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends
		// And then on your layout
		findViewById(R.id.profile_label_skills).startAnimation(alpha);
		findViewById(R.id.profile_label_email).startAnimation(alpha);

		User localUser = getLocalUser();
		((TextView) findViewById(R.id.profile_fullname)).setText(localUser
				.getFullName());
		((TextView) findViewById(R.id.profile_headline)).setText(localUser
				.getHeadline());

		loadSkills();
	}

	private User getLocalUser() {
		return ((TalentRadarApplication) getApplication()).getLocalUser();
	}

	private void loadSkills() {
		for (String skill : getLocalUser().getSkills()) {
			Button skillButton = ((TalentRadarApplication) getApplication())
					.getSkillButtonFactory().getSkillButton(skill);
			((ViewGroup) findViewById(R.id.profile_layout_skills))
					.addView(skillButton);
		}
	}
}
