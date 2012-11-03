package com.menatwork;

import java.io.IOException;

import org.json.JSONException;

import android.os.AsyncTask;
import android.util.Log;

import com.menatwork.service.GetSkills;
import com.menatwork.service.response.SkillsResponse;

class GetSkillsTask extends AsyncTask<Void, Void, SkillsResponse> {

	private final TalentRadarApplication trApplication;

	GetSkillsTask(final TalentRadarApplication trApplication) {
		super();
		this.trApplication = trApplication;
	}

	@Override
	protected SkillsResponse doInBackground(final Void... arg0) {
		try {
			final GetSkills getSkills = GetSkills
					.newInstance(trApplication);
			SkillsResponse skillsResponse;
			skillsResponse = getSkills.execute();
			return skillsResponse;
		} catch (final JSONException e) {
			Log.e("GetSkillsTask", "Error parsing JSON response", e);
			throw new RuntimeException(e);
		} catch (final IOException e) {
			Log.e("GetSkillsTask", "IO error trying to get system skills",
					e);
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void onPostExecute(final SkillsResponse result) {
		if (result.isSuccessful())
			trApplication.loadSkills(result.getSkills());
	}
}