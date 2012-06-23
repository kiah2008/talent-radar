package com.menatwork.register;

import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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
			Set<String> keys = extras.keySet();
			for (String key : keys) {
				Log.d(this.getClass().getName(), key + " = " + extras.get(key));
			}
		}
	}
}
