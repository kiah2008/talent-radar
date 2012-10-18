package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.menatwork.model.User;

public class SimpleSkillHuntBuilder {

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	private String title;
	private final List<String> requiredSkills;
	private final List<String> preferredSkills;
	private final List<User> users;

	public static SimpleSkillHuntBuilder newInstance() {
		return new SimpleSkillHuntBuilder();
	}

	protected SimpleSkillHuntBuilder() {
		this.requiredSkills = new ArrayList<String>();
		this.preferredSkills = new ArrayList<String>();
		this.users = new ArrayList<User>();
	}

	// ************************************************ //
	// ====== Build method ======
	// ************************************************ //

	public SimpleSkillHunt build() {
		return new SimpleSkillHunt(title, requiredSkills, preferredSkills,
				users);
	}

	// ************************************************ //
	// ====== Setter methods ======
	// ************************************************ //

	public SimpleSkillHuntBuilder setTitle(final String title) {
		this.title = title;
		return this;
	}

	public SimpleSkillHuntBuilder addRequiredSkills(
			final String... requiredSkills) {
		this.requiredSkills.addAll(Arrays.asList(requiredSkills));
		return this;
	}

	public SimpleSkillHuntBuilder addPreferredSkills(
			final String... preferredSkills) {
		this.preferredSkills.addAll(Arrays.asList(preferredSkills));
		return this;
	}

	public SimpleSkillHuntBuilder addUsers(final User... users) {
		this.users.addAll(Arrays.asList(users));
		return this;
	}

}
