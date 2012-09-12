package com.menatwork;

import com.menatwork.model.User;
import com.menatwork.preferences.TalentRadarPreferences;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

public class DispatcherActivity extends TalentRadarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		TODO - leaving this part with no effect since it is still unstable.
		
		//		ProgressDialog progress = ProgressDialog.show(this,
//				getString(R.string.generic_wait), "");
		
//		try {
//			TalentRadarPreferences preferences = getTalentRadarApplication()
//					.getPreferences();
//			String localUserId = preferences.getLocalUserId();
//			if (User.EMPTY_USER_ID.equals(localUserId)) {
				startActivity(new Intent(this, LoginActivity.class));
//			} else {
//				User localUser = getUserById(localUserId);
//				getTalentRadarApplication().loadLocalUser(localUser);
//				startActivity(new Intent(this, MainActivity.class));
//			}
//		} finally {
//			progress.dismiss();
//		}
	}
}
