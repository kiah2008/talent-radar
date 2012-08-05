package com.menatwork.skills;

import com.menatwork.R;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class DefaultSkillButtonFactory implements SkillButtonFactory {

	private DefaultSkillButtonFactory() {

	}

	public static SkillButtonFactory newInstance() {
		return new DefaultSkillButtonFactory();
	}

	@Override
	public Button getSkillButton(Context caller, String skill) {
		Button button = createButton(caller);
		button.setText(skill);
		button.setBackgroundResource(R.drawable.skill);
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		return button;
	}

	@Override
	public Button getEmptySkillsButton(Context caller) {
		Button button = createButton(caller);
		button.setText(R.string.profile_no_skills_button);
		button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		button.setEnabled(false);
		return button;
	}

	private Button createButton(Context caller) {
		Button button = new Button(caller);
		return button;
	}

}
