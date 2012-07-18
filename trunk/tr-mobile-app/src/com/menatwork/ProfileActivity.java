package com.menatwork;

import com.menatwork.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.profile);

		// User localUser = ((TalentRadarApplication) getApplication())
		// .getLocalUser();
		// ((TextView) findViewById(R.id.profile_fullname)).setText(localUser
		// .getFullName());
		// ((TextView) findViewById(R.id.profile_headline)).setText(localUser
		// .getHeadline());
	}
}
