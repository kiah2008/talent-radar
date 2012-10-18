package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.menatwork.model.User;
import com.menatwork.service.Defect;

public class SimpleSkillHuntBuilder {

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	private String title;
	private final List<String> requiredSkills;
	private final List<String> preferredSkills;
	private final List<User> users;
	private String id;

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
		if (id == null)
			throw new Defect("can't instantiate a Hunt without id");

		return new SimpleSkillHunt(id, title, requiredSkills, preferredSkills,
				users);
	}

	// ************************************************ //
	// ====== Setter methods ======
	// ************************************************ //

	public SimpleSkillHuntBuilder setId(final String id) {
		this.id = id;
		return this;
	}

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
