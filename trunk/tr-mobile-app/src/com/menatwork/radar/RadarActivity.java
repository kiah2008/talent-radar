package com.menatwork.radar;

import android.os.Bundle;

import com.menatwork.R;
import com.menatwork.TalentRadarActivity;

public class RadarActivity extends TalentRadarActivity {

	@Override
	protected int getViewLayoutId() {
		return R.layout.radar;
	}

	@Override
	protected void setupButtons() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void findViewElements() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void postCreate(final Bundle savedInstanceState) {
		// bindService(new Intent(this, RadarService.class), conn, flags);
	}

}
