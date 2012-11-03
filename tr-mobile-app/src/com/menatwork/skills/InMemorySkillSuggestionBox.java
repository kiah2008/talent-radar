package com.menatwork.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InMemorySkillSuggestionBox implements SkillSuggestionBox {

	private List<String> skillList;

	private SearchAlgorithm searchAlgorithm;

	public InMemorySkillSuggestionBox() {
		skillList = Collections.<String> emptyList();
		this.setSearchAlgorithm(new BruteForceSearchAlgorithm());
	}

	@Override
	public void setSkills(final String... skills) {
		skillList = new ArrayList<String>(Arrays.asList(skills));
		this.searchAlgorithm.initialize(skillList);
	}

	@Override
	public void setSkills(final List<String> skills) {
		skillList = skills;
		this.searchAlgorithm.initialize(skillList);
	}

	@Override
	public void setSearchAlgorithm(final SearchAlgorithm searchAlgorithm) {
		this.searchAlgorithm = searchAlgorithm;
		this.searchAlgorithm.initialize(skillList);
	}

	@Override
	public List<String> getSuggestionsFor(final String term) {
		if (searchAlgorithm == null)
			throw new IllegalStateException("Search algorithm not set");
		return searchAlgorithm.search(term);
	}

}
