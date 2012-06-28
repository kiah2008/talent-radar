package com.menatwork;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.menatwork.register.ChooseTypeActivity;
import com.menatwork.utils.GonzaUtils;
import com.menatwork.utils.LogUtils;
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
			progressDialog = ProgressDialog
					.show(LoginActivity.this, "", LoginActivity.this
							.getString(R.string.login_authenticating), true);
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			try {
				String email = arg0[0];
				String password = arg0[1];
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost loginPost = this.buildLoginPost(email, password);
				LogUtils.d(this, "Login POST", loginPost);
				HttpResponse response;
				response = httpClient.execute(loginPost);
				this.handleResponse(response);
				return true;
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		private HttpPost buildLoginPost(String email, String password) {
			LoginActivity context = LoginActivity.this;
			HttpPost loginPost = new HttpPost(
					context.getString(R.string.post_uri_login));
			List<NameValuePair> params = new ArrayList<NameValuePair>(2);

			params.add(new BasicNameValuePair(context
					.getString(R.string.post_key_login_email), email));
			params.add(new BasicNameValuePair(context
					.getString(R.string.post_key_login_password), password));

			setSafeEntity(loginPost, params);

			return loginPost;
		}

		private void setSafeEntity(HttpPost loginPost,
				List<NameValuePair> params) {
			try {
				loginPost.setEntity(new UrlEncodedFormEntity(params));
			} catch (UnsupportedEncodingException e) {
			}
		}

		private void handleResponse(HttpResponse response) {
			JSONObject jsonResponse;
			try {
				jsonResponse = GonzaUtils.readJSON(response.getEntity()
						.getContent());
				Log.d("LoginTask", jsonResponse.toString());
			} catch (IllegalStateException e) {
				// ignore 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			progressDialog.dismiss();
		}
	}
}