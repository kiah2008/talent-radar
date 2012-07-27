package com.menatwork.radar;

import java.util.Collection;
import java.util.EventListener;

import com.menatwork.model.User;

public interface RadarServiceListener extends EventListener {

	void usersFound(Collection<? extends User> users);

}
