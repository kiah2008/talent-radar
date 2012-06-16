package com.menatwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TextView textview = new TextView(this);
		textview.setText("Profile tab");
		setContentView(textview);
	}

}
