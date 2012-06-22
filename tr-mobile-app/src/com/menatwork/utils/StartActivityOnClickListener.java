package com.menatwork.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartActivityOnClickListener implements OnClickListener {
	private Class<? extends Activity> destiny;
	private Activity source;

	public StartActivityOnClickListener(Activity source,
			Class<? extends Activity> destiny) {
		super();
		this.destiny = destiny;
		this.source = source;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(source, destiny);
		source.startActivity(intent);
	}

}