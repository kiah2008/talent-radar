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

	private final Activity activity;

	private ProgressDialog progressDialog;
	private String toUsername;

	public PingTask(final Activity activity) {
		this.activity = activity;
	}

	// private boolean userConfirmsAction() {
	// return UserConfirmationPrompter.prompt(activity, "Ping",
	// String.format("Pingear a %s?", toUsername));
	// }

	@Override
	protected void onPreExecute() {
		// if (!this.userConfirmsAction()) {
		// this.cancel(true);
		// return;
		// }
		progressDialog = ProgressDialog.show(activity, "",
				activity.getString(R.string.generic_wait));
	}

	@Override
	protected void onPostExecute(final Response result) {
		progressDialog.dismiss();
		if (result.isSuccessful())
			Toast.makeText(
					activity,
					String.format(
							activity.getString(R.string.pinged_successfully),
							toUsername), Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(activity,
					activity.getString(R.string.generic_error),
					Toast.LENGTH_SHORT).show();
	}

	@Override
	protected Response doInBackground(final String... params) {
		try {
			final String localUserId = params[0];
			final String toId = params[1];

			toUsername = params[2];

			final Ping ping = Ping.newInstance(activity, localUserId, toId,
					TalentRadarApplication.getContext().getPreferences()
							.getPingMessage());
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