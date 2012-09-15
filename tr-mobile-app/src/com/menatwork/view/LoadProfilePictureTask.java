package com.menatwork.view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.menatwork.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoadProfilePictureTask extends AsyncTask<Void, Void, Bitmap> {
	private ImageView targetView;
	private ProgressBar progressIndicator;
	private String urlString;
	private Context context;

	public LoadProfilePictureTask(Context context, ImageView targetView,
			ProgressBar progressIndicator, String url) {
		super();
		this.context = context;
		this.targetView = targetView;
		this.progressIndicator = progressIndicator;
		this.urlString = url;
	}

	public LoadProfilePictureTask(Context context, ImageView targetView,
			String url) {
		this(context, targetView, null, url);
	}

	@Override
	protected Bitmap doInBackground(Void... args) {
		try {
			// TODO ProfilePicCache or sth like that
			URL url = new URL(urlString);
			return BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
		} catch (MalformedURLException e) {
			// if url is empty or malformed, just leave the default profile
			// picture
			return BitmapFactory.decodeResource(context.getResources(),
					R.drawable.default_profile_pic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap bmp) {
		if (bmp != null) {
			if (progressIndicator != null)
				progressIndicator.setVisibility(View.GONE);
			targetView.setImageBitmap(bmp);
		}
	}

}