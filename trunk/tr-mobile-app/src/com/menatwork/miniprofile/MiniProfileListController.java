package com.menatwork.miniprofile;

import java.util.List;

import android.widget.ListView;

import com.menatwork.GuiTalentRadarActivity;
import com.menatwork.R;

public class MiniProfileListController {

	private MiniProfileAdapter adapter;
	private ListView listView;
	private final GuiTalentRadarActivity activity;

	public MiniProfileListController(final GuiTalentRadarActivity activity,
			final int listViewId, final List<MiniProfileItemRow> itemRows) {
		this(activity, listViewId, itemRows.toArray(new MiniProfileItemRow[0]));
	}

	public MiniProfileListController(final GuiTalentRadarActivity activity,
			final int listViewId, final MiniProfileItemRow... itemRowsArray) {
		this.activity = activity;
		this.adapter = new MiniProfileAdapter(activity,
				R.layout.mini_profile_item_row, itemRowsArray);

		initializeList(activity, listViewId);
	}

	private void initializeList(final GuiTalentRadarActivity activity,
			final int listViewId) {
		listView = activity.findViewById(listViewId, ListView.class);
		listView.setAdapter(adapter);
	}

	public void updateList(final List<MiniProfileItemRow> itemsRowArray) {
		adapter = new MiniProfileAdapter(activity,
				R.layout.mini_profile_item_row, itemsRowArray.toArray(new MiniProfileItemRow[0]));

		listView.setAdapter(adapter);
	}
}
