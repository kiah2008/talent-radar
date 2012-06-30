package com.menatwork.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.menatwork.LoginActivity;
import com.menatwork.MainActivity;
import com.menatwork.utils.GonzaUtils;
import com.menatwork.utils.LogUtils;
import com.menatwork.utils.StartActivityOnClickListener;
import com.mentatwork.R;

public class SkillsActivity extends DataInputActivity {

	private Button finishButton;
	private Button cancelButton;
	private EditText headline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_skills);
		findViewElements();
		setupButtons();
	}

	private void findViewElements() {
		finishButton = (Button) findViewById(R.id.register_skills_button_finish);
		cancelButton = (Button) findViewById(R.id.register_skills_button_cancel);
		headline = (EditText) findViewById(R.id.register_skills_headline);
	}

	private void setupButtons() {
		finishButton.setOnClickListener(this.getNextButtonClickListener());
		cancelButton.setOnClickListener(new CancelButtonListener(this));
	}

	@Override
	Bundle getConfiguredData() {
		Bundle bundle = new Bundle();
		bundle.putString(RegistrationExtras.HEADLINE, getControlHeadline()
				.getText().toString());
		return bundle;
	}

	private EditText getControlHeadline() {
		return headline;
	}

	private StartActivityOnClickListener getNextButtonClickListener() {
		return new StartActivityOnClickListener(this, MainActivity.class) {

			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
				bundle.putAll(getConfiguredData());
				RegisterTask registerTask = new RegisterTask();
				registerTask.execute(bundle);
				try {
					if (registerTask.get())
						super.onClick(v);
					else {
						// show dialog and return to main screen
						// showDialog()
						Intent intent = new Intent(SkillsActivity.this,
								LoginActivity.class);
						startActivity(intent);
						return;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	private class RegisterTask extends AsyncTask<Bundle, Integer, Boolean> {

		private ProgressDialog progressDialog;
		private final Context context;

		public RegisterTask() {
			this.context = SkillsActivity.this;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context,
					context.getString(R.string.register_finaldialog_title),
					context.getString(R.string.register_finaldialog_message),
					true);
		}

		@Override
		protected Boolean doInBackground(Bundle... params) {
			try {
				List<NameValuePair> postParams = buildParams(params[0]);
				HttpPost httpPost = GonzaUtils.buildPost(
						this.context.getString(R.string.post_uri_registration),
						postParams);
				LogUtils.d(this, "Registration POST object", httpPost);
				JSONObject response = GonzaUtils.executePost(httpPost);
				return this.handleResponse(response);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

		private List<NameValuePair> buildParams(
				Bundle bundleWithRegistrationData) {
			List<NameValuePair> params = new ArrayList<NameValuePair>(
					bundleWithRegistrationData.size());

			params.add(new BasicNameValuePair(this.context
					.getString(R.string.post_key_register_email),
					bundleWithRegistrationData
							.getString(RegistrationExtras.EMAIL)));
			params.add(new BasicNameValuePair(this.context
					.getString(R.string.post_key_register_username),
					bundleWithRegistrationData
							.getString(RegistrationExtras.NICKNAME)));
			params.add(new BasicNameValuePair(this.context
					.getString(R.string.post_key_register_password),
					bundleWithRegistrationData
							.getString(RegistrationExtras.PASSWORD)));
			params.add(new BasicNameValuePair(this.context
					.getString(R.string.post_key_register_name),
					bundleWithRegistrationData
							.getString(RegistrationExtras.REALNAME)));
			params.add(new BasicNameValuePair(this.context
					.getString(R.string.post_key_register_surname),
					bundleWithRegistrationData
							.getString(RegistrationExtras.REALNAME)));
			params.add(new BasicNameValuePair(this.context
					.getString(R.string.post_key_register_headline),
					bundleWithRegistrationData
							.getString(RegistrationExtras.HEADLINE)));
			return params;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
		}

		private Boolean handleResponse(JSONObject response)
				throws JSONException {
			Log.d("RegisterTask", "JSON Response");
			Log.d("RegisterTask", response.toString());
			String resultStatus = response.getJSONObject("result").getString(
					"status");
			return "ok".equals(resultStatus);
		}

	}

}
