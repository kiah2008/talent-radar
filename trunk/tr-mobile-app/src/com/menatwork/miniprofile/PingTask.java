package com.menatwork.miniprofile;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.menatwork.R;
import com.menatwork.TalentRadarApplication;
import com.menatwork.service.Ping;
import com.menatwork.service.response.ErroneousResponse;
import com.menatwork.service.response.Response;

public class PingTask extends AsyncTask<String, Void, Response> {

	private ProgressDialog progressDialog;

	private final Activity activity;

	public PingTask(final Activity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, "", activity.getString(R.string.generic_wait));
	}

	@Override
	protected void onPostExecute(final Response result) {
		progressDialog.dismiss();
		if (!result.isSuccessful()) {
			Toast.makeText(activity, activity.getString(R.string.generic_error), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected Response doInBackground(final String... params) {
		try {
			final String localUserId = params[0];
			final String toId = params[1];

			final Ping ping = Ping.newInstance(activity, localUserId, toId, TalentRadarApplication
					.getContext().getPreferences().getPingMessage());
			return ping.execute();
		} catch (final JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ErroneousResponse.INSTANCE;
	}

}