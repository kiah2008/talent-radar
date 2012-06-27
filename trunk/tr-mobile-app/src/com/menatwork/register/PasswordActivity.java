package com.menatwork.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.menatwork.LoginActivity;
import com.mentatwork.R;

public class PasswordActivity extends DataInputActivity {

	// TODO define/implement restrictions on passwords (a..z|1..9)

	private EditText password1;
	private EditText password2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_password);

		this.setupButtons();
		this.setupSaraza();
	}

	private void setupSaraza() {
		this.getControlPassword1().addTextChangedListener(
				new Password1TextWatcher());
		this.getControlPassword2().addTextChangedListener(
				new Password2TextWatcher());
	}

	private EditText getControlPassword1() {
		if (password1 == null)
			password1 = (EditText) findViewById(R.id.register_password_password1);
		return password1;
	}

	private EditText getControlPassword2() {
		if (password2 == null)
			password2 = (EditText) findViewById(R.id.register_password_password2);
		return password2;
	}

	private void setupButtons() {
		Button nextButton = getControlButtonNext();
		Button cancelButton = (Button) findViewById(R.id.register_password_button_cancel);

		nextButton.setEnabled(false);
		nextButton.setOnClickListener(new StartActivityPassingDataListener(
				this, SkillsActivity.class));
		cancelButton.setOnClickListener(new StartActivityPassingDataListener(
				this, LoginActivity.class));
	}

	private Button getControlButtonNext() {
		return (Button) findViewById(R.id.register_password_button_next);
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

	private class Password1TextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			if (s == null)
				return;
			String thisPassword = s.toString();
			String otherPassword = getControlPassword2().getText().toString();
			boolean enabled = "".equals(thisPassword) ? false : thisPassword
					.equals(otherPassword);
			getControlButtonNext().setEnabled(enabled);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	}

	private class Password2TextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			if (s == null)
				return;
			String thisPassword = s.toString();
			String otherPassword = getControlPassword1().getText().toString();
			boolean enabled = "".equals(thisPassword) ? false : thisPassword
					.equals(otherPassword);
			getControlButtonNext().setEnabled(enabled);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	}
}
