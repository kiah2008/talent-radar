package com.menatwork.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.menatwork.LoginActivity;
import com.mentatwork.R;

public class PasswordActivity extends DataInputActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_password);

		this.setupButtons();
	}

	private void setupButtons() {
		Button nextButton = (Button) findViewById(R.id.register_password_button_next);
		Button cancelButton = (Button) findViewById(R.id.register_password_button_cancel);

		nextButton.setOnClickListener(new StartActivityPassingDataListener(
				this, SkillsActivity.class));
		cancelButton.setOnClickListener(new StartActivityPassingDataListener(
				this, LoginActivity.class));
	}

	Bundle getConfiguredData() {
		Bundle data = new Bundle();
		data.putString(RegistrationExtras.PASSWORD, this.getControlPassword()
				.getText().toString());
		return data;
	}

	private TextView getControlPassword() {
		return (TextView) findViewById(R.id.register_password_password1);
	}
}
