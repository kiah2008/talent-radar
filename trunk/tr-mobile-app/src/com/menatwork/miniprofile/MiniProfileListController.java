package com.menatwork.miniprofile;

import java.util.List;

import android.widget.ListView;

import com.menatwork.R;
import com.menatwork.TalentRadarActivity;

public class MiniProfileListController {

	private final TalentRadarActivity activy;
	private final int listViewId;
	private final MiniProfileItemRow[] itemRowsArray;

	public MiniProfileListController(final TalentRadarActivity activy, final int listViewId,
			final List<MiniProfileItemRow> itemRows) {
		this(activy, listViewId, itemRows.toArray(new MiniProfileItemRow[0]));
	}

	public MiniProfileListController(final TalentRadarActivity activy, final int listViewId,
			final MiniProfileItemRow... itemRowsArrays) {
		this.activy = activy;
		this.listViewId = listViewId;
		this.itemRowsArray = itemRowsArrays;
	}

	public void showList() {
		final MiniProfileAdapter adapter = new MiniProfileAdapter(activy, R.layout.mini_profile_item_row,
				itemRowsArray);

		final ListView listView = activy.findViewById(listViewId, ListView.class);
		listView.setAdapter(adapter);
	}
}
