package com.menatwork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.menatwork.register.ChooseTypeActivity;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class LoginActivity extends Activity {
	public static final int DIALOG_WAIT = 0;
	private Button registerButton;
	private ImageButton linkedInButton;
	private Button loginButton;
	private EditText email;
	private EditText password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		findViewElements();
		setupButtons();
	}

	private void findViewElements() {
		registerButton = (Button) findViewById(R.id.login_button_register);
		loginButton = (Button) findViewById(R.id.login_button_login);
		linkedInButton = (ImageButton) findViewById(R.id.login_button_linkedin);
		email = (EditText) findViewById(R.id.login_email);
		password = (EditText) findViewById(R.id.login_password);
	}

	private void setupButtons() {
		getRegisterButton()
				.setOnClickListener(
						new StartActivityOnClickListener(this,
								ChooseTypeActivity.class));
		getLinkedInButton().setOnClickListener(
				new StartActivityOnClickListener(this, MainActivity.class));
		getLoginButton().setOnClickListener(
				new LoginButtonListener(this, MainActivity.class));
	}

	public Button getRegisterButton() {
		return registerButton;
	}

	public ImageButton getLinkedInButton() {
		return linkedInButton;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public EditText getEmail() {
		return email;
	}

	public EditText getPassword() {
		return password;
	}

	private class LoginButtonListener extends StartActivityOnClickListener {

		public LoginButtonListener(Activity source,
				Class<? extends Activity> destiny) {
			super(source, destiny);
		}

		@Override
		public void onClick(View v) {

			String email = getEmail().getText().toString();
			String password = getPassword().getText().toString();

			LoginTask task = new LoginTask();
			task.execute(email, password);

			super.onClick(v);
		}
	}

	private class LoginTask extends AsyncTask<String, Integer, Boolean> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "",
					"Autenticando...", true);
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
		}
	}
}