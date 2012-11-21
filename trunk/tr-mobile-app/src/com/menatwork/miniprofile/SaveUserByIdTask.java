package com.menatwork.miniprofile;

import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import android.widget.Toast;

import com.menatwork.R;
import com.menatwork.model.User;
import com.menatwork.service.GetUser;
import com.menatwork.service.response.GetUserResponse;
import com.menatwork.utils.SaveContactBaseTask;

public class SaveUserByIdTask extends SaveContactBaseTask<String> {

	public SaveUserByIdTask(final Context context) {
		super(context);
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

			if (response.isSuccessful()) {
				final User user = response.getUser();
				saveContact(user);
			} else
				handler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(context,
								R.string.save_contact_by_id_error,
								Toast.LENGTH_SHORT).show();
					}
				});

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
