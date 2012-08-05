package com.menatwork.skills;

import android.content.Context;
import android.widget.Button;

public interface SkillButtonFactory {

	Button getSkillButton(Context caller, String skill);

	Button getEmptySkillsButton(Context caller);

}
