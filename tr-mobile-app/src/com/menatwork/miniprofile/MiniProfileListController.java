package com.menatwork.miniprofile;

import java.util.ArrayList;
import java.util.LinkedList;
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
		this.activity = activity;
		this.adapter = new MiniProfileAdapter(activity,
				R.layout.mini_profile_item_row, itemRows);

		initializeList(activity, listViewId);
	}

	public MiniProfileListController(final GuiTalentRadarActivity activity,
			final int listViewId, final MiniProfileItemRow... itemRowsArray) {
		this(activity, listViewId, toList(itemRowsArray));
	}

	private void initializeList(final GuiTalentRadarActivity activity,
			final int listViewId) {
		listView = activity.findViewById(listViewId, ListView.class);
		listView.setAdapter(adapter);
	}

	public void updateList(final List<MiniProfileItemRow> itemsRows) {
		adapter = new MiniProfileAdapter(activity,
				R.layout.mini_profile_item_row, itemsRows);

		listView.setAdapter(adapter);

		// XXX - this code was for an algorithm that just changed what was
		// needed in the list so as not to refresh every row (because the
		// pictures are a little batateras), but it didn't work as expected -
		// miguel - 19/09/2012

		// final List<MiniProfileItemRow> oldAdapterItems = adapterItems();
		//
		// // for each new item row, we verify that:
		// // - if i already have it, i don't change it
		// // - if i didn't have it, i add it to adapter
		// for (final MiniProfileItemRow newItemRow : itemsRows) {
		// if (containsSameUser(newItemRow, oldAdapterItems))
		// removeMiniProfile(newItemRow, oldAdapterItems);
		// else
		// adapter.add(newItemRow);
		// }
		//
		// // for each old item (already in the adapter)
		// // - if i have it in my new items, i don't change it
		// // - if i don't have it anymore, i remove it
		// for (final MiniProfileItemRow oldAdapterItem : oldAdapterItems)
		// adapter.remove(oldAdapterItem);
		//
		// adapter.notifyDataSetChanged();
	}

	private void removeMiniProfile(final MiniProfileItemRow miniProfileItemRow,
			final List<MiniProfileItemRow> adapterItems) {
		MiniProfileItemRow toBeRemoved = null;

		for (final MiniProfileItemRow adapterItem : adapterItems)
			if (isSameUser(miniProfileItemRow, adapterItem))
				toBeRemoved = adapterItem;

		adapterItems.remove(toBeRemoved);
	}

	private boolean containsSameUser(
			final MiniProfileItemRow miniProfileItemRow,
			final List<MiniProfileItemRow> adapterItems) {
		for (final MiniProfileItemRow adapterItem : adapterItems)
			if (isSameUser(miniProfileItemRow, adapterItem))
				return true;

		return false;
	}

	private boolean isSameUser(final MiniProfileItemRow miniProfileItemRow,
			final MiniProfileItemRow adapterItem) {
		return adapterItem.getUserId().equals(miniProfileItemRow.getUserId());
	}

	private List<MiniProfileItemRow> adapterItems() {
		final LinkedList<MiniProfileItemRow> adapterItems = new LinkedList<MiniProfileItemRow>();

		for (int i = 0; i < adapter.getCount(); i++)
			adapterItems.add(adapter.getItem(i));

		return adapterItems;
	}

	// ************************************************ //
	// ====== Utils ======
	// ************************************************ //

	private static List<MiniProfileItemRow> toList(
			final MiniProfileItemRow[] itemRowsArray) {
		final ArrayList<MiniProfileItemRow> arrayList = new ArrayList<MiniProfileItemRow>(
				itemRowsArray.length);

		for (final MiniProfileItemRow miniProfileItemRow : itemRowsArray)
			arrayList.add(miniProfileItemRow);

		return arrayList;
	}

}
