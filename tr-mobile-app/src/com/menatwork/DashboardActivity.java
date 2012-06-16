package com.menatwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DashboardActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TextView textview = new TextView(this);
		textview.setText("Dashboard tab");
		setContentView(textview);
	}

}
