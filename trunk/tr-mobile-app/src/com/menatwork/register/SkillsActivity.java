package com.menatwork.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.menatwork.MainActivity;
import com.menatwork.utils.GonzaUtils;
import com.menatwork.utils.LogUtils;
import com.menatwork.utils.NaiveDialogClickListener;
import com.menatwork.utils.StartActivityListener;
import com.mentatwork.R;

public class SkillsActivity extends DataInputActivity {

	public static final int DIALOG_ERROR = 0;
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

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Builder builder = new AlertDialog.Builder(this);
		switch (id) {
		case DIALOG_ERROR:
			builder.setMessage(args.getString("message"));
			builder.setPositiveButton("OK", new NaiveDialogClickListener());
			return builder.create();
		}
		return null;
	}

	private StartActivityListener getNextButtonClickListener() {
		return new StartActivityListener(this, MainActivity.class) {

			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
				bundle.putAll(getConfiguredData());
				new RegisterTask().execute(bundle);
			}
		};
	}

	private class RegisterTask extends AsyncTask<Bundle, Void, JSONObject> {

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
		protected JSONObject doInBackground(Bundle... params) {
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
			return null;
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
		protected void onPostExecute(JSONObject response) {
			try {
				super.onPostExecute(response);
				progressDialog.dismiss();
				String resultStatus;
				resultStatus = response.getJSONObject("result").getString(
						"status");
				if ("ok".equals(resultStatus)) {
					Intent intent = new Intent(SkillsActivity.this,
							MainActivity.class);
					startActivity(intent);
				} else {
					Bundle bundleWithMessage = new Bundle();
					String message; // should be message defined by service
					message = getString(R.string.register_errordialog_message);
					bundleWithMessage.putString("message", message);
					showDialog(DIALOG_ERROR, bundleWithMessage);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		private JSONObject handleResponse(JSONObject response) {
			Log.d("RegisterTask", "JSON Response");
			Log.d("RegisterTask", response.toString());
			return response;
		}

	}

}
