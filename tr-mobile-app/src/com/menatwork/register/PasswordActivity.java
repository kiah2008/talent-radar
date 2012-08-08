package com.menatwork.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.menatwork.R;

public class PasswordActivity extends DataInputActivity {

	private EditText password1;
	private EditText password2;
	private Button nextButton;
	private Button cancelButton;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
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
		password1.addTextChangedListener(new PasswordTextWatcher(password2));
		this.password2
				.addTextChangedListener(new PasswordTextWatcher(password1));
	}

	private void setupButtons() {
		nextButton.setEnabled(false);
		nextButton.setOnClickListener(new DataCarrierListener(this,
				SkillsActivity.class));
		cancelButton.setOnClickListener(new CancelButtonListener(this));
	}

	@Override
	Bundle getConfiguredData() {
		final Bundle data = new Bundle();
		data.putString(RegistrationExtras.PASSWORD, ((TextView) this
				.findViewById(R.id.register_password_password1)).getText()
				.toString());
		return data;
	}

	private class PasswordTextWatcher implements TextWatcher {

		// TODO implement password validation (a..z|0..9)
		// XXX - Tipititap - miguel - 07/08/2012
		// Pattern pattern = Pattern.compile("(a..z|0..9)");
		// pattern.matcher("unfuckingpasswordingresado");

		private final EditText theOneToCompareAgainst;

		public PasswordTextWatcher(final EditText theOneToCompareAgainst) {
			this.theOneToCompareAgainst = theOneToCompareAgainst;
		}

		@Override
		public void afterTextChanged(final Editable s) {
			if (s == null)
				return;
			final String thisPassword = s.toString();
			final String otherPassword = theOneToCompareAgainst.getText()
					.toString();
			final boolean enabled = "".equals(thisPassword) ? false
					: thisPassword.equals(otherPassword);
			nextButton.setEnabled(enabled);
		}

		@Override
		public void beforeTextChanged(final CharSequence s, final int start,
				final int count, final int after) {
		}

		@Override
		public void onTextChanged(final CharSequence s, final int start,
				final int before, final int count) {
		}

	}

}
