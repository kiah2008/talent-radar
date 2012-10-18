package com.menatwork.skills;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import com.menatwork.R;

public class DefaultSkillButtonFactory implements SkillButtonFactory {

	private DefaultSkillButtonFactory() {

	}

	public static SkillButtonFactory newInstance() {
		return new DefaultSkillButtonFactory();
	}

	@Override
	public Button getSkillButton(final Context caller, final String skill) {
		final Button button = createButton(caller);
		button.setText(skill);
		button.setBackgroundResource(R.drawable.skill);
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		return button;
	}

	@Override
	public Button getEmptySkillsButton(final Context caller) {
		final Button button = new EmptySkillButton(caller);
		button.setText(R.string.profile_no_skills_button);
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		button.setEnabled(false);
		return button;
	}

	private Button createButton(final Context caller) {
		final Button button = new Button(caller);
		return button;
	}

	public static class EmptySkillButton extends Button {
		public EmptySkillButton(final Context context) {
			super(context);
		}

		@Override
		public boolean equals(final Object o) {
			return o instanceof EmptySkillButton;

		}
	}

}
