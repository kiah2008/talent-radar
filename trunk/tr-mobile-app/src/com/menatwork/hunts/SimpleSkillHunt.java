package com.menatwork.hunts;

import java.util.List;

import com.menatwork.model.User;

public class SimpleSkillHunt implements Hunt {

	private static final String SEPARATOR_BETWEEN_REQUIREMENTS = "\n";
	private static final String SEPARATOR_BETWEEN_SKILLS = ", ";

	private String title;
	private List<String> requiredSkills;
	private List<String> preferredSkills;

	private final List<User> users;
	private final String id;

	public SimpleSkillHunt( //
			final String id, //
			final String title, //
			final List<String> requiredSkills, //
			final List<String> preferredSkills, //
			final List<User> users) {
		this.id = id;
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
	public List<User> getUsers() {
		return users;
	}

	@Override
	public String getId() {
		return id;
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

	@Override
	public boolean addUserIfCriteriaMatched(final User user) {
		if (hasRequiredSkills(user))
			return addUser(user);

		return false;
	}

	private boolean addUser(final User user) {
		if (!isUserAlreadyInHunt(user))
			return users.add(user);

		return false;
	}

	private boolean isUserAlreadyInHunt(final User user) {
		return users.contains(user) || hasUserWithSameId(user);
	}

	private boolean hasUserWithSameId(final User newUser) {
		for (final User user : users)
			if (user.getId().equals(newUser.getId()))
				return true;

		return false;
	}

	private boolean hasRequiredSkills(final User user) {
		for (final String requiredSkill : requiredSkills)
			if (!user.hasSkill(requiredSkill))
				return false;

		return true;
	}

	@Override
	public List<String> getRequiredSkills() {
		return requiredSkills;
	}

	@Override
	public List<String> getPreferredSkills() {
		return preferredSkills;
	}

	@Override
	public void setTitle(final String title) {
		this.title = title;
	}

	@Override
	public void setRequiredSkills(final List<String> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	@Override
	public void setPreferredSkills(final List<String> preferredSkills) {
		this.preferredSkills = preferredSkills;
	}
}
