package com.menatwork;

import android.app.Activity;
import android.app.Application;

import com.menatwork.model.User;
import com.menatwork.service.GetUser;
import com.menatwork.service.GetUserSkills;
import com.menatwork.service.response.GetUserResponse;
import com.menatwork.service.response.GetUserSkillsResponse;

/**
 * Common Activity superclass for all our activities.
 *
 * @author miguel
 *
 */
public class TalentRadarActivity extends Activity {

	/**
	 * {@link TalentRadarApplication#isRunningOnEmulator()}
	 */
	public boolean isRunningOnEmulator() {
		return getTalentRadarApplication().isRunningOnEmulator();
	}

	/**
	 * Retrieves the {@link Application} object casted to a
	 * {@link TalentRadarApplication}
	 *
	 * @return {@link TalentRadarApplication} for the app
	 */
	public TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) getApplication();
	}

	/* ****************************************** */
	/* ********* Business' commons ************** */
	/* ****************************************** */

	protected User getLocalUser() {
		return getTalentRadarApplication().getLocalUser();
	}

	protected User getUserById(final String userid) {
		try {
			final GetUser getUser = GetUser.newInstance(this, userid);
			final GetUserResponse response = getUser.execute();
			final GetUserSkills getUserSkills = GetUserSkills.newInstance(this,
					userid);
			final User user = response.getUser();
			final GetUserSkillsResponse userSkillsResponse = getUserSkills
					.execute();
			user.setSkills(userSkillsResponse.getSkills());
			return user;
		} catch (final Exception e) {
			// TODO get this right please
			e.printStackTrace();
		}
		return null;
	}
}
