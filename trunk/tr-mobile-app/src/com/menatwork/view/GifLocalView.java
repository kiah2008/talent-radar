package com.menatwork.view;

import android.content.Context;
import android.util.AttributeSet;

public class GifLocalView extends GifWebView {

	public GifLocalView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public GifLocalView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected String getAnimatedGifPath(final AttributeSet attrs) {
		final String animatedGifPath = super.getAnimatedGifPath(attrs);

		return "file:///" + animatedGifPath;
	}

}
