package com.menatwork;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.menatwork.hunts.DefaultHunt;
import com.menatwork.miniprofile.PingTask;
import com.menatwork.model.JobPosition;
import com.menatwork.model.User;
import com.menatwork.skills.SkillButtonFactory;
import com.menatwork.view.LoadProfilePictureTask;

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
public class ProfileActivity extends GuiTalentRadarActivity {

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
	private ProgressBar loadingProfilePic;
	private ImageView profilePic;

	private ViewGroup jobsLayout;

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		super.postCreate(savedInstanceState);

		this.makeTitleLabelsTransparent();
		this.initializeUser();
	}

	private void loadProfilePicture() {
		final String profilePicUrl = this.user.getProfilePictureUrl();
		new LoadProfilePictureTask(this, profilePic, loadingProfilePic,
				profilePicUrl).execute();
	}

	private void loadUserData() {
		if (user.equals(getLocalUser()))
			fullname.setText(user.forceGetRealName());
		else
			fullname.setText(user.getDisplayableLongName());
		this.headline.setText(getHeadlineText(user.getHeadline()));
		this.loadSkills();
		this.loadJobs();
	}

	private void loadJobs() {
		if (user.equals(getLocalUser())
				|| user.getPrivacySettings().isJobPositionsPublic()) {
			final List<JobPosition> jobPositions = user.getJobPositions();
			for (final JobPosition jobPosition : jobPositions) {
				final TextView newJobPositionLabel = new TextView(this);
				newJobPositionLabel.setText(jobPosition.getTitle());
				int index;
				if (jobPosition.isCurrent()) {
					index = 0;
					newJobPositionLabel.setTextAppearance(this,
							android.R.style.TextAppearance_Medium);
				} else {
					index = jobsLayout.getChildCount();
					newJobPositionLabel.setTextAppearance(this,
							android.R.style.TextAppearance_Small);
				}
				jobsLayout.addView(newJobPositionLabel, index);

			}
		} else
			addEmptyJobPosition();
	}

	private void addEmptyJobPosition() {
		final TextView newJobPositionLabel = new TextView(this);
		newJobPositionLabel.setText(getString(R.string.profile_no_jobs));
		jobsLayout.addView(newJobPositionLabel);
	}

	private void initializeUser() {
		final Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(EXTRAS_USERID))
			// user = this.getUserById(extras.getString(EXTRAS_USERID));
			new SeeProfileGetUserTask(this).execute(extras
					.getString(EXTRAS_USERID));
		else {
			user = getLocalUser();
			this.disablePingAndCaptureButtons();

			this.loadUserData();
			loadProfilePicture();
		}
	}

	private void disablePingAndCaptureButtons() {
		pingButton.setVisibility(View.INVISIBLE);
		captureButton.setVisibility(View.INVISIBLE);
	}

	private String getHeadlineText(final String headline) {
		return "null".equals(headline) ? getString(R.string.profile_no_headline)
				: headline;
	}

	private void makeTitleLabelsTransparent() {
		final AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F);
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends

		// And then on your layout
		findViewById(R.id.profile_label_skills).startAnimation(alpha);
		findViewById(R.id.profile_label_jobs).startAnimation(alpha);
	}

	@Override
	protected void setupButtons() {
		pingButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				final String localUserId = getTalentRadarApplication()
						.getLocalUser().getId();
				new PingTask(ProfileActivity.this).execute(localUserId,
						getUser().getId(), getUser().getNickname());
			}
		});
		captureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				DefaultHunt.getInstance().addUser(user);
			}
		});
	}

	@Override
	protected void findViewElements() {
		fullname = findTextViewById(R.id.profile_fullname);
		headline = findTextViewById(R.id.profile_headline);
		skillsLayout = findViewGroupById(R.id.profile_layout_skills);
		jobsLayout = findViewGroupById(R.id.profile_layout_jobs);
		pingButton = findImageButtonById(R.id.profile_button_ping);
		captureButton = findImageButtonById(R.id.profile_button_capture);
		loadingProfilePic = (ProgressBar) findViewById(R.id.profile_loading_profile_pic);
		profilePic = findImageViewById(R.id.profile_profile_pic);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.profile;
	}

	private void loadSkills() {
		final List<String> userSkills = user.getSkills();
		final SkillButtonFactory skillButtonFactory = getTalentRadarApplication()
				.getSkillButtonFactory();
		if (userSkills.isEmpty())
			skillsLayout.addView(skillButtonFactory.getEmptySkillsButton(this));
		else
			for (final String skill : userSkills) {
				final Button skillButton = skillButtonFactory.getSkillButton(
						this, skill);

				skillsLayout.addView(skillButton);
			}
	}

	public User getUser() {
		return user;
	}

	// ************************************************ //
	// ====== SeeProfileGetUserTask ======
	// ************************************************ //

	private class SeeProfileGetUserTask extends GetUserTask {

		public SeeProfileGetUserTask(final TalentRadarActivity activity) {
			super(activity);
		}

		@Override
		protected void onPostExecute(final User result) {
			super.onPostExecute(result);
			user = result;
			loadUserData();
			loadProfilePicture();
		}

	}
}
