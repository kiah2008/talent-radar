package com.menatwork;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.menatwork.skills.SkillButtonFactory;

public class NewHuntActivity extends GuiTalentRadarActivity {

	private ViewGroup necessarySkillsContainer;
	private ViewGroup optionalSkillsContainer;
	private ImageButton addNecessarySkillButton;
	private ImageButton addOptionalSkillButton;
	private TextView necessarySkillTextView;
	private TextView optionalSkillTextView;
	private TextView nameTextView;

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		this.initializeContainers();
	}

	private void initializeContainers() {
		necessarySkillsContainer.removeAllViews();
		optionalSkillsContainer.removeAllViews();
		necessarySkillsContainer.addView(getTalentRadarApplication()
				.getSkillButtonFactory().getEmptySkillsButton(this));
		optionalSkillsContainer.addView(getTalentRadarApplication()
				.getSkillButtonFactory().getEmptySkillsButton(this));
	}

	@Override
	protected void setupButtons() {
		addNecessarySkillButton.setOnClickListener(new AddSkillOnClickListener(
				necessarySkillsContainer, necessarySkillTextView));
		addOptionalSkillButton.setOnClickListener(new AddSkillOnClickListener(
				optionalSkillsContainer, optionalSkillTextView));
	}

	@Override
	protected void findViewElements() {
		necessarySkillsContainer = findViewGroupById(R.id.new_hunt_layout_necessary_skills);
		optionalSkillsContainer = findViewGroupById(R.id.new_hunt_layout_optional_skills);
		addNecessarySkillButton = findImageButtonById(R.id.new_hunt_button_add_necessary_skill);
		addOptionalSkillButton = findImageButtonById(R.id.new_hunt_button_add_optional_skill);
		necessarySkillTextView = findTextViewById(R.id.new_hunt_necessary_skill_input);
		optionalSkillTextView = findTextViewById(R.id.new_hunt_optional_skill_input);
		nameTextView = findTextViewById(R.id.new_hunt_name);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.new_hunt;
	}

	private class AddSkillOnClickListener implements OnClickListener {
		private final ViewGroup destinationContainer;
		private final TextView input;

		private AddSkillOnClickListener(final ViewGroup destinationContainer,
				final TextView input) {
			super();
			this.destinationContainer = destinationContainer;
			this.input = input;
		}

		@Override
		public void onClick(final View arg0) {
			final SkillButtonFactory skillButtonFactory = getTalentRadarApplication()
					.getSkillButtonFactory();
			final String newSkillText = input.getText().toString();
			final Button newSkillButton = skillButtonFactory.getSkillButton(
					NewHuntActivity.this, newSkillText);

			// remove empty skill button if present
			if (destinationContainer.getChildCount() == 1)
				if (destinationContainer.getChildAt(0).equals(
						skillButtonFactory
								.getEmptySkillsButton(NewHuntActivity.this)))
					destinationContainer.removeAllViews();

			destinationContainer.addView(newSkillButton);
			input.setText("");
		}

	}

}
