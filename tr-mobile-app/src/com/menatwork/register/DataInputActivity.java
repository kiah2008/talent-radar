package com.menatwork.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

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

class DataCarrierListener implements OnClickListener {
	private final DataInputActivity source;
	private final Class<? extends Activity> destiny;

	public DataCarrierListener(DataInputActivity source,
			Class<? extends Activity> destiny) {
		this.source = source;
		this.destiny = destiny;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(source, destiny);
		Bundle configuredData = source.getConfiguredData();
		Bundle extras = source.getIntent().getExtras();
		if (extras != null) {
			extras.putAll(configuredData);
			intent.putExtras(extras);
		} else {
			intent.putExtras(configuredData);
		}
		source.startActivity(intent);
	}

}
