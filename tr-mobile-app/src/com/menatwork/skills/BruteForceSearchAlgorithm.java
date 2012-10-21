package com.menatwork.skills;

import java.util.ArrayList;
import java.util.List;

public class BruteForceSearchAlgorithm implements SearchAlgorithm {

	private List<String> skillList;

	@Override
	public void initialize(final List<String> skillList) {
		this.skillList = skillList;

		// Collections.sort(skillList, new AlphabeticalComparator());
	}

	@Override
	public List<String> search(final String term) {
		final List<String> results = new ArrayList<String>();
		for (final String skill : skillList)
			if (skill.toLowerCase().contains(term.toLowerCase()))
				results.add(skill);
		return results;
	}

	// private class AlphabeticalComparator implements Comparator<String> {
	//
	// @Override
	// public int compare(final String lhs, final String rhs) {
	// return lhs.compareTo(rhs);
	// }
	//
	// }
}
