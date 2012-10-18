package com.menatwork.hunts;

import java.util.List;

import android.content.Intent;

import com.menatwork.model.User;

public class SimpleSkillHunt implements Hunt {

	private static final String SEPARATOR_BETWEEN_REQUIREMENTS = "\n";
	private static final String SEPARATOR_BETWEEN_SKILLS = ", ";
	private final String title;
	private final List<String> requiredSkills;
	private final List<String> preferredSkills;
	private final List<User> users;

	public SimpleSkillHunt(final String title,
			final List<String> requiredSkills,
			final List<String> preferredSkills, final List<User> users) {
		this.title = title;
		this.requiredSkills = requiredSkills;
		this.preferredSkills = preferredSkills;
		this.users = users;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getQuantity() {
		return users.size();
	}

	@Override
	public String getDescription() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Requerido: ");
		stringBuilder.append( //
				concatStringsWithSep(requiredSkills, SEPARATOR_BETWEEN_SKILLS));
		stringBuilder.append(SEPARATOR_BETWEEN_REQUIREMENTS);
		stringBuilder.append("Preferido: ");
		stringBuilder.append( //
				concatStringsWithSep(preferredSkills, SEPARATOR_BETWEEN_SKILLS));
		return stringBuilder.toString();
	}

	@Override
	public Intent getIntent() {
		return null;
	}

	// ************************************************ //
	// ====== Other utils ======
	// ************************************************ //

	public String concatStringsWithSep(final List<String> strings,
			final String separator) {
		final StringBuilder sb = new StringBuilder();
		String sep = "";
		for (final String s : strings) {
			sb.append(sep).append(s);
			sep = separator;
		}
		return sb.toString();
	}
}
