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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.menatwork.model.User;
import com.menatwork.register.ChooseTypeActivity;
import com.menatwork.service.Login;
import com.menatwork.service.SaveDeviceId;
import com.menatwork.service.response.LoginResponse;
import com.menatwork.service.response.SaveDeviceIdResponse;
import com.menatwork.utils.LogUtils;
import com.menatwork.utils.NaiveDialogClickListener;
import com.menatwork.utils.StartActivityListener;

public class LoginActivity extends GuiTalentRadarActivity {
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
	protected void postCreate(final Bundle savedInstanceState) {
		// XXX - test-purposed, comment if necessary

		// is it because you're using the emulator? if it's just so try using
		// the method isRunningOnEmulator() ;)

		// GCMRegistrar.unregister(this);
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
		registerButton.setOnClickListener(new StartActivityListener(this, ChooseTypeActivity.class));
		linkedInButton.setOnClickListener(new LoginWithLinkedinListener());
		loginButton.setOnClickListener(new LoginButtonListener());
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		final Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_INCORRECT_LOGIN:
			builder.setTitle(this.getString(R.string.login_dialog_incorrectLogin_title));
			builder.setMessage(this.getString(R.string.login_dialog_incorrectLogin_message));
			builder.setPositiveButton("OK", new NaiveDialogClickListener());
			return builder.create();
		case DIALOG_ERROR:
			builder.setTitle(this.getString(R.string.login_dialog_error_title));
			builder.setMessage(this.getString(R.string.login_dialog_error_message));
			builder.setPositiveButton("OK", new NaiveDialogClickListener());
			return builder.create();
		}
		return null;
	}

	@Override
	protected void onNewIntent(final Intent intent) {
		// handle logging in with linked in
		Log.d("LoginActivity", "New intent: " + intent.getDataString());
		final Uri dataUri = intent.getData();
		if ("talent".equals(dataUri.getScheme())) {
			if ("notification".equals(dataUri.getSchemeSpecificPart()))
				handleTestNotification();
		} else if ("talent.call.linkedin.back".equals(dataUri.getScheme())) {
			handleLoginWithLinkedin(intent);
		}
	}

	private void handleTestNotification() {
		Toast.makeText(this, "Te abri— la app VITEH\'", Toast.LENGTH_LONG).show();
	}

	private void handleLoginWithLinkedin(final Intent intent) {
		LogUtils.d(this, "Returning from Login with Linkedin service", intent);
		final Uri data = intent.getData();
		final String userid = data.getPathSegments().get(0);
		if (userid == null) {
			showDialog(DIALOG_ERROR);
			return;
		}
		final ProgressDialog progressDialog = ProgressDialog.show(this, "",
				getString(R.string.login_authenticating), true);
		Log.d("LoginActivity", "Returning id from Login with Linkedin service");
		Log.d("LoginActivity", userid);
		final User user = getUserById(userid);
		this.finishSuccessfulLogin(user, progressDialog);
	}

	private void finishSuccessfulLogin(final User user, final ProgressDialog progressDialog) {
		if (isRunningOnEmulator()) {
			getTalentRadarApplication().loadLocalUser(user);
			progressDialog.dismiss();
			final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
		} else
			new SaveDeviceIdTask() {

				@Override
				protected void onPostExecute(final SaveDeviceIdResponse result) {
					getTalentRadarApplication().loadLocalUser(user);
					progressDialog.dismiss();
					final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}

			}.execute(user.getId());
	}

	// ******** login innards *********

	private class LoginButtonListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final LoginTask task = new LoginTask();
			// FIXME - Wooow! app shouldn't explodeee if no inet - boris -
			// 22/08/2012
			task.execute(email.getText().toString(), password.getText().toString());
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

	private class LoginTask extends AsyncTask<String, Void, LoginResponse> {

		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(LoginActivity.this, "",
					getString(R.string.login_authenticating), true);
		}

		@Override
		protected LoginResponse doInBackground(final String... arg0) {
			try {
				final Login login = Login.newInstance(LoginActivity.this, arg0[0], arg0[1]);
				final LoginResponse loginResponse = login.execute();
				LogUtils.d(LoginActivity.this, "Login response", loginResponse);
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
			if (result.isValid() && result.isSuccessful()) {
				finishSuccessfulLogin(result.getUser(), progressDialog);
			} else if (result.isValid())
				showDialog(DIALOG_INCORRECT_LOGIN);
			else
				showDialog(DIALOG_ERROR);

			// TODO - what happens when no inet is given? check this out NOOOW -
			// boris - 13/09/2012
			// final UserBuilder userBuilder = UserBuilder.newInstance();
			// userBuilder.setId("asdfsdf");
			// userBuilder.setUserName("Mikeys");
			// userBuilder.setUserSurname("O");
			// userBuilder.setEmail("omikeys@ohoh.com");
			// userBuilder.setHeadline("Groso de la vida");
			// final User user = userBuilder.build();
			//
			// finishSuccessfulLogin(user, progressDialog);
		}

	}

	private class SaveDeviceIdTask extends AsyncTask<String, Void, SaveDeviceIdResponse> {

		@Override
		protected SaveDeviceIdResponse doInBackground(final String... params) {
			final String userId = params[0];

			try {
				final TalentRadarApplication talentRadarApplication = getTalentRadarApplication();
				if (!talentRadarApplication.isDeviceRegistered()) {
					talentRadarApplication.registerDevice();
				}
				final String deviceId = talentRadarApplication.getDeviceRegistrationId();
				final SaveDeviceId saveDeviceId = SaveDeviceId.newInstance(LoginActivity.this, userId,
						deviceId);
				SaveDeviceIdResponse saveDeviceIdResponse;
				saveDeviceIdResponse = saveDeviceId.execute();
				LogUtils.d(LoginActivity.this, "GCM Registration response", saveDeviceIdResponse);
				return saveDeviceIdResponse;
			} catch (final JSONException e) {
				Log.e("SaveDeviceIdTask", "Error parsing JSON response", e);
				throw new RuntimeException(e);
			} catch (final IOException e) {
				Log.e("SaveDeviceIdTask", "IO error trying to register device", e);
				throw new RuntimeException(e);
			}
		}

	}

}