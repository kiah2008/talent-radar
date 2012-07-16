package com.menatwork.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.menatwork.utils.GonzaUtils;

public abstract class ServiceCall<T extends Response> {

	protected final Context context;

	public ServiceCall(Context context) {
		super();
		this.context = context;
	}

	protected String getString(int resId) {
		return context.getString(resId);
	}

	public T execute() throws JSONException, IOException {
		HttpPost httpPost = GonzaUtils.buildPost(this.getMethodUri(),
				this.buildPostParametersList());
		JSONObject response = GonzaUtils.executePost(httpPost);
		return this.wrap(response);
	}

	protected abstract List<NameValuePair> buildPostParametersList();

	protected abstract T wrap(JSONObject response);

	protected abstract String getMethodUri();

}
