package com.menatwork.skills;

import java.util.List;

public interface SkillSuggestionBox {

	public List<String> getSuggestionsFor(final String term);

	public void setSkills(String... skills);

	void setSkills(List<String> skills);

	public void setSearchAlgorithm(SearchAlgorithm searchAlgorithm);

}
