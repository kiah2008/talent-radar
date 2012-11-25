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
	private final boolean enableSaveContactButtons;

	public MiniProfileListController(final GuiTalentRadarActivity activity,
			final int listViewId, final List<MiniProfileItemRow> itemRows) {
		this(activity, listViewId, itemRows, true);
	}

	public MiniProfileListController(final GuiTalentRadarActivity activity,
			final int listViewId, final MiniProfileItemRow... itemRowsArray) {
		this(activity, listViewId, toList(itemRowsArray));
	}

	public MiniProfileListController(final GuiTalentRadarActivity activity,
			final int listViewId, final List<MiniProfileItemRow> itemRows,
			final boolean enableSaveContactButtons) {
		this.activity = activity;
		this.enableSaveContactButtons = enableSaveContactButtons;
		this.adapter = new MiniProfileAdapter(activity,
				R.layout.mini_profile_item_row, itemRows,
				enableSaveContactButtons);

		initializeList(activity, listViewId);
	}

	private void initializeList(final GuiTalentRadarActivity activity,
			final int listViewId) {
		listView = activity.findViewById(listViewId, ListView.class);
		listView.setAdapter(adapter);
	}

	public void updateList(final List<MiniProfileItemRow> itemsRows) {
		adapter = new MiniProfileAdapter(activity,
				R.layout.mini_profile_item_row, itemsRows,
				enableSaveContactButtons);

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

	public void removeMiniProfileBeUserId(final String userIdToBeRemoved) {
		final List<MiniProfileItemRow> adapterItems = adapterItems();
		MiniProfileItemRow toBeRemoved = null;

		for (final MiniProfileItemRow adapterItem : adapterItems)
			if (isSameUser(userIdToBeRemoved, adapterItem))
				toBeRemoved = adapterItem;

		// adapterItems.remove(toBeRemoved);
		adapter.remove(toBeRemoved);
		adapter.notifyDataSetChanged();
	}

	@SuppressWarnings("unused")
	private void removeMiniProfile(final MiniProfileItemRow miniProfileItemRow,
			final List<MiniProfileItemRow> adapterItems) {
		MiniProfileItemRow toBeRemoved = null;

		final String userIdToBeRemoved = miniProfileItemRow.getUserId();

		for (final MiniProfileItemRow adapterItem : adapterItems)
			if (isSameUser(userIdToBeRemoved, adapterItem))
				toBeRemoved = adapterItem;

		adapterItems.remove(toBeRemoved);
	}

	@SuppressWarnings("unused")
	private boolean containsSameUser(
			final MiniProfileItemRow miniProfileItemRow,
			final List<MiniProfileItemRow> adapterItems) {
		for (final MiniProfileItemRow adapterItem : adapterItems)
			if (isSameUser(miniProfileItemRow.getUserId(), adapterItem))
				return true;

		return false;
	}

	private final boolean isSameUser(final String userIdToBeRemoved,
			final MiniProfileItemRow adapterItem) {
		return adapterItem.getUserId().equals(userIdToBeRemoved);
	}

	@SuppressWarnings("unused")
	private List<MiniProfileItemRow> adapterItems() {
		final LinkedList<MiniProfileItemRow> adapterItems = new LinkedList<MiniProfileItemRow>();

		for (int i = 0; i < adapter.getCount(); i++)
			adapterItems.add(adapter.getItem(i));

		return adapterItems;
	}

	public ListView getListView() {
		return listView;
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
