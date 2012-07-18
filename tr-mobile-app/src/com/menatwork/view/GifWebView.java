package com.menatwork.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

import com.menatwork.R;

public class GifWebView extends WebView {

	public GifWebView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public GifWebView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	protected void init(final AttributeSet attrs) {
		loadUrl(getAnimatedGifPath(attrs));

	}

	protected String getAnimatedGifPath(final AttributeSet attrs) {
		final TypedArray typedArray = getContext().obtainStyledAttributes(
				attrs, R.styleable.GifWebView);

		final String animatedGifPath = typedArray
				.getString(R.styleable.GifWebView_gifAnimatedPath);

		Log.d("test", "animatedGifPath = " + animatedGifPath);

		// Don't forget this
		typedArray.recycle();

		return animatedGifPath;
	}

}
