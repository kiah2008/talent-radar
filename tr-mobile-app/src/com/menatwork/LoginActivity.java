package com.menatwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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

import com.google.code.linkedinapi.client.oauth.LinkedInAccessToken;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthService;
import com.google.code.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.google.code.linkedinapi.client.oauth.LinkedInRequestToken;
import com.menatwork.register.ChooseTypeActivity;
import com.menatwork.utils.GonzaUtils;
import com.menatwork.utils.LogUtils;
import com.menatwork.utils.NaiveDialogClickListener;
import com.menatwork.utils.StartActivityListener;
import com.mentatwork.R;

public class LoginActivity extends Activity {
	public static final int DIALOG_INCORRECT_LOGIN = 1;
	public static final int DIALOG_ERROR = 2;
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
		getRegisterButton().setOnClickListener(
				new StartActivityListener(this, ChooseTypeActivity.class));
		getLinkedInButton().setOnClickListener(new LoginWithLinkedinListener());
		getLoginButton().setOnClickListener(new LoginButtonListener());
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Builder builder = new AlertDialog.Builder(this);
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

	private class LoginButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String email = getEmail().getText().toString();
			String password = getPassword().getText().toString();

			LoginTask task = new LoginTask();
			task.execute(email, password);
		}
	}

	private class LoginTask extends AsyncTask<String, Integer, Integer> {

		public static final int SUCCESS = 0;
		public static final int WRONG_ID = 1;
		public static final int ERROR = 2;
		private ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog
					.show(LoginActivity.this, "", LoginActivity.this
							.getString(R.string.login_authenticating), true);
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				String email = arg0[0];
				String password = arg0[1];
				HttpPost loginPost = this.buildLoginPost(email, password);
				LogUtils.d(this, "Login POST", loginPost);
				JSONObject response = GonzaUtils.executePost(loginPost);
				return this.handleResponse(response);
			} catch (JSONException e) {
				Log.e("LoginTask", "Error parsing JSON response", e);
				return ERROR;
			} catch (IOException e) {
				Log.e("LoginTask", "IO error trying to log in", e);
				return ERROR;
			}
		}

		private HttpPost buildLoginPost(String email, String password) {
			LoginActivity context = LoginActivity.this;
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);

			params.add(new BasicNameValuePair(context
					.getString(R.string.post_key_login_email), email));
			params.add(new BasicNameValuePair(context
					.getString(R.string.post_key_login_password), password));
			return GonzaUtils.buildPost(
					context.getString(R.string.post_uri_login), params);
		}

		private Integer handleResponse(JSONObject response) {
			try {
				Log.d("LoginTask", "JSON Response");
				Log.d("LoginTask", response.toString());
				/*
				 * TODO maybe wrap responses in objects that know how to deal
				 * with them: ResponseHandler.wrapLoginResponse(response);
				 */
				return !"error".equals(response.getJSONObject("result")
						.getString("status")) ? SUCCESS : WRONG_ID;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			progressDialog.dismiss();
			switch (result) {
			case LoginTask.SUCCESS:
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				break;
			case LoginTask.WRONG_ID:
				showDialog(DIALOG_INCORRECT_LOGIN);
				break;
			case LoginTask.ERROR:
				showDialog(DIALOG_ERROR);
				break;
			}
		}
	}

	// from here on, 'exploratory' code for logging in with LinkedIn! :)
	private LinkedInOAuthService oAuthService;
	private LinkedInRequestToken liToken;

	@Override
	protected void onNewIntent(Intent intent) {
		String verifier = intent.getData().getQueryParameter("oauth_verifier");
		LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(
				liToken, verifier);

		Log.d("Linking in", accessToken.getToken());
	}

	private class LoginWithLinkedinListener implements OnClickListener {

		private static final String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
		private static final String OAUTH_CALLBACK_HOST = "callback";
		private static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME
				+ "://" + OAUTH_CALLBACK_HOST;

		@Override
		public void onClick(View v) {
			String apiKey = LoginActivity.this
					.getString(R.string.linkedin_api_key);
			String apiSecret = LoginActivity.this
					.getString(R.string.linkedin_api_secret);
			oAuthService = LinkedInOAuthServiceFactory.getInstance()
					.createLinkedInOAuthService(apiKey, apiSecret);
			liToken = oAuthService.getOAuthRequestToken(OAUTH_CALLBACK_URL);
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(liToken
					.getAuthorizationUrl()));
			startActivity(i);
		}

	}
}