package com.menatwork.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.http.client.methods.HttpPost;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.menatwork.service.response.Response;

public class LogUtils {

	private static final String NULL = "<null value>";

	public static <T> void d(T caller, String message, HttpPost postObject) {
		Log.d(caller.getClass().getSimpleName(), message);
		Log.d(caller.getClass().getSimpleName(), postObject.getURI().toString());

		ByteArrayOutputStream entityDescription = new ByteArrayOutputStream();
		try {
			postObject.getEntity().writeTo(entityDescription);
		} catch (IOException e) {
			// do nothing
		}
		Log.d(caller.getClass().getSimpleName(), entityDescription.toString());
	}

	public static <T> void d(T caller, String message, Bundle bundle) {
		Log.d(caller.getClass().getSimpleName(), message);
		Set<String> keys = bundle.keySet();
		for (String key : keys) {
			Log.d(caller.getClass().getSimpleName(),
					key + " = " + bundle.get(key));
		}
	}

	public static <T> void d(T caller, String message, JSONObject jsonObject) {
		Log.d(caller.getClass().getSimpleName(), message);
		Log.d(caller.getClass().getSimpleName(), jsonObject == null ? NULL
				: jsonObject.toString());
	}

	public static <T> void d(T caller, String message, Response response) {
		Log.d(caller.getClass().getSimpleName(), message);
		Log.d(caller.getClass().getSimpleName(), response.toString());
	}

	public static <T> void d(T caller, String message, Intent intent) {
		Log.d(caller.getClass().getSimpleName(), message);
		Log.d(caller.getClass().getSimpleName(), intent.getDataString());
	}

}
