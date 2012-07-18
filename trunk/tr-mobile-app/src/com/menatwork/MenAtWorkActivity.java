package com.menatwork;

import android.app.Activity;
import android.os.Bundle;

/**
 * Common Activity superclass for all our activities.
 *
 * @author miguel
 *
 */
public abstract class MenAtWorkActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getViewLayoutId());
		findViewElements();
		setupButtons();
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

}
