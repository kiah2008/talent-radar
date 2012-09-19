package com.menatwork.miniprofile;

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
import android.widget.Toast;

import com.menatwork.ProfileActivity;
import com.menatwork.R;
import com.menatwork.TalentRadarApplication;
import com.menatwork.view.LoadProfilePictureTask;

public class MiniProfileAdapter extends ArrayAdapter<MiniProfileItemRow> {

	private final Activity activity;
	private final int layoutResourceId;
	private final MiniProfileItemRow items[];

	public MiniProfileAdapter(final Activity activity,
			final int layoutResourceId, final MiniProfileItemRow[] items) {
		super(activity, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.activity = activity;
		this.items = items;
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
		final MiniProfileItemRow miniProfileItem = items[position];

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
//				new SeeProfileTask(activity).execute(miniProfileItem.getUserId());
				final Intent intent = new Intent(activity, ProfileActivity.class);
				intent.putExtra(ProfileActivity.EXTRAS_USERID, miniProfileItem.getUserId());
				activity.startActivity(intent);
			}
		});
		pingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final String localUserId = getTalentRadarApplication()
						.getLocalUser().getId();
				new PingTask(activity).execute(localUserId,
						miniProfileItem.getUserId());
			}
		});
		saveContactButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// TODO - save the contact which means... I suppose some other
				// kind of activity, right? - boris - 17/08/2012
				Toast.makeText(
						activity,
						"saving the contact " + miniProfileItem.getUsername()
								+ "... ohh, WTF!!!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) activity.getApplication();
	}

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