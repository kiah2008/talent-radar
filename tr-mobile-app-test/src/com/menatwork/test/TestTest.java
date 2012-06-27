package com.menatwork.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import android.test.AndroidTestCase;

public class TestTest extends AndroidTestCase {

	public void testServicio() throws Exception {
		final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("data[User][email]", "miguel"));
		parameters.add(new BasicNameValuePair("data[User][password]",
				"password"));

		final HttpPost request = new HttpPost(
				"http://localhost/tr-service/users/app_login");

		request.setEntity(new UrlEncodedFormEntity(parameters));

		BufferedReader in = null;
		try {
			final HttpClient client = new DefaultHttpClient();

			final HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));

			final StringBuffer sb = new StringBuffer("");
			String line = "";

			while ((line = in.readLine()) != null)
				sb.append(line);

			final String resp = sb.toString();

			System.out.println(resp);

		} catch (final ClientProtocolException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
		}
	}

}
