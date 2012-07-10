package com.menatwork.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.menatwork.R;

public class EssentialsActivity extends DataInputActivity {

	private EditText realname;
	private EditText nickname;
	private EditText email;
	private CheckBox publicEmail;
	private CheckBox publicRealname;
	private Button nextButton;
	private Button cancelButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_essential);
		findViewElements();
		this.setupButtons();
	}

	private void findViewElements() {
		realname = (EditText) findViewById(R.id.register_realname);
		nickname = (EditText) findViewById(R.id.register_nickname);
		email = (EditText) findViewById(R.id.register_email);
		publicEmail = (CheckBox) findViewById(R.id.register_checkbox_public_email);
		publicRealname = (CheckBox) findViewById(R.id.register_checkbox_public_realname);
		nextButton = (Button) findViewById(R.id.register_button_next);
		cancelButton = (Button) findViewById(R.id.register_button_cancel);
	}

	private void setupButtons() {

		nextButton.setOnClickListener(new DataCarrierListener(
				this, PasswordActivity.class));
		cancelButton.setOnClickListener(new CancelButtonListener(this));
	}

	Bundle getConfiguredData() {
		Bundle data = new Bundle();
		data.putString(RegistrationExtras.REALNAME, realname.getText()
				.toString());
		data.putBoolean(RegistrationExtras.PUBLIC_REALNAME,
				publicRealname.isChecked());
		data.putString(RegistrationExtras.NICKNAME, nickname.getText()
				.toString());
		data.putString(RegistrationExtras.EMAIL, email.getText().toString());
		data.putBoolean(RegistrationExtras.PUBLIC_EMAIL,
				publicEmail.isChecked());
		return data;
	}

}
