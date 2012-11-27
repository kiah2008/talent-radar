package com.menatwork.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class GonzaUtils {

	public static JSONObject executePost(final HttpPost httpPost)
			throws JSONException, IOException {
		try {
			// final DefaultHttpClient httpClient = new DefaultHttpClient();
			// final HttpResponse httpResponse = httpClient.execute(httpPost);
			// final HttpEntity httpEntity = httpResponse.getEntity();
			// return readJSON(httpEntity.getContent());

			// ************************************************ //
			// ====== Empieza prueba ======
			// ************************************************ //

			final DefaultHttpClient httpClient = new DefaultHttpClient();
			httpPost.setHeader("Accept", "application/json");
			final HttpResponse httpResponse = httpClient.execute(httpPost);
			final HttpEntity httpEntity = httpResponse.getEntity();
			return readJSON(httpEntity.getContent());

			// ************************************************ //
			// ====== Fin prueba ======
			// ************************************************ //

		} catch (final UnsupportedEncodingException e) {
			// should not happen
			throw new RuntimeException(e);
		} catch (final ClientProtocolException e) {
			// should not happen
			throw new RuntimeException(e);
		}
	}

	public static HttpPost buildPost(final String url,
			final List<NameValuePair> params) {
		try {
			final HttpPost httpPost = new HttpPost(url);

			params.add(new BasicNameValuePair("_method", "POST"));

			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			return httpPost;
		} catch (final UnsupportedEncodingException e) {
			// should not happen
			throw new RuntimeException(e);
		}
	}

	private static JSONObject readJSON(final InputStream content)
			throws JSONException, IOException {
		final String json = readContents(content);
		return new JSONObject(json);
	}

	private static String readContents(final InputStream content)
			throws IOException {
		try {
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(content, "UTF-8"), 8);
			final StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				stringBuilder.append(line + "\n");
			return stringBuilder.toString();
		} finally {
			try {
				content.close();
			} catch (final IOException e) {
			}
		}
	}
}
