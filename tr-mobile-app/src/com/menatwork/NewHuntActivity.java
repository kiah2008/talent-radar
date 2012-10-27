package com.menatwork;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.menatwork.hunts.Hunt;
import com.menatwork.hunts.HuntingCriteriaEngine;
import com.menatwork.hunts.SimpleSkillHuntBuilder;
import com.menatwork.skills.SkillButtonFactory;
import com.menatwork.skills.SkillSuggestionBox;
import com.menatwork.utils.CloseActivityClickListener;

public class NewHuntActivity extends GuiTalentRadarActivity {

	private ViewGroup necessarySkillsContainer;
	private ViewGroup optionalSkillsContainer;
	private ImageButton addNecessarySkillButton;
	private ImageButton addOptionalSkillButton;
	private TextView necessarySkillTextView;
	private TextView optionalSkillTextView;
	private TextView nameTextView;
	private ViewGroup optionalSkillsSuggestionsContainer;
	private ViewGroup necessarySkillsSuggestionsContainer;
	private Button saveButton;
	private Button cancelButton;

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		this.initializeContainers();
		this.initializeTextWatchers();
	}

	private void initializeTextWatchers() {
		this.necessarySkillTextView
				.addTextChangedListener(new SkillsTextWatcher(
						addNecessarySkillButton, necessarySkillsContainer,
						necessarySkillsSuggestionsContainer));
		this.optionalSkillTextView
				.addTextChangedListener(new SkillsTextWatcher(
						addOptionalSkillButton, optionalSkillsContainer,
						optionalSkillsSuggestionsContainer));
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
		addNecessarySkillButton.setEnabled(false);
		addOptionalSkillButton.setOnClickListener(new AddSkillOnClickListener(
				optionalSkillsContainer, optionalSkillTextView));
		addOptionalSkillButton.setEnabled(false);
		cancelButton.setOnClickListener(new CloseActivityClickListener(this));
		saveButton.setOnClickListener(new SaveHuntClickListener());
	}

	@Override
	protected void findViewElements() {
		necessarySkillsContainer = findViewGroupById(R.id.new_hunt_layout_necessary_skills);
		necessarySkillsSuggestionsContainer = findViewGroupById(R.id.new_hunt_layout_suggestions_necessary_skills);
		optionalSkillsContainer = findViewGroupById(R.id.new_hunt_layout_optional_skills);
		optionalSkillsSuggestionsContainer = findViewGroupById(R.id.new_hunt_layout_suggestions_optional_skills);
		addNecessarySkillButton = findImageButtonById(R.id.new_hunt_button_add_necessary_skill);
		addOptionalSkillButton = findImageButtonById(R.id.new_hunt_button_add_optional_skill);
		necessarySkillTextView = findTextViewById(R.id.new_hunt_necessary_skill_input);
		optionalSkillTextView = findTextViewById(R.id.new_hunt_optional_skill_input);
		saveButton = findButtonById(R.id.new_hunt_button_save);
		cancelButton = findButtonById(R.id.new_hunt_button_cancel);
		nameTextView = findTextViewById(R.id.new_hunt_name);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.new_hunt;
	}

	protected void removeSkillFromViewGroup(final View buttonToDelete,
			final ViewGroup skillsContainer) {
		skillsContainer.removeView(buttonToDelete);
		if (skillsContainer.getChildCount() == 0)
			skillsContainer.addView(getTalentRadarApplication()
					.getSkillButtonFactory().getEmptySkillsButton(this));
	}

	private Hunt buildHunt() {
		final SimpleSkillHuntBuilder builder = SimpleSkillHuntBuilder
				.newInstance();
		final String huntName = nameTextView.getText().toString();
		builder.setTitle(huntName);
		builder.setId(generateHuntId(huntName));
		builder.setRequiredSkills(getSkillsFromContainer(necessarySkillsContainer));
		builder.setPreferredSkills(getSkillsFromContainer(optionalSkillsContainer));
		return builder.build();
	}

	protected String generateHuntId(final String huntName) {
		final StringBuilder stringBuilder = new StringBuilder(getLocalUser()
				.getId());
		stringBuilder.append("_");
		stringBuilder.append(huntName.replace(' ', '_'));
		return stringBuilder.toString();
	}

	private List<String> getSkillsFromContainer(final ViewGroup skillContainer) {
		final int childCount = skillContainer.getChildCount();
		final List<String> skills = new ArrayList<String>(childCount);
		for (int i = 0; i < childCount; i++)
			skills.add(((Button) skillContainer.getChildAt(i)).getText()
					.toString());
		return skills;
	}

	void addSkill(final String newSkillText, final ViewGroup destinationContainer,
			final Editable input) {
		final SkillButtonFactory skillButtonFactory = getTalentRadarApplication()
				.getSkillButtonFactory();
		final Button newSkillButton = skillButtonFactory.getSkillButton(
				NewHuntActivity.this, newSkillText);
		newSkillButton.setOnLongClickListener(new SkillOnLongClickListener());

		// remove empty skill button if present
		if (destinationContainer.getChildCount() == 1)
			if (destinationContainer.getChildAt(0).equals(
					skillButtonFactory
							.getEmptySkillsButton(NewHuntActivity.this)))
				destinationContainer.removeAllViews();

		destinationContainer.addView(newSkillButton);
		input.clear();
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
			final String newSkillText = input.getText().toString();
			addSkill(newSkillText, destinationContainer,
					input.getEditableText());
		}

	}

	private class SkillOnLongClickListener implements OnLongClickListener {

		@Override
		public boolean onLongClick(final View v) {
			if (v != null) {
				final Builder builder = new AlertDialog.Builder(
						NewHuntActivity.this);
				builder.setItems(new CharSequence[] { "Eliminar skill" },
						new DeleteSkillListener(v));
				builder.create().show();
				return true;
			}
			return true;
		}

		private class DeleteSkillListener implements
				DialogInterface.OnClickListener {

			private final View buttonToDelete;

			public DeleteSkillListener(final View v) {
				this.buttonToDelete = v;
			}

			@Override
			public void onClick(final DialogInterface arg0, final int arg1) {
				if (findViewInViewGroup(necessarySkillsContainer,
						buttonToDelete))
					removeSkillFromViewGroup(buttonToDelete,
							necessarySkillsContainer);
				// remove skill from hunt model or whatever
				else if (findViewInViewGroup(optionalSkillsContainer,
						buttonToDelete))
					removeSkillFromViewGroup(buttonToDelete,
							optionalSkillsContainer);
				// remove skill from hunt model or whatever
			}

			private boolean findViewInViewGroup(final ViewGroup viewGroup,
					final View view) {
				boolean found = false;
				final int childCount = viewGroup.getChildCount();
				for (int i = 0; i < childCount; i++)
					if (view.equals(viewGroup.getChildAt(i)))
						found = true;
				return found;
			}
		}
	}

	private class SkillsTextWatcher implements TextWatcher {

		private final ImageButton button;
		private final ViewGroup suggestionContainer;
		private final ViewGroup skillContainer;

		public SkillsTextWatcher(final ImageButton button,
				final ViewGroup skillContainer,
				final ViewGroup suggestionContainer) {
			this.button = button;
			this.skillContainer = skillContainer;
			this.suggestionContainer = suggestionContainer;
		}

		@Override
		public void afterTextChanged(final Editable editable) {
			if (editable == null)
				return;
			final String inputString = editable.toString();
			final boolean inputNotEmpty = inputString.length() > 0;
			button.setEnabled(inputNotEmpty);
			if (!inputNotEmpty) {
				// if input is empty... return (I know)
				suggestionContainer.removeAllViews();
				return;
			}

			// add suggestions to suggestions-box
			final SkillSuggestionBox skillSuggestionBox = getTalentRadarApplication()
					.getSkillSuggestionBox();
			final List<String> suggestions = skillSuggestionBox
					.getSuggestionsFor(inputString);

			suggestionContainer.removeAllViews();
			for (final String suggestion : suggestions) {
				final Button suggestionButton = getTalentRadarApplication()
						.getSkillButtonFactory().getSkillButton(
								NewHuntActivity.this, suggestion);
				suggestionButton
						.setOnClickListener(new SuggestionOnClickListener(
								suggestionContainer, skillContainer, editable));
				suggestionContainer.addView(suggestionButton);
			}

		}

		@Override
		public void beforeTextChanged(final CharSequence arg0, final int arg1,
				final int arg2, final int arg3) {
		}

		@Override
		public void onTextChanged(final CharSequence arg0, final int arg1,
				final int arg2, final int arg3) {
		}

	}

	private class SuggestionOnClickListener implements OnClickListener {

		private final ViewGroup suggestionContainer;
		private final ViewGroup skillContainer;
		private final Editable editable;

		public SuggestionOnClickListener(final ViewGroup suggestionContainer,
				final ViewGroup skillContainer, final Editable editable) {
			this.suggestionContainer = suggestionContainer;
			this.skillContainer = skillContainer;
			this.editable = editable;
		}

		@Override
		public void onClick(final View view) {
			if (view != null) {
				suggestionContainer.removeAllViews();
				editable.clear();
				addSkill(((Button) view).getText().toString(), skillContainer,
						editable);
			}
		}

	}

	private class SaveHuntClickListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final Hunt newHunt = buildHunt();
			final HuntingCriteriaEngine huntingCriteriaEngine = getTalentRadarApplication()
					.getHuntingCriteriaEngine();
			huntingCriteriaEngine.addHunt(newHunt);
			finish();
		}

	}
}
