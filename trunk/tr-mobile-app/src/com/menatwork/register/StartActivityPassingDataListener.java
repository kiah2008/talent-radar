package com.menatwork.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

class StartActivityPassingDataListener implements OnClickListener {
	private final DataInputActivity source;
	private final Class<? extends Activity> destiny;

	public StartActivityPassingDataListener(DataInputActivity source,
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