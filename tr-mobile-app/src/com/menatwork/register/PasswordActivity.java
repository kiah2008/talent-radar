package com.menatwork.register;

import com.menatwork.LoginActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class PasswordActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_password);

		this.setupButtons();
	}

	private void setupButtons() {
		Button nextButton = (Button) findViewById(R.id.register_password_button_next);
		Button cancelButton = (Button) findViewById(R.id.register_password_button_cancel);

		nextButton.setOnClickListener(new StartActivityOnClickListener(this,
				SkillsActivity.class));
		cancelButton.setOnClickListener(new StartActivityOnClickListener(this,
				LoginActivity.class));
	}
}
