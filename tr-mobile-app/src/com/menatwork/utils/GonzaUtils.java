package com.menatwork.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

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

	public static InputStream httpPost(String url,
			ArrayList<NameValuePair> params) {

		InputStream resultContent = null;

		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			resultContent = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultContent;
	}

	public static JSONObject readJSON(InputStream content)
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
