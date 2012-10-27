package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.menatwork.register.EssentialsActivity;
import com.menatwork.service.Login;
import com.menatwork.service.response.LoginResponse;
import com.menatwork.utils.LogUtils;
import com.menatwork.utils.NaiveDialogClickListener;
import com.menatwork.utils.StartActivityListener;

public class LoginWithTalentRadarActivity extends AbstractLoginActivity {
	public static final int DIALOG_INCORRECT_LOGIN = 1;
	public static final int DIALOG_ERROR = 2;

	private Button registerButton;
	private Button loginButton;
	private EditText email;
	private EditText password;

	@Override
	protected void setupButtons() {
		registerButton.setOnClickListener(new StartActivityListener(this,
				EssentialsActivity.class));
		registerButton.setEnabled(false);
		loginButton.setOnClickListener(new LoginButtonListener());
	}

	@Override
	protected void findViewElements() {
		registerButton = findButtonById(R.id.login_button_register);
		loginButton = findButtonById(R.id.login_button_login);
		email = findEditTextById(R.id.login_email);
		password = findEditTextById(R.id.login_password);
	}

	@Override
	protected int getViewLayoutId() {
		return R.layout.login_tr;
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		final Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_INCORRECT_LOGIN:
			builder.setTitle(this
					.getString(R.string.login_dialog_incorrectLogin_title));
			builder.setMessage(this
					.getString(R.string.login_dialog_incorrectLogin_message));
			builder.setPositiveButton("OK", new NaiveDialogClickListener());
			return builder.create();
		case DIALOG_ERROR:
			builder.setTitle(this.getString(R.string.login_dialog_error_title));
			builder.setMessage(this
					.getString(R.string.login_dialog_error_message));
			builder.setPositiveButton("OK", new NaiveDialogClickListener());
			return builder.create();
		}
		return null;
	}

	private void showIOExceptionMessage() {
		Toast.makeText(this, R.string.login_unknown_host_exception,
				Toast.LENGTH_LONG).show();
	}

	private class LoginButtonListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final LoginTask task = new LoginTask();
			task.execute(email.getText().toString(), password.getText()
					.toString());
		}
	}

	private class LoginTask extends AsyncTask<String, Void, LoginResponse> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(
					LoginWithTalentRadarActivity.this, "",
					getString(R.string.login_authenticating), true);
		}

		@Override
		protected LoginResponse doInBackground(final String... arg0) {
			try {
				final Login login = Login.newInstance(
						LoginWithTalentRadarActivity.this, arg0[0], arg0[1]);
				final LoginResponse loginResponse = login.execute();
				LogUtils.d(LoginWithTalentRadarActivity.this, "Login response",
						loginResponse);
				return loginResponse;
			} catch (final JSONException e) {
				Log.e("LoginTask", "Error parsing JSON response", e);
				return null;
			} catch (final IOException e) {
				Log.e("LoginTask", "IO error trying to log in", e);
				return null;
			}
		}

		@Override
		protected void onPostExecute(final LoginResponse result) {
			if (result != null) {
				if (result.isValid() && result.isSuccessful())
					finishSuccessfulLogin(LoginWithTalentRadarActivity.this,
							result.getUser(), progressDialog);
				else if (result.isValid())
					showDialog(DIALOG_INCORRECT_LOGIN);
				else
					showDialog(DIALOG_ERROR);
			} else {
				progressDialog.dismiss();
				showIOExceptionMessage();
			}
			// this code is just here for testing purpose only when inet
			// connection is not available

			// final UserBuilder userBuilder = UserBuilder.newInstance();
			// userBuilder.setId("asdfsdf");
			// userBuilder.setUserName("Mikeys");
			// userBuilder.setUserSurname("O");
			// userBuilder.setEmail("omikeys@ohoh.com");
			// userBuilder.setHeadline("Groso de la vida");
			// final User user = userBuilder.build();
			// finishSuccessfulLogin(user, progressDialog);
		}

	}
}
