package com.menatwork.miniprofile;

import java.util.List;

import android.widget.ListView;

import com.menatwork.R;
import com.menatwork.GuiTalentRadarActivity;

public class MiniProfileListController {

	private final GuiTalentRadarActivity activity;
	private final int listViewId;
	private final MiniProfileItemRow[] itemRowsArray;

	public MiniProfileListController(final GuiTalentRadarActivity activity, final int listViewId,
			final List<MiniProfileItemRow> itemRows) {
		this(activity, listViewId, itemRows.toArray(new MiniProfileItemRow[0]));
	}

	public MiniProfileListController(final GuiTalentRadarActivity activity, final int listViewId,
			final MiniProfileItemRow... itemRowsArrays) {
		this.activity = activity;
		this.listViewId = listViewId;
		this.itemRowsArray = itemRowsArrays;
	}

	public void showList() {
		final MiniProfileAdapter adapter = new MiniProfileAdapter(activity, R.layout.mini_profile_item_row,
				itemRowsArray);

		final ListView listView = activity.findViewById(listViewId, ListView.class);
		listView.setAdapter(adapter);
	}
}
