package com.menatwork.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class StartActivityOnClickListener implements OnClickListener {
	protected final Activity source;
	protected final Class<? extends Activity> destiny;
	protected Bundle extras;

	public StartActivityOnClickListener(Activity source,
			Class<? extends Activity> destiny, Bundle extras) {
		this(source, destiny);
		this.extras = extras;
	}

	public StartActivityOnClickListener(Activity source,
			Class<? extends Activity> destiny) {
		super();
		this.source = source;
		this.destiny = destiny;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(source, destiny);
		if (extras != null)
			intent.putExtras(extras);
		source.startActivity(intent);
	}

}