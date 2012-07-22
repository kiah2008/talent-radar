package com.menatwork.model;

import java.util.List;

public interface User {

	String getFullName();

	String getHeadline();

	List<String> getSkills();

	void setSkills(List<String> skills);

}
