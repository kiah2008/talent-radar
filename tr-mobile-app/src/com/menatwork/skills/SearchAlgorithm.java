package com.menatwork.skills;

import java.util.List;

public interface SearchAlgorithm {

	void initialize(List<String> skillList);

	List<String> search(String term);

}
