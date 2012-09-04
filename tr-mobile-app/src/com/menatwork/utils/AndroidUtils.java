package com.menatwork.utils;

import android.os.Build;

public class AndroidUtils {

	private static final String EMULATOR_BUILD_PRODUCT = "sdk";

	/**
	 * Tells whether the application is running on an emulator rather than a
	 * real phone.
	 *
	 * @return <code>true</code> - if running on emulator
	 */
	public static boolean isRunningOnEmulator() {
		// XXX - should work for version 2.3.3 and above and the generic
		// google's emulator (beware of intel's and other implementations) -
		// miguel - 27/08/2012
		return EMULATOR_BUILD_PRODUCT.equals(Build.PRODUCT);
	}
}
