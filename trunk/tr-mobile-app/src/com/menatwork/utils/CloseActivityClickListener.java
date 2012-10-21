package com.menatwork.utils;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class CloseActivityClickListener implements OnClickListener {

	private final Activity activity;

	public CloseActivityClickListener(final Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(final View v) {
		activity.finish();
	}

}
