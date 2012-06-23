package com.menatwork.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.menatwork.LoginActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class EssentialsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_essential);

		this.setupButtons();
	}

	private void setupButtons() {
		Button nextButton = (Button) findViewById(R.id.register_button_next);
		Button cancelButton = (Button) findViewById(R.id.register_button_cancel);

		nextButton.setOnClickListener(new NextButtonClickListener());
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

	private class NextButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			final Intent intent = new Intent(EssentialsActivity.this,
					PasswordActivity.class);
			intent.putExtra(RegistrationExtras.REALNAME, getControlRealname()
					.getText().toString());
			intent.putExtra(RegistrationExtras.NICKNAME, getControlNickname()
					.getText().toString());
			intent.putExtra(RegistrationExtras.EMAIL, getControlEmail()
					.getText().toString());
			
			startActivity(intent);
		}
	}

}
