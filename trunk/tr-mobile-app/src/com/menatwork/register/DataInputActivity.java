package com.menatwork.register;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.menatwork.utils.LogUtils;

abstract class DataInputActivity extends Activity {
	abstract Bundle getConfiguredData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.logIncomingData();
	}

	private final void logIncomingData() {
		Bundle extras = this.getIntent().getExtras();
		if (extras == null) {
			Log.d(this.getClass().getName(), "This activity received no input");
		} else {
			LogUtils.d(this, "Input parameters for current activity", extras);

		}
	}
}
