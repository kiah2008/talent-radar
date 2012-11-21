package com.menatwork.hunts;

import java.util.EventListener;
import java.util.List;

import com.menatwork.model.User;

public interface HuntingCriteriaListener extends EventListener {

	void onUsersAddedToHunt(Hunt hunt, List<User> newUsers);

}
