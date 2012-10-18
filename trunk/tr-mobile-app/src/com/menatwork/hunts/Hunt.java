package com.menatwork.hunts;

import android.content.Intent;

public interface Hunt {

	String getTitle();

	int getQuantity();

	String getDescription();

	Intent getIntent();

}
