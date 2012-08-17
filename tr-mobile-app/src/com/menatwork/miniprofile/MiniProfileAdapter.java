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

public class MiniProfileAdapter extends ArrayAdapter<MiniProfileItemRow> {

	private final Activity activity;
	private final int layoutResourceId;
	private final MiniProfileItemRow items[];

	public MiniProfileAdapter(final android.app.Activity activity, final int layoutResourceId,
			final MiniProfileItemRow[] items) {
		super(activity, layoutResourceId, items);
		this.layoutResourceId = layoutResourceId;
		this.activity = activity;
		this.items = items;
	}

	/**
	 * Is called everytime a row item is displayed.
	 */
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		MiniProfileItemRowHolder holder;

		if (row == null) {
			final LayoutInflater inflater = activity.getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			// initialize mini profile item row holder 'le cache'
			holder = new MiniProfileItemRowHolder();
			holder.txtUsername = (TextView) row.findViewById(R.id.mini_profile_username);
			holder.txtHeadline = (TextView) row.findViewById(R.id.mini_profile_headline);
			holder.imgPicture = (ImageView) row.findViewById(R.id.mini_profile_user_pic);

			// find thy buttons
			final Button seeProfileButton = (Button) row.findViewById(R.id.mini_profile_see_profile_button);
			final Button pingButton = (Button) row.findViewById(R.id.mini_profile_ping_button);
			final Button saveContactButton = (Button) row.findViewById(R.id.mini_profile_save_contact_button);

			// set buttons' on click listeners
			seeProfileButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					final Intent intent = new Intent(activity, ProfileActivity.class);
					// TODO - get user id from here, try to reach the
					// miniprofileitemrow if possible - boris - 17/08/2012
					// intent.putExtra(ProfileActivity.EXTRAS_USERID, userId);
					intent.putExtra(ProfileActivity.EXTRAS_USERID, "1" /*for now userId = 1*/); 
					activity.startActivity(intent);
				}
			});
			pingButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					// TODO - ping the almighty user - boris - 17/08/2012
					Toast.makeText(activity, "Ping! ... ping, ping! Hmff... PIINAGFIANGPRREAIG",
							Toast.LENGTH_SHORT).show();
				}
			});
			saveContactButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(final View v) {
					// TODO - save the contact which means... - boris -
					// 17/08/2012
					Toast.makeText(activity, "saving the contact... ohh, WTF!!!", Toast.LENGTH_SHORT).show();
				}
			});

			// magic needed
			row.setTag(holder);

		} else
			holder = (MiniProfileItemRowHolder) row.getTag();

		// gets the item to be modified (i suppose)
		final MiniProfileItemRow itemRow = items[position];

		// sets stuff
		holder.txtUsername.setText(itemRow.getUsername());
		holder.txtHeadline.setText(itemRow.getHeadline());

		if (itemRow.getPicture() == null)
			holder.imgPicture.setImageResource(R.drawable.default_profile_pic);
		else
			// in case it indeed have a user picture (not yet implemented!)
			throw new UnsupportedOperationException(
					"don't know how to present a non predetermined user picture");

		// voila!
		return row;
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
