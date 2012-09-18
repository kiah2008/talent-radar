package com.menatwork.utils;

import android.graphics.Bitmap;

public interface ProfilePictureCache {

	public abstract boolean hasKey(String urlString);

	public abstract void put(String urlString, Bitmap bitmap);

	public abstract Bitmap get(String urlString);

}