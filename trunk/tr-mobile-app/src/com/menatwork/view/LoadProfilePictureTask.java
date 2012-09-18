package com.menatwork.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.menatwork.R;
import com.menatwork.utils.MapProfilePicCache;

public class LoadProfilePictureTask extends AsyncTask<Void, Void, Bitmap> {
	private final ImageView targetView;
	private final ProgressBar progressIndicator;
	private final String urlString;
	private final Context context;

	public LoadProfilePictureTask(final Context context,
			final ImageView targetView, final ProgressBar progressIndicator,
			final String url) {
		super();
		this.context = context;
		this.targetView = targetView;
		this.progressIndicator = progressIndicator;
		this.urlString = url;
	}

	public LoadProfilePictureTask(final Context context,
			final ImageView targetView, final String url) {
		this(context, targetView, null, url);
	}

	@Override
	protected Bitmap doInBackground(final Void... args) {
		try {
			// TODO ProfilePicCache or sth like that
			if (!MapProfilePicCache.INSTANCE.hasKey(urlString)) {
				final URL url = new URL(urlString);
				final Bitmap bitmap = BitmapFactory.decodeStream(url
						.openConnection().getInputStream());
				MapProfilePicCache.INSTANCE.put(urlString, bitmap);
			}
			return MapProfilePicCache.INSTANCE.get(urlString);
		} catch (final MalformedURLException e) {
			// if url is empty or malformed, just leave the default profile
			// picture
			return BitmapFactory.decodeResource(context.getResources(),
					R.drawable.default_profile_pic);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(final Bitmap bmp) {
		if (bmp != null) {
			if (progressIndicator != null)
				progressIndicator.setVisibility(View.GONE);
			targetView.setImageBitmap(bmp);
		}
	}

}