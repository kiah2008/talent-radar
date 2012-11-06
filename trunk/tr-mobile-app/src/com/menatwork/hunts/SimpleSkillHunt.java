package com.menatwork.hunts;

import java.util.List;

import com.menatwork.model.User;
import com.menatwork.utils.StringUtils;

public class SimpleSkillHunt extends BaseHunt {

	private static final String SEPARATOR_BETWEEN_REQUIREMENTS = "\n";
	private static final String SEPARATOR_BETWEEN_SKILLS = ", ";

	private String title;
	private List<String> requiredSkills;
	private List<String> preferredSkills;

	private final String id;

	public SimpleSkillHunt( //
			final String id, //
			final String title, //
			final List<String> requiredSkills, //
			final List<String> preferredSkills, //
			final List<User> users) {
		super(users);
		this.id = id;
		this.title = title;
		this.requiredSkills = requiredSkills;
		this.preferredSkills = preferredSkills;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getDescription() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Requerido: ");
		stringBuilder.append( //
				StringUtils.concatStringsWithSep(requiredSkills,
						SEPARATOR_BETWEEN_SKILLS));
		stringBuilder.append(SEPARATOR_BETWEEN_REQUIREMENTS);
		stringBuilder.append("Preferido: ");
		stringBuilder.append( //
				StringUtils.concatStringsWithSep(preferredSkills,
						SEPARATOR_BETWEEN_SKILLS));
		return stringBuilder.toString();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean addUserIfCriteriaMatched(final User user) {
		if (hasRequiredSkills(user))
			return addUser(user);

		return false;
	}

	private boolean hasRequiredSkills(final User user) {
		for (final String requiredSkill : requiredSkills)
			if (!user.hasSkill(requiredSkill))
				return false;

		return true;
	}

	public List<String> getRequiredSkills() {
		return requiredSkills;
	}

	public List<String> getPreferredSkills() {
		return preferredSkills;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setRequiredSkills(final List<String> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public void setPreferredSkills(final List<String> preferredSkills) {
		this.preferredSkills = preferredSkills;
	}

	@Override
	public String toString() {
		return "SimpleSkillHunt [title=" + title + ", requiredSkills="
				+ requiredSkills + ", preferredSkills=" + preferredSkills
				+ ", users=" + users + ", id=" + id + "]";
	}

}
