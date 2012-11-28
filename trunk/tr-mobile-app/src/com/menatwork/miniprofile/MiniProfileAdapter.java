package com.menatwork.miniprofile;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.menatwork.ProfileActivity;
import com.menatwork.R;
import com.menatwork.TalentRadarApplication;
import com.menatwork.utils.ConfirmPingListener;
import com.menatwork.view.LoadProfilePictureTask;

public class MiniProfileAdapter extends ArrayAdapter<MiniProfileItemRow> {

	private final Activity activity;
	private final int layoutResourceId;
	private final List<MiniProfileItemRow> items;
	private final boolean enableSaveContactButtons;

	public MiniProfileAdapter(final Activity activity,
			final int layoutResourceId,
			final List<MiniProfileItemRow> itemRows,
			final boolean enableSaveContactButtons) {
		super(activity, layoutResourceId, itemRows);
		this.layoutResourceId = layoutResourceId;
		this.activity = activity;
		this.items = itemRows;
		this.enableSaveContactButtons = enableSaveContactButtons;
	}

	/**
	 * Is called everytime a row item is displayed.
	 */
	@Override
	public View getView(final int position, final View convertView,
			final ViewGroup parent) {
		View row = convertView;
		MiniProfileItemRowHolder holder;

		if (row == null) {
			final LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			// so that it can create a new context menu later
			row.setLongClickable(true);

			if (!enableSaveContactButtons)
				row.findViewById(R.id.mini_profile_save_contact_button)
						.setVisibility(View.GONE);

			// initialize mini profile item row holder 'le cache'
			holder = new MiniProfileItemRowHolder();
			holder.txtUsername = (TextView) row
					.findViewById(R.id.mini_profile_username);
			holder.txtHeadline = (TextView) row
					.findViewById(R.id.mini_profile_headline);
			holder.imgPicture = (ImageView) row
					.findViewById(R.id.mini_profile_user_pic);

			// magic needed
			row.setTag(holder);

		} else
			holder = (MiniProfileItemRowHolder) row.getTag();

		// gets the item to be modified (i suppose)
		final MiniProfileItemRow miniProfileItem = items.get(position);

		initializeRow(row, holder, miniProfileItem);

		// voila!
		return row;
	}

	private void initializeRow(final View row,
			final MiniProfileItemRowHolder holder,
			final MiniProfileItemRow miniProfileItem) {
		// sets stuff
		holder.txtUsername.setText(miniProfileItem.getUsername());
		holder.txtHeadline.setText(miniProfileItem.getHeadline());

		if (miniProfileItem.getPicture() == null)
			holder.imgPicture.setImageResource(R.drawable.default_profile_pic);
		else
			new LoadProfilePictureTask(activity, holder.imgPicture,
					miniProfileItem.getPicture()).execute();

		setupButtons(row, miniProfileItem);
	}

	private void setupButtons(final View row,
			final MiniProfileItemRow miniProfileItem) {
		// find thy buttons
		final Button seeProfileButton = (Button) row
				.findViewById(R.id.mini_profile_see_profile_button);
		final Button pingButton = (Button) row
				.findViewById(R.id.mini_profile_ping_button);
		final Button saveContactButton = (Button) row
				.findViewById(R.id.mini_profile_save_contact_button);

		// set buttons' on click listeners
		seeProfileButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final Intent intent = new Intent(activity,
						ProfileActivity.class);
				intent.putExtra(ProfileActivity.EXTRAS_USERID,
						miniProfileItem.getUserId());
				activity.startActivity(intent);
			}
		});

		pingButton.setOnClickListener(new ConfirmPingListener(activity,
				miniProfileItem.getUserId(), miniProfileItem.getUsername()));

		saveContactButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final String localUserId = getTalentRadarApplication()
						.getLocalUserId();
				final String userToBeAddedId = miniProfileItem.getUserId();

				new SaveUserByIdTask(activity).execute(localUserId,
						userToBeAddedId);
			}
		});
	}

	// ************************************************ //
	// ====== Talent Radar commons ======
	// ************************************************ //

	private TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) activity.getApplication();
	}

	// ************************************************ //
	// ====== MiniProfileItemRowHolder ======
	// ************************************************ //

	/**
	 * Class created for the sake of performance (as donde in the 'tutorial'),
	 * please forgive me Abraxas...
	 * 
	 * @author boris
	 * 
	 */
	static class MiniProfileItemRowHolder {
		ImageView imgPicture;
		TextView txtHeadline;
		TextView txtUsername;
	}

}