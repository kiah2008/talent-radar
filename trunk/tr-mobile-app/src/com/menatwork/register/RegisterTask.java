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

import android.os.AsyncTask;
import android.os.Bundle;

public class RegisterTask extends AsyncTask<Bundle, Integer, Integer> {

	private static final String REGISTRATION_URI = "http://localhost/tr-service/users/app_register";

	@Override
	protected Integer doInBackground(Bundle... params) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(this
					.buildRequestUriString(params[0]));
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

	private HttpPost buildRequestUriString(Bundle bundle) {
		HttpPost httpPost = new HttpPost(REGISTRATION_URI);
		List<NameValuePair> params = new ArrayList<NameValuePair>(bundle.size());

		params.add(new BasicNameValuePair("data[User][email]", bundle
				.getString(RegistrationExtras.EMAIL)));
		params.add(new BasicNameValuePair("data[User][username]", bundle
				.getString(RegistrationExtras.NICKNAME)));
		params.add(new BasicNameValuePair("data[User][password]", bundle
				.getString(RegistrationExtras.PASSWORD)));
		params.add(new BasicNameValuePair("data[User][name]", bundle
				.getString(RegistrationExtras.REALNAME)));
		params.add(new BasicNameValuePair("data[User][surname]", bundle
				.getString(RegistrationExtras.PUBLIC_REALNAME)));
		params.add(new BasicNameValuePair("data[User][headline]", bundle
				.getString(RegistrationExtras.HEADLINE)));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// hence thy wizard sayeth: "thou shall not pass!"
		}
		return httpPost;
	}
}
