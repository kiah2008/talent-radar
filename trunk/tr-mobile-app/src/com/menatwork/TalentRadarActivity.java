package com.menatwork;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Common Activity superclass for all our activities.
 *
 * @author miguel
 *
 */
public abstract class TalentRadarActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getViewLayoutId());
		findViewElements();
		setupButtons();
		postCreate(savedInstanceState);
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
	 * Retrieves the {@link Application} object casted to a
	 * {@link TalentRadarApplication}
	 *
	 * @return {@link TalentRadarApplication} for the app
	 */
	protected TalentRadarApplication getTalentRadarApplication() {
		return (TalentRadarApplication) getApplication();
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

	protected <T> T findViewById(final int id, final Class<T> viewType) {
		return viewType.cast(findViewById(id));
	}

	protected Button findButtonById(final int id) {
		return findViewById(id, Button.class);
	}

	protected EditText findEditTextById(final int id) {
		return findViewById(id, EditText.class);
	}

	protected ImageButton findImageButtonById(final int id) {
		return findViewById(id, ImageButton.class);
	}

	protected TextView findTextViewById(final int id) {
		return findViewById(id, TextView.class);
	}

	protected ViewGroup findViewGroupById(final int id) {
		return findViewById(id, ViewGroup.class);
	}

}
