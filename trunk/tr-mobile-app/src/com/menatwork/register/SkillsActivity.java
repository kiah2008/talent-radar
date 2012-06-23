package com.menatwork.register;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.menatwork.LoginActivity;
import com.menatwork.MainActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class SkillsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_skills);
		setupButtons();

		try {
			Bundle extras = getIntent().getExtras();
			Log.d("alme", RegistrationExtras.REALNAME);
			String realname = extras.getString(RegistrationExtras.REALNAME);
			String nickname = extras.getString(RegistrationExtras.NICKNAME);
			String email = extras.getString(RegistrationExtras.EMAIL);
			Log.d("alme", "realname " + realname + " nickname " + nickname
					+ " email " + email);
		} catch (NullPointerException e) {
			Log.e("alme", "screwed up dude", e);
		}
	}

	private void setupButtons() {
		Button finishButton = (Button) findViewById(R.id.register_skills_button_finish);
		Button cancelButton = (Button) findViewById(R.id.register_skills_button_cancel);
		
		finishButton.setOnClickListener(new StartActivityOnClickListener(this,
				MainActivity.class));
		cancelButton.setOnClickListener(new StartActivityOnClickListener(this,
				LoginActivity.class));
	}
}
