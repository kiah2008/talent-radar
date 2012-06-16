package com.menatwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class RadarActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TextView textview = new TextView(this);
		textview.setText("Radar tab");
		setContentView(textview);
	}

}
