package com.menatwork.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.menatwork.LoginActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class EssentialsActivity extends DataInputActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_essential);

		this.setupButtons();
	}

	private void setupButtons() {
		Button nextButton = (Button) findViewById(R.id.register_button_next);
		Button cancelButton = (Button) findViewById(R.id.register_button_cancel);

		nextButton.setOnClickListener(new StartActivityPassingDataListener(
				this, PasswordActivity.class));
		cancelButton.setOnClickListener(new StartActivityOnClickListener(this,
				LoginActivity.class));
	}

	private TextView getControlRealname() {
		return (TextView) findViewById(R.id.register_realname);
	}

	private TextView getControlNickname() {
		return (TextView) findViewById(R.id.register_nickname);
	}

	private TextView getControlEmail() {
		return (TextView) findViewById(R.id.register_email);
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

	private CheckBox getControlPublicEmail() {
		return (CheckBox) findViewById(R.id.register_checkbox_public_email);
	}

	private CheckBox getControlPublicRealname() {
		return (CheckBox) findViewById(R.id.register_checkbox_public_realname);
	}

}
