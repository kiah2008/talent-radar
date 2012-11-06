package com.menatwork.hunts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.menatwork.model.User;
import com.menatwork.service.Defect;

public class SimpleSkillHuntBuilder {

	private String id;
	private String title;
	private List<String> requiredSkills;
	private List<String> preferredSkills;
	private List<User> users;

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	protected SimpleSkillHuntBuilder() {
		this.title = "";
		this.requiredSkills = new ArrayList<String>();
		this.preferredSkills = new ArrayList<String>();
		this.users = new ArrayList<User>();
	}

	public static SimpleSkillHuntBuilder newInstance() {
		return new SimpleSkillHuntBuilder();
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

	public SimpleSkillHuntBuilder setRequiredSkills(
			final List<String> requiredSkills) {
		this.requiredSkills = requiredSkills;
		return this;
	}

	public SimpleSkillHuntBuilder setPreferredSkills(
			final List<String> preferredSkills) {
		this.preferredSkills = preferredSkills;
		return this;
	}

	public SimpleSkillHuntBuilder setUsers(final List<User> users) {
		this.users = users;
		return this;
	}

	public SimpleSkillHuntBuilder addRequiredSkills(
			final String... requiredSkills) {
		return addRequiredSkills(Arrays.asList(requiredSkills));
	}

	public SimpleSkillHuntBuilder addRequiredSkills(final List<String> asList) {
		this.requiredSkills.addAll(asList);
		return this;
	}

	public SimpleSkillHuntBuilder addPreferredSkills(
			final String... preferredSkills) {
		return addPreferredSkills(Arrays.asList(preferredSkills));
	}

	public SimpleSkillHuntBuilder addPreferredSkills(final List<String> asList) {
		this.preferredSkills.addAll(asList);
		return this;
	}

	public SimpleSkillHuntBuilder addUsers(final User... users) {
		this.users.addAll(Arrays.asList(users));
		return this;
	}

}