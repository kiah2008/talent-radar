package com.menatwork;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.menatwork.model.User;
import com.menatwork.service.GetUser;
import com.menatwork.service.GetUserSkills;
import com.menatwork.service.ResponseException;
import com.menatwork.service.response.GetUserResponse;
import com.menatwork.service.response.GetUserSkillsResponse;

public class GetUserTask extends AsyncTask<String, Void, User> {

	protected ProgressDialog progressDialog;
	protected final TalentRadarActivity activity;
	private boolean finished;

	public GetUserTask(final TalentRadarActivity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, "",
				activity.getString(R.string.generic_wait));
	}

	@Override
	protected void onPostExecute(final User result) {
		progressDialog.dismiss();
		finished = true;
	}

	@Override
	protected User doInBackground(final String... params) {
		try {
			final String userid = params[0];
			String requesterUserId;
			if (params.length > 1)
				requesterUserId = params[1];
			else
				requesterUserId = activity.getTalentRadarApplication()
						.getLocalUserId();

			// getting user profile
			final GetUser getUser = GetUser.newInstance(activity, userid,
					requesterUserId);
			final GetUserResponse response = getUser.execute();
			final User user = response.getUser();

			// getting skills
			try {
				final GetUserSkills getUserSkills = GetUserSkills.newInstance(
						activity, userid);
				final GetUserSkillsResponse userSkillsResponse = getUserSkills
						.execute();
				user.setSkills(userSkillsResponse.getSkills());
			} catch (final ResponseException e) {
				// TODO Auto-generated catch block
				// won't set skills, but will return user, is it a lot of
				// damage?
				e.printStackTrace();
			}

			return user;
		} catch (final Exception e) {
			// TODO get this right please
			e.printStackTrace();
			return null;
		}
	}

	public boolean finished() {
		return finished;
	}

}
