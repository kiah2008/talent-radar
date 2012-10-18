package com.menatwork.hunts;

import java.util.List;

import android.content.Intent;

import com.menatwork.model.User;

public interface Hunt {

	String getTitle();

	int getQuantity();

	String getDescription();

	Intent getIntent();

	List<User> getUsers();

	String getId();

}
