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

/**
 * In order to make this view reusable for viewing any user profile, you need to
 * pass along in a bundle the user id that you are trying to visualize (or
 * nothing for the local user, that means, don't put anything in the extras!)
 * 
 * key: userid / value: a string with the user id (eg. "25")
 * 
 * @see {@link ProfileActivity#EXTRAS_USERID}
 * 
 * @author aabdala
 * 
 */
public class ProfileActivity extends TalentRadarActivity {

	/**
	 * Param for extras bundle to pass along what user to show in this view
	 */
	public static final String EXTRAS_USERID = "userid";

	private ViewGroup skillsLayout;
	private TextView fullname;
	private TextView headline;
	private ImageButton pingButton;
	private ImageButton captureButton;
	private User user;

	private TextView email;

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		super.postCreate(savedInstanceState);

		this.makeTitleLabelsTransparent();
		this.initializeUser();
		this.loadUserData();
	}

	private void loadUserData() {
		fullname.setText(user.getFullName());
		this.headline.setText(getHeadlineTextFromUser(user));
		this.loadSkills();
		this.loadEmail();
	}

	private void loadEmail() {
		String email = this.user.getEmail();
		this.email.setText(email.equals("null") ? user.getName().toLowerCase()
				+ "." + user.getSurname().toLowerCase() + "@gmail.com" : email);
	}

	private void initializeUser() {
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(EXTRAS_USERID))
			user = this.getUserById(extras.getString(EXTRAS_USERID));
		else {
			user = getLocalUser();
			this.disablePingAndCaptureButtons();
		}
	}

	private void disablePingAndCaptureButtons() {
		pingButton.setVisibility(View.INVISIBLE);
		captureButton.setVisibility(View.INVISIBLE);
	}

	private String getHeadlineTextFromUser(User user) {
		return "null".equals(user.getHeadline()) ? getString(R.string.profile_no_headline)
				: user.getHeadline();
	}

	private void makeTitleLabelsTransparent() {
		final AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends

		// And then on your layout
		findViewById(R.id.profile_label_skills).startAnimation(alpha);
		findViewById(R.id.profile_label_email).startAnimation(alpha);
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
		email = findTextViewById(R.id.profile_email);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.profile;
	}

	private void loadSkills() {
		List<String> userSkills = user.getSkills();
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
