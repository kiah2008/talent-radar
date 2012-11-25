package com.menatwork.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.menatwork.R;
import com.menatwork.service.response.Response;
import com.menatwork.utils.GonzaUtils;
import com.menatwork.utils.LogUtils;

public abstract class ServiceCall<T extends Response> {

	protected final Context context;

	public ServiceCall(final Context context) {
		super();
		this.context = context;
	}

	protected String getString(final int resId) {
		return context.getString(resId);
	}

	public T execute() throws JSONException, IOException {
		final String uri = getString(R.string.post_uri) + getMethodUri();

		final HttpPost httpPost = GonzaUtils.buildPost(uri, this.buildPostParametersList());
		LogUtils.d(this, "About to execute POST:", httpPost);
		final JSONObject response = GonzaUtils.executePost(httpPost);
		LogUtils.d(this, "Received response:", response);
		return this.wrap(response);
	}

	protected abstract List<NameValuePair> buildPostParametersList();

	protected abstract T wrap(JSONObject response);

	protected abstract String getMethodUri();

}
