package com.menatwork.utils;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public enum MapProfilePicCache implements ProfilePictureCache {
	INSTANCE;

	// TODO implemented as a simple HashMap with no restrictions of size
	// whatsoever, not really a cache, should implement it correctly (when it
	// gets necessary)

	private final Map<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.menatwork.utils.ProfilePictureCache#hasKey(java.lang.String)
	 */
	@Override
	public boolean hasKey(final String urlString) {
		return bitmapCache.keySet().contains(urlString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.menatwork.utils.ProfilePictureCache#put(java.lang.String,
	 * android.graphics.Bitmap)
	 */
	@Override
	public void put(final String urlString, final Bitmap bitmap) {
		bitmapCache.put(urlString, bitmap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.menatwork.utils.ProfilePictureCache#get(java.lang.String)
	 */
	@Override
	public Bitmap get(final String urlString) {
		return bitmapCache.get(urlString);
	}
}
