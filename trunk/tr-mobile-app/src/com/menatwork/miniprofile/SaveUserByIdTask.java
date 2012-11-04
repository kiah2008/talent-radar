package com.menatwork.miniprofile;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

import com.menatwork.hunts.DefaultHunt;
import com.menatwork.service.GetUser;
import com.menatwork.service.response.GetUserResponse;

public class SaveUserByIdTask extends AsyncTask<String, Void, Void> {

	private final Context context;

	public SaveUserByIdTask(final Context context) {
		this.context = context;
	}

	// TODO - could add some progress until is has been saved - miguel -
	// 04/11/2012

	@Override
	protected Void doInBackground(final String... params) {
		try {
			final String localUserId = params[0];
			final String userToBeAddedId = params[1];

			final GetUser getUser = GetUser.newInstance(context,
					userToBeAddedId, localUserId);
			final GetUserResponse response = getUser.execute();
			DefaultHunt.getInstance().addUser(response.getUser());

		} catch (final JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return something for the sake of Java
		return null;
	}

}
