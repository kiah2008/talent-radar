package com.menatwork;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.menatwork.model.User;
import com.menatwork.utils.LogUtils;
import com.menatwork.utils.NaiveDialogClickListener;

public class LoginActivity extends AbstractLoginActivity {
	public static final int DIALOG_INCORRECT_LOGIN = 1;
	public static final int DIALOG_ERROR = 2;

	private ImageButton linkedInButton;

	@Override
	protected int getViewLayoutId() {
		return R.layout.login;
	}

	@Override
	protected void findViewElements() {
		linkedInButton = findImageButtonById(R.id.login_button_linkedin);
	}

	@Override
	protected void setupButtons() {
		linkedInButton.setOnClickListener(new LoginWithLinkedinListener());
	}

	@Override
	protected void onNewIntent(final Intent intent) {
		// handle logging in with linked in
		Log.d("LoginActivity", "New intent: " + intent.getDataString());
		final Uri dataUri = intent.getData();
		if (dataUri == null) {
			// nothing?
		} else if ("talent".equals(dataUri.getScheme())) {
			if ("notification".equals(dataUri.getSchemeSpecificPart()))
				handleTestNotification();
		} else if ("talent.call.linkedin.back".equals(dataUri.getScheme()))
			handleLoginWithLinkedin(intent);
	}

	private void handleTestNotification() {
		Toast.makeText(this, "Te abri� la app VITEH\'", Toast.LENGTH_LONG)
				.show();
	}

	private void handleLoginWithLinkedin(final Intent intent) {
		LogUtils.d(this, "Returning from Login with Linkedin service", intent);
		final Uri data = intent.getData();
		final String userid = data.getPathSegments().get(0);
		if (userid == null) {
			showDialog(DIALOG_ERROR);
			return;
		}
		if ("error=user_refused".equals(userid))
			return;
		if (userid.startsWith("error=")) {
			showDialog(DIALOG_ERROR);
			return;
		}
		final ProgressDialog progressDialog = ProgressDialog.show(this, "",
				getString(R.string.login_authenticating), true);
		Log.d("LoginActivity", "Returning id from Login with Linkedin service");
		Log.d("LoginActivity", userid);
		final User user = getUserById(userid, userid);
		this.finishSuccessfulLogin(LoginActivity.this, user, progressDialog);
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

	// ************************************************ //
	// ====== login innards ======
	// ************************************************ //

	private class LoginWithLinkedinListener implements OnClickListener {

		@Override
		public void onClick(final View v) {
			final Intent browserIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.uri_login_with_linkedin)));
			browserIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivityForResult(browserIntent, LINKED_IN_REQUEST_CODE);
		}

	}

	@Override
	public void onConfigurationChanged(final Configuration newConfig) {
		// newConfig.orientation = Configuration.ORIENTATION_PORTRAIT;
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	}
}