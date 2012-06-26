package com.menatwork.register;

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

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.menatwork.utils.LogUtils;
import com.mentatwork.R;

public class RegisterTask extends AsyncTask<Bundle, Integer, Integer> {

	private String registrationUri;
	private final AlertDialog alertDialog;
	private final Context context;

	public RegisterTask(Context context, AlertDialog alertDialog) {
		this.context = context;
		this.alertDialog = alertDialog;
		this.registrationUri = context
				.getString(R.string.post_uri_registration);
	}

	@Override
	protected Integer doInBackground(Bundle... params) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			Bundle bundleWithRegistrationData = params[0];
			HttpPost registrationPost = this
					.buildRegistrationPost(bundleWithRegistrationData);
			LogUtils.d(this, "Registration POST object", registrationPost);
			HttpResponse response = httpClient.execute(registrationPost);
			this.handleResponse(response);
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		alertDialog.dismiss();
	}

	private void handleResponse(HttpResponse response) {
		// TODO still dont know what the response looks like
		Log.d("RegisterTask", response.toString());
	}

	private HttpPost buildRegistrationPost(Bundle bundle) {
		HttpPost httpPost = new HttpPost(registrationUri);
		List<NameValuePair> params = new ArrayList<NameValuePair>(bundle.size());

		params.add(new BasicNameValuePair(context
				.getString(R.string.post_key_register_email), bundle
				.getString(RegistrationExtras.EMAIL)));
		params.add(new BasicNameValuePair(context
				.getString(R.string.post_key_register_username), bundle
				.getString(RegistrationExtras.NICKNAME)));
		params.add(new BasicNameValuePair(context
				.getString(R.string.post_key_register_password), bundle
				.getString(RegistrationExtras.PASSWORD)));
		params.add(new BasicNameValuePair(context
				.getString(R.string.post_key_register_name), bundle
				.getString(RegistrationExtras.REALNAME)));
		params.add(new BasicNameValuePair(context
				.getString(R.string.post_key_register_surname), bundle
				.getString(RegistrationExtras.REALNAME)));
		params.add(new BasicNameValuePair(context
				.getString(R.string.post_key_register_headline), bundle
				.getString(RegistrationExtras.HEADLINE)));

		this.safeSetEntity(httpPost, params);
		return httpPost;
	}

	private void safeSetEntity(HttpPost httpPost, List<NameValuePair> params) {
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// hence thy wizard sayeth: "thou shall not pass!"
		}
	}
}
