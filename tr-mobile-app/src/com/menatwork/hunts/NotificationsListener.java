package com.menatwork.hunts;

import java.util.Date;
import java.util.List;

import android.content.Intent;

import com.menatwork.HuntMiniProfilesActivity;
import com.menatwork.MainActivity;
import com.menatwork.R;
import com.menatwork.TalentRadarApplication;
import com.menatwork.model.User;
import com.menatwork.notification.TrNotificationBuilder;
import com.menatwork.notification.TrNotificationType;

public class NotificationsListener implements HuntingCriteriaListener {

	private final TalentRadarApplication talentRadarApplication;

	public NotificationsListener(
			final TalentRadarApplication talentRadarApplication) {
		this.talentRadarApplication = talentRadarApplication;
	}

	@Override
	public void onUsersAddedToHunt(final Hunt hunt, final List<User> newUsers) {
		String message;
		if (newUsers.size() == 1)
			message = String.format(talentRadarApplication
					.getString(R.string.hunt_notification_single_user), hunt
					.getTitle().trim(), newUsers.get(0)
					.getDisplayableLongName());
		else
			message = String.format(talentRadarApplication
					.getString(R.string.hunt_notification_multiple_users), hunt
					.getTitle().trim(), newUsers.size());

		final Intent notificationIntent = new Intent(talentRadarApplication,
				MainActivity.class);

		// android notification
		TalentRadarApplication.generateAndroidNotification(
				talentRadarApplication, hunt.hashCode(), message,
				notificationIntent);

		// talent radar notification
		final TrNotificationBuilder builder = TrNotificationBuilder
				.newInstance();
		builder.setDate(new Date());
		builder.setDescription(message);
		builder.setHeader("Nuevo usuario capturado");
		builder.setNotificationId(hunt.hashCode() + newUsers.get(0).getId());
		builder.setType(TrNotificationType.ADDED_TO_SEARCH);
		final Intent dashboardIntent = new Intent(talentRadarApplication,
				HuntMiniProfilesActivity.class);
		dashboardIntent.putExtra(HuntMiniProfilesActivity.EXTRAS_HUNT_ID, hunt.getId());
		builder.setIntent(dashboardIntent);
		talentRadarApplication.getNotificationManager().newNotification(
				builder.build());
	}
}
