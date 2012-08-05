package com.menatwork;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.menatwork.model.User;
import com.menatwork.skills.SkillButtonFactory;

public class ProfileActivity extends TalentRadarActivity {

	private ViewGroup skillsLayout;
	private TextView fullname;
	private TextView headline;
	private ImageButton pingButton;
	private ImageButton captureButton;

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		super.postCreate(savedInstanceState);

		fullname.setText(getLocalUser().getFullName());
		String headline = getLocalUser().getHeadline();
		this.headline
				.setText("null".equals(headline) ? getString(R.string.profile_no_headline)
						: headline);

		final AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends

		// And then on your layout
		findViewById(R.id.profile_label_skills).startAnimation(alpha);
		findViewById(R.id.profile_label_email).startAnimation(alpha);

		this.loadSkills();
	}

	@Override
	protected void setupButtons() {
		// TODO Enable-disable-setup the ping and capture contact buttons

		pingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(ProfileActivity.this,
						"Pinging user... (NA MENTIRA!)", Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	@Override
	protected void findViewElements() {
		fullname = findTextViewById(R.id.profile_fullname);
		headline = findTextViewById(R.id.profile_headline);
		skillsLayout = findViewGroupById(R.id.profile_layout_skills);
		pingButton = findImageButtonById(R.id.profile_button_ping);
		captureButton = findImageButtonById(R.id.profile_button_capture);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.profile;
	}

	private User getLocalUser() {
		return ((TalentRadarApplication) getApplication()).getLocalUser();
	}

	private void loadSkills() {
		List<String> userSkills = getLocalUser().getSkills();
		SkillButtonFactory skillButtonFactory = getTalentRadarApplication()
				.getSkillButtonFactory();
		if (userSkills.isEmpty()) {
			skillsLayout.addView(skillButtonFactory.getEmptySkillsButton(this));
		} else
			for (final String skill : userSkills) {
				final Button skillButton = skillButtonFactory.getSkillButton(
						this, skill);

				skillsLayout.addView(skillButton);
			}
	}

}
