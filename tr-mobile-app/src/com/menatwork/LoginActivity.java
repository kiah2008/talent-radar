package com.menatwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.menatwork.register.RegisterChooseTypeActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class LoginActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		setupLoginWithLinkedinButton();
		setupRegisterButton();
	}

	private void setupRegisterButton() {
		Button registerButton = (Button) findViewById(R.id.login_button_register);
		registerButton.setOnClickListener(new StartActivityOnClickListener(
				this, RegisterChooseTypeActivity.class));
	}

	private void setupLoginWithLinkedinButton() {
		final ImageButton button = (ImageButton) findViewById(R.id.login_button_linkedin);
		button.setOnClickListener(new StartActivityOnClickListener(this,
				DashboardActivity.class));
	}

}