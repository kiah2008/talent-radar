package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.menatwork.model.User;
import com.menatwork.register.ChooseTypeActivity;
import com.menatwork.service.GetUser;
import com.menatwork.service.GetUserResponse;
import com.menatwork.service.GetUserSkills;
import com.menatwork.service.GetUserSkillsResponse;
import com.menatwork.service.Login;
import com.menatwork.service.LoginResponse;
import com.menatwork.utils.NaiveDialogClickListener;
import com.menatwork.utils.StartActivityListener;

public class LoginActivity extends TalentRadarActivity {
	public static final int DIALOG_INCORRECT_LOGIN = 1;
	public static final int DIALOG_ERROR = 2;
	private Button registerButton;
	private ImageButton linkedInButton;
	private Button loginButton;
	private EditText email;
	private EditText password;

	@Override
	protected int getViewLayoutId() {
		return R.layout.login;
	}

	@Override
	protected void findViewElements() {
		registerButton = findButtonById(R.id.login_button_register);
		loginButton = findButtonById(R.id.login_button_login);
		linkedInButton = findImageButtonById(R.id.login_button_linkedin);
		email = findEditTextById(R.id.login_email);
		password = findEditTextById(R.id.login_password);
	}

	@Override
	protected void setupButtons() {
		registerButton.setOnClickListener(new StartActivityListener(this,
				ChooseTypeActivity.class));
		linkedInButton.setOnClickListener(new LoginWithLinkedinListener());
		loginButton.setOnClickListener(new LoginButtonListener());
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

	@Override
	protected void onNewIntent(final Intent intent) {
		// handle logging in with linked in
		Log.d("LoginActivity", "Linkedin access");
		final Uri data = intent.getData();
		final String userId = data.getPathSegments().get(0);
		if (userId == null) {
			showDialog(DIALOG_ERROR);
			return;
		}
		Log.d("LoginActivity", "Returning id from Login with Linkedin service");
		Log.d("LoginActivity", userId);
		new LoadLocalUserTask().execute(userId);
	}

	private class LoginButtonListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final LoginTask task = new LoginTask();
			task.execute(email.getText().toString(), password.getText()
					.toString());
		}
	}

	private class LoginWithLinkedinListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.uri_login_with_linkedin)));
			startActivity(browserIntent);
		}

	}

	private class LoadLocalUserTask extends
			AsyncTask<String, Void, GetUserResponse> {
		private ProgressDialog progressDialog;
		private GetUserSkillsResponse getUserSkillsResponse;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "",
					getString(R.string.login_authenticating), true);
		}

		@Override
		protected GetUserResponse doInBackground(final String... params) {
			try {
				final GetUser getUser = GetUser.newInstance(LoginActivity.this,
						params[0]);
				final GetUserSkills getUserSkills = GetUserSkills.newInstance(
						LoginActivity.this, params[0]);
				final GetUserResponse getUserResponse = getUser.execute();
				getUserSkillsResponse = getUserSkills.execute();
				return getUserResponse;
			} catch (final JSONException e) {
				Log.e("LoginTask", "Error parsing JSON response", e);
			} catch (final IOException e) {
				Log.e("LoginTask", "IO error trying to get user data", e);
			}
			return null;
		}

		@Override
		protected void onPostExecute(final GetUserResponse result) {
			progressDialog.dismiss();
			if (result != null && result.isSuccessful()) {
				final User user = result.getUser();
				user.setSkills(getUserSkillsResponse.getSkills());
				getTalentRadarApplication().loadLocalUser(user);
				final Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
			} else {
				showDialog(DIALOG_ERROR);
			}
		}

	}

	private class LoginTask extends AsyncTask<String, Void, LoginResponse> {

		public static final int SUCCESS = 0;
		public static final int WRONG_ID = 1;
		public static final int ERROR = 2;
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "",
					getString(R.string.login_authenticating), true);
		}

		@Override
		protected LoginResponse doInBackground(final String... arg0) {
			try {
				final Login login = Login.newInstance(LoginActivity.this,
						arg0[0], arg0[1]);
				return this.handleResponse(login.execute());
			} catch (final JSONException e) {
				Log.e("LoginTask", "Error parsing JSON response", e);
				return null;
			} catch (final IOException e) {
				Log.e("LoginTask", "IO error trying to log in", e);
				return null;
			}
		}

		private LoginResponse handleResponse(final LoginResponse response) {
			Log.d("LoginTask", "JSON Response");
			Log.d("LoginTask", response.toString());
			return response;
		}

		@Override
		protected void onPostExecute(final LoginResponse result) {
			progressDialog.dismiss();
			if (result.isValid() && result.isSuccessful()) {
				((TalentRadarApplication) getApplication())
						.loadLocalUser(result.getUser());
				final Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
			} else if (result.isValid())
				showDialog(DIALOG_INCORRECT_LOGIN);
			else
				showDialog(DIALOG_ERROR);
		}
	}

}