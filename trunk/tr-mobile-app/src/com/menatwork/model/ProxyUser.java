package com.menatwork.model;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.util.Log;

import com.menatwork.TalentRadarApplication;
import com.menatwork.service.GetUser;
import com.menatwork.service.response.GetUserResponse;

public class ProxyUser implements User {

	private final String id;

	private User realUser;

	// ************************************************ //
	// ====== Creation methods ======
	// ************************************************ //

	public static ProxyUser withId(final String id) {
		return new ProxyUser(id);
	}

	protected ProxyUser(final String id) {
		this.id = id;
	}

	// ************************************************ //
	// ====== Instance methods ======
	// ************************************************ //

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<JobPosition> getJobPositions() {
		return realUser.getJobPositions();
	}

	@Override
	public String getDisplayableLongName() {
		loadRealUserIfNeeded();
		return realUser.getDisplayableLongName();
	}

	@Override
	public String getHeadline() {
		loadRealUserIfNeeded();
		return realUser.getHeadline();
	}

	@Override
	public List<String> getSkills() {
		loadRealUserIfNeeded();
		return realUser.getSkills();
	}

	@Override
	public void setSkills(final List<String> skills) {
		loadRealUserIfNeeded();
		realUser.setSkills(skills);
	}

	@Override
	public String getEmail() {
		loadRealUserIfNeeded();
		return realUser.getEmail();
	}

	@Override
	public String getName() {
		loadRealUserIfNeeded();
		return realUser.getName();
	}

	@Override
	public String getSurname() {
		loadRealUserIfNeeded();
		return realUser.getSurname();
	}

	@Override
	public String getNickname() {
		loadRealUserIfNeeded();
		return realUser.getNickname();
	}

	@Override
	public String getProfilePictureUrl() {
		loadRealUserIfNeeded();
		return realUser.getProfilePictureUrl();
	}

	@Override
	public String forceGetRealName() {
		loadRealUserIfNeeded();
		return realUser.forceGetRealName();
	}

	@Override
	public PrivacySettings getPrivacySettings() {
		loadRealUserIfNeeded();
		return realUser.getPrivacySettings();
	}

	@Override
	public boolean hasSkill(final String requiredSkill) {
		loadRealUserIfNeeded();
		return realUser.hasSkill(requiredSkill);
	}

	// ************************************************ //
	// ====== Proxy stuff ======
	// ************************************************ //

	private void loadRealUserIfNeeded() {
		if (realUser == null)
			loadRealUser();
	}

	private void loadRealUser() {
		try {
			// TODO - this solution should be reconsidered, maybe persisting
			// the user as well as the hunt (or the necessary fields) or
			// having a service which collects all users from a list of user
			// ids - miguel - 05/11/2012
			final GetUser getUserService = GetUser.newInstance(
					getTalentRadarApplication(), id, getLocalUserId());
			final GetUserResponse response = getUserService.execute();

			realUser = response.getUser();

		} catch (final JSONException e) {
			handleGetUserServiceException(e);
		} catch (final IOException e) {
			handleGetUserServiceException(e);
		}
	}

	private void handleGetUserServiceException(final Exception e) {
		Log.w(getClass().getSimpleName(), //
				"user with id='" + id + "' could not be fetched from server");
		e.printStackTrace();
	}

	// ************************************************ //
	// ====== Talent Radar commons ======
	// ************************************************ //

	private String getLocalUserId() {
		return getTalentRadarApplication().getLocalUserId();
	}

	private TalentRadarApplication getTalentRadarApplication() {
		return TalentRadarApplication.getContext();
	}

}
