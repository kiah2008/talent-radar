package com.menatwork.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mentatwork.R;

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

		nextButton.setOnClickListener(new StartActivityPassingDataListener(
				this, PasswordActivity.class));
		cancelButton.setOnClickListener(new CancelButtonListener(this));
	}

	private EditText getControlRealname() {
		return realname;
	}

	private EditText getControlNickname() {
		return nickname;
	}

	private EditText getControlEmail() {
		return email;
	}

	private CheckBox getControlPublicEmail() {
		return publicEmail;
	}

	private CheckBox getControlPublicRealname() {
		return publicRealname;
	}

	Bundle getConfiguredData() {
		Bundle data = new Bundle();
		data.putString(RegistrationExtras.REALNAME, getControlRealname()
				.getText().toString());
		data.putBoolean(RegistrationExtras.PUBLIC_REALNAME,
				getControlPublicRealname().isChecked());
		data.putString(RegistrationExtras.NICKNAME, getControlNickname()
				.getText().toString());
		data.putString(RegistrationExtras.EMAIL, getControlEmail().getText()
				.toString());
		data.putBoolean(RegistrationExtras.PUBLIC_EMAIL,
				getControlPublicEmail().isChecked());
		return data;
	}

}
