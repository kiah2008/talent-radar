package com.menatwork.radar;

import java.util.List;

import com.menatwork.model.User;

public interface RadarListener {

	void onNewSurroundingUsers(List<? extends User> surroundingUsers);

}
