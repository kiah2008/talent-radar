package com.menatwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Common Activity superclass for all our GUI activities.
 *
 * @author miguel
 *
 */
public abstract class GuiTalentRadarActivity extends TalentRadarActivity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		preCreate(savedInstanceState);
		setContentView(getViewLayoutId());
		findViewElements();
		setupButtons();
		postCreate(savedInstanceState);
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.log_out:
			getTalentRadarApplication().logOut();
			startActivity(new Intent(this, LoginActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This method is called once the activity is created, but its view content
	 * as well as its view elements and buttons have not been configured.
	 *
	 * @param savedInstanceState
	 */
	protected void preCreate(final Bundle savedInstanceState) {
		// Won't do anything by default
	}

	/**
	 * This method is called once the activity is created, its view content is
	 * initialized as well as its view elements found and buttons configured.
	 *
	 * @param savedInstanceState
	 */
	protected void postCreate(final Bundle savedInstanceState) {
		// Won't do anything by default
	}

	/**
	 * Sets up all buttons in this activity, i.e. adding listeners to them and
	 * stuff.
	 */
	protected abstract void setupButtons();

	/**
	 * Finds and initializes this activities sub views.
	 */
	protected abstract void findViewElements();

	/**
	 * Returns a view layout ID. Tipically would be something like
	 * R.layout.myLayout
	 *
	 * @return Layout ID
	 */
	protected abstract int getViewLayoutId();

	/* ****************************************** */
	/* ********* Finding views ****************** */
	/* ****************************************** */

	public <T extends View> T findViewById(final int id, final Class<T> viewType) {
		return viewType.cast(findViewById(id));
	}

	public ToggleButton findToggleButtonById(final int id) {
		return findViewById(id, ToggleButton.class);
	}

	public Button findButtonById(final int id) {
		return findViewById(id, Button.class);
	}

	public EditText findEditTextById(final int id) {
		return findViewById(id, EditText.class);
	}

	public ImageButton findImageButtonById(final int id) {
		return findViewById(id, ImageButton.class);
	}

	public ImageView findImageViewById(final int id) {
		return findViewById(id, ImageView.class);
	}

	public TextView findTextViewById(final int id) {
		return findViewById(id, TextView.class);
	}

	public ViewGroup findViewGroupById(final int id) {
		return findViewById(id, ViewGroup.class);
	}

}
