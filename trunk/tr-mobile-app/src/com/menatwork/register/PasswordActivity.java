package com.menatwork.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mentatwork.R;

public class PasswordActivity extends DataInputActivity {

	// TODO define/implement restrictions on passwords (a..z|1..9)

	private EditText password1;
	private EditText password2;
	private Button nextButton;
	private Button cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_password);
		findViewElements();
		this.setupButtons();
		this.setupPasswordTextWatchers();
	}

	private void findViewElements() {
		password1 = (EditText) findViewById(R.id.register_password_password1);
		password2 = (EditText) findViewById(R.id.register_password_password2);
		nextButton = (Button) findViewById(R.id.register_password_button_next);
		cancelButton = (Button) findViewById(R.id.register_password_button_cancel);
	}

	private void setupPasswordTextWatchers() {
		getControlPassword1().addTextChangedListener(
				new PasswordTextWatcher(getControlPassword2()));
		this.getControlPassword2().addTextChangedListener(
				new PasswordTextWatcher(getControlPassword1()));
	}

	private void setupButtons() {
		nextButton.setEnabled(false);
		nextButton.setOnClickListener(new StartActivityPassingDataListener(
				this, SkillsActivity.class));
		cancelButton.setOnClickListener(new CancelButtonListener(this));
	}

	Bundle getConfiguredData() {
		Bundle data = new Bundle();
		data.putString(RegistrationExtras.PASSWORD, this.getControlPassword()
				.getText().toString());
		return data;
	}

	private EditText getControlPassword1() {
		return password1;
	}

	private EditText getControlPassword2() {
		return password2;
	}

	private TextView getControlPassword() {
		return (TextView) findViewById(R.id.register_password_password1);
	}

	private class PasswordTextWatcher implements TextWatcher {

		private final EditText theOneToCompareAgainst;

		public PasswordTextWatcher(EditText theOneToCompareAgainst) {
			this.theOneToCompareAgainst = theOneToCompareAgainst;
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s == null)
				return;
			String thisPassword = s.toString();
			String otherPassword = theOneToCompareAgainst.getText().toString();
			boolean enabled = "".equals(thisPassword) ? false : thisPassword
					.equals(otherPassword);
			((Button) findViewById(R.id.register_password_button_next))
					.setEnabled(enabled);
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
