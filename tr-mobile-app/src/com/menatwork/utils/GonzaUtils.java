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
import org.json.JSONException;
import org.json.JSONObject;

public class GonzaUtils {

	public static JSONObject executePost(HttpPost httpPost)
			throws JSONException, IOException {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			return readJSON(httpEntity.getContent());
		} catch (UnsupportedEncodingException e) {
			// should not happen
			throw new RuntimeException(e);
		} catch (ClientProtocolException e) {
			// should not happen
			throw new RuntimeException(e);
		}
	}

	public static HttpPost buildPost(String url, List<NameValuePair> params) {
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			return httpPost;
		} catch (UnsupportedEncodingException e) {
			// should not happen
			throw new RuntimeException(e);
		}
	}

	private static JSONObject readJSON(InputStream content)
			throws JSONException, IOException {
		String json = readContents(content);
		return new JSONObject(json);
	}

	private static String readContents(InputStream content) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content), 8);
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "n");
			}
			return stringBuilder.toString();
		} finally {
			try {
				content.close();
			} catch (IOException e) {
			}
		}
	}
}
