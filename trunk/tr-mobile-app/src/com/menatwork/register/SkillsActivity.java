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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.menatwork.MainActivity;
import com.menatwork.R;
import com.menatwork.service.Response;
import com.menatwork.service.ServiceCall;
import com.menatwork.utils.GonzaUtils;
import com.menatwork.utils.LogUtils;
import com.menatwork.utils.NaiveDialogClickListener;

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
		finishButton.setOnClickListener(new NextButtonListener());
		cancelButton.setOnClickListener(new CancelButtonListener(this));
	}

	@Override
	Bundle getConfiguredData() {
		Bundle bundle = new Bundle();
		bundle.putString(RegistrationExtras.HEADLINE, headline.getText()
				.toString());
		return bundle;
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

	private class NextButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Bundle bundle = getIntent().getExtras();
			bundle.putAll(getConfiguredData());
			new RegisterTask().execute(bundle);
		}
	}

	private class RegisterTask extends AsyncTask<Bundle, Void, Integer> {

		private ProgressDialog progressDialog;
		public static final int SUCCESS = 0;
		public static final int FAILURE = 1;

		// public static final int REPEATED = 2;
		// public static final int MISSING_FIELDS = 3;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(SkillsActivity.this,
					getString(R.string.register_finaldialog_title),
					getString(R.string.register_finaldialog_message), true);
		}

		@Override
		protected Integer doInBackground(Bundle... params) {
			try {
				List<NameValuePair> postParams = buildParams(params[0]);
				HttpPost httpPost = GonzaUtils.buildPost(
						getString(R.string.post_uri_registration), postParams);
				LogUtils.d(this, "Registration POST object", httpPost);
				JSONObject response = GonzaUtils.executePost(httpPost);
				return this.handleResponse(ServiceCall.REGISTER.wrap(response));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return FAILURE;
		}

		private List<NameValuePair> buildParams(
				Bundle bundleWithRegistrationData) {
			List<NameValuePair> params = new ArrayList<NameValuePair>(
					bundleWithRegistrationData.size());

			params.add(new BasicNameValuePair(
					getString(R.string.post_key_register_email),
					bundleWithRegistrationData
							.getString(RegistrationExtras.EMAIL)));
			params.add(new BasicNameValuePair(
					getString(R.string.post_key_register_username),
					bundleWithRegistrationData
							.getString(RegistrationExtras.NICKNAME)));
			params.add(new BasicNameValuePair(
					getString(R.string.post_key_register_password),
					bundleWithRegistrationData
							.getString(RegistrationExtras.PASSWORD)));
			params.add(new BasicNameValuePair(
					getString(R.string.post_key_register_name),
					bundleWithRegistrationData
							.getString(RegistrationExtras.REALNAME)));
			params.add(new BasicNameValuePair(
					getString(R.string.post_key_register_surname),
					bundleWithRegistrationData
							.getString(RegistrationExtras.REALNAME)));
			params.add(new BasicNameValuePair(
					getString(R.string.post_key_register_headline),
					bundleWithRegistrationData
							.getString(RegistrationExtras.HEADLINE)));
			return params;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			if (result == SUCCESS) {
				startActivity(new Intent(SkillsActivity.this,
						MainActivity.class));
			} else {
				Bundle bundleWithMessage = new Bundle();
				String message; // should be message defined by service
				message = getString(R.string.register_errordialog_message);
				bundleWithMessage.putString("message", message);
				showDialog(DIALOG_ERROR, bundleWithMessage);
			}
		}

		private int handleResponse(Response response) {
			Log.d("RegisterTask", "JSON Response");
			Log.d("RegisterTask", response.toString());
			return response.isSuccessful() ? SUCCESS : FAILURE;
		}

	}

}
