package com.menatwork;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * This activity should show up whenever the license of the app has expired.
 *
 * @author miguel
 *
 */
public class ExpiredActivity extends GuiTalentRadarActivity {

	private TextView siteUrl;

	@Override
	protected int getViewLayoutId() {
		return R.layout.expired;
	}

	@Override
	protected void findViewElements() {
		siteUrl = findTextViewById(R.id.expired_site_url);
	}

	@Override
	protected void setupButtons() {
		siteUrl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				goToMainSite();
			}

		});
	}

	protected void goToMainSite() {
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(getString(R.string.talent_radar_site_url)));
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}
}
